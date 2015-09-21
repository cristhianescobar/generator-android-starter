package <%= appPackage %>.screen.register;

import <%= appPackage %>.R;
import <%= appPackage %>.TestApplicationModule;
import <%= appPackage %>.model.UserWithPassword;
import <%= appPackage %>.util.dagger.ObjectGraphService;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.WithModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import dagger.Provides;
import flow.path.Path;

import static <%= appPackage %>.test.util.ViewTestHelper.createView;
import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@RunWith(RobolectricTestRunner.class)
public class RegisterViewTest {

    @Inject
    RegisterPresenter presenter;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private RegisterView view;

    @Before
    public void before() throws Exception {
        view = createView(RegisterView.class).forScreen(new MockRegisterScreen());
        ObjectGraphService.inject(view.getContext(), this);
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

    @WithModule(MockRegisterScreen.MockRegisterModule.class)
    @Layout(R.layout.view_register)
    static class MockRegisterScreen extends Path {

        @dagger.Module(
                injects = {RegisterView.class, RegisterViewTest.class},
                addsTo = TestApplicationModule.class
        )
        class MockRegisterModule {
            @Provides
            @Singleton
            RegisterPresenter provideMockPresenter() {
                return mock(RegisterPresenter.class, RETURNS_DEEP_STUBS);
            }
        }
    }
}
