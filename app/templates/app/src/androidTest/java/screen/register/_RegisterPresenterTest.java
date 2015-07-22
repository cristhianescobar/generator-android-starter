package <%= appPackage %>.screen.register;

import static <%= appPackage %>.test.util.FlowTestHelper.createFlow;
import static <%= appPackage %>.test.util.ViewTestHelper.mockView;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;

import <%= appPackage %>.actionbar.ActionBarConfig;
import <%= appPackage %>.actionbar.ActionBarOwner;
import <%= appPackage %>.model.UserWithPassword;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.service.ApiService;
import <%= appPackage %>.service.StubApiService;
import <%= appPackage %>.test.util.FlowTestHelper.MockFlowListener;

import flow.Flow;

@RunWith(RobolectricTestRunner.class)
public class RegisterPresenterTest {
	@Mock RegisterScreen screen;
	@Mock ActionBarOwner actionBarOwner;
	@Mock JsonSharedPreferencesRepository prefsRepository;

	@Spy ApiService apiService = new StubApiService();
	@Spy Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Captor ArgumentCaptor<ActionBarConfig> actionBarConfigCaptor;
	@Captor ArgumentCaptor<UserWithPassword> userWithPasswordArgumentCaptor;

	private MockFlowListener flowListener;
	private RegisterPresenter presenter;

	@Before
	public void before() throws Exception {
		MockitoAnnotations.initMocks(this);

		flowListener = new MockFlowListener();
		Flow flow = createFlow(screen, flowListener);

		RegisterView view = mockView(RegisterView.class);

		presenter = new RegisterPresenter(flow, actionBarOwner, validator, apiService, prefsRepository);
		presenter.takeView(view);
	}

	@Test
	public void shouldConfigureActionBarOnLoad() throws Exception {
		verify(actionBarOwner).setConfig(actionBarConfigCaptor.capture());

		ActionBarConfig config = actionBarConfigCaptor.getValue();
		assertThat(config.getTitle()).isEqualTo("Register");
		assertThat(config.isVisible()).isTrue();
	}

	@Test
	public void shouldRegister() throws Exception {
		String name = "Foo  ";
		String email = " foo@example.org ";
		String password = "     password1";

		presenter.register(name, email, password);

		verify(apiService).register(userWithPasswordArgumentCaptor.capture());

		UserWithPassword userWithPassword = userWithPasswordArgumentCaptor.getValue();
		assertThat(userWithPassword.getName()).isEqualTo("Foo");
		assertThat(userWithPassword.getEmail()).isEqualTo("foo@example.org");

		Field passwordField = userWithPassword.getClass().getDeclaredField("password");
		passwordField.setAccessible(true);
		assertThat(passwordField.get(userWithPassword)).isEqualTo("password1");
	}
}
