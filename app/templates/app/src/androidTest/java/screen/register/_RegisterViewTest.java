package <%= appPackage %>.screen.register;

import static <%= appPackage %>.test.util.ViewTestHelper.createView;
import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import <%= appPackage %>.R;
import <%= appPackage %>.TestApplicationModule;
import <%= appPackage %>.model.UserWithPassword;

import dagger.Provides;
import flow.Layout;
import mortar.Blueprint;
import mortar.Mortar;

@RunWith(RobolectricTestRunner.class)
public class RegisterViewTest {

	@Inject RegisterPresenter presenter;

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private RegisterView view;

	@Before
	public void before() throws Exception {
		view = createView(RegisterView.class).forScreen(new MockRegisterScreen());

		Mortar.inject(view.getContext(), this);
	}

	@Test
	public void shouldRegister() throws Exception {
		String name = "Foo";
		String email = "foo@example.org";
		String password = "password1";

		view.nameEditText.setText(name);
		view.emailEditText.setText(email);
		view.passwordEditText.setText(password);

		view.registerButton.performClick();

		verify(presenter).register(name, email, password);
	}

	@Test
	public void shouldShowError() throws Exception {
		view.onRegistrationError(new RuntimeException("intentional"));

		assertThat(view.alertDialog).isShowing();
	}

	@Test
	public void shouldShowValidationError() throws Exception {
		UserWithPassword userWithPassword = new UserWithPassword(null, "", " ");
		Set<ConstraintViolation<UserWithPassword>> errors = validator.validate(userWithPassword);

		view.onValidationError(errors);

		assertThat(view.nameEditText).hasError();
		assertThat(view.emailEditText).hasError();
		assertThat(view.passwordEditText).hasError();
	}

	@Layout(R.layout.view_register)
	static class MockRegisterScreen implements Blueprint {
		@Override
		public String getMortarScopeName() {
			return getClass().getName();
		}

		@Override
		public Object getDaggerModule() {
			return new Module();
		}

		@dagger.Module(
				injects = { RegisterView.class, RegisterViewTest.class },
				addsTo = TestApplicationModule.class
		)
		class Module {
			@Provides
			@Singleton
			RegisterPresenter provideMockPresenter() {
				return mock(RegisterPresenter.class, RETURNS_DEEP_STUBS);
			}
		}
	}
}
