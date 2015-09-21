package <%= appPackage %>.screen.register;

import <%= appPackage %>.model.UserWithPassword;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.service.ApiService;
import <%= appPackage %>.service.StubApiService;
import <%= appPackage %>.test.util.FlowTestHelper.MockFlowDispatcher;
import <%= appPackage %>.toolbar.ToolbarConfig;
import <%= appPackage %>.toolbar.ToolbarOwner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;

import javax.validation.Validation;
import javax.validation.Validator;

import flow.FlowDelegate;

import static <%= appPackage %>.test.util.FlowTestHelper.createFlow;
import static <%= appPackage %>.test.util.ViewTestHelper.mockView;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class RegisterPresenterTest {
	@Mock RegisterScreen screen;
	@Mock
	ToolbarOwner toolbarOwner;
	@Mock JsonSharedPreferencesRepository prefsRepository;

	@Spy ApiService apiService = new StubApiService();
	@Spy Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Captor ArgumentCaptor<ToolbarConfig> actionBarConfigCaptor;
	@Captor ArgumentCaptor<UserWithPassword> userWithPasswordArgumentCaptor;

	private MockFlowDispatcher flowListener;
	private RegisterPresenter presenter;

	@Before
	public void before() throws Exception {
		MockitoAnnotations.initMocks(this);

		flowListener = new MockFlowDispatcher();
		FlowDelegate flow = createFlow(screen, flowListener);

		RegisterView view = mockView(RegisterView.class);

		presenter = new RegisterPresenter(toolbarOwner, validator, apiService, prefsRepository);
		presenter.takeView(view);
	}

	@Test
	public void shouldConfigureActionBarOnLoad() throws Exception {
		verify(toolbarOwner).setConfig(actionBarConfigCaptor.capture());

		ToolbarConfig config = actionBarConfigCaptor.getValue();
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
