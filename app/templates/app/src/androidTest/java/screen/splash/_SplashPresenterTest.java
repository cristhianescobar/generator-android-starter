package <%= appPackage %>.screen.splash;

import <%= appPackage %>.screen.login.LoginScreen;
import <%= appPackage %>.screen.register.RegisterScreen;
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
import org.robolectric.RobolectricTestRunner;

import flow.Flow;
import flow.FlowDelegate;

import static <%= appPackage %>.test.util.FlowTestHelper.createFlow;
import static <%= appPackage %>.test.util.ViewTestHelper.mockView;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class SplashPresenterTest {

    @Mock
    SplashScreen screen;
    @Mock
    ToolbarOwner toolbarOwner;

    @Captor
    ArgumentCaptor<ToolbarConfig> actionBarConfigCaptor;

    private SplashView view;
    private SplashPresenter presenter;
    private MockFlowDispatcher flowListener;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        view = mockView(SplashView.class);
        flowListener = new MockFlowDispatcher();
        FlowDelegate flow = createFlow(screen, flowListener);

        presenter = new SplashPresenter(toolbarOwner);
        presenter.takeView(view);
    }

    @Test
    public void shouldHideActionBarOnLoad() throws Exception {
        verify(toolbarOwner).setConfig(actionBarConfigCaptor.capture());

        ToolbarConfig toolbarConfig = actionBarConfigCaptor.getValue();
        assertThat(toolbarConfig.isVisible()).isFalse();
    }

    @Test
    public void shouldGoToLoginScreen() throws Exception {
        presenter.login();

        assertThat(flowListener.lastDirection).isEqualTo(Flow.Direction.FORWARD);
        assertThat(flowListener.lastScreen).isInstanceOf(LoginScreen.class);
    }

    @Test
    public void shouldGoToRegisterScreen() throws Exception {
        presenter.register();

        assertThat(flowListener.lastDirection).isEqualTo(Flow.Direction.FORWARD);
        assertThat(flowListener.lastScreen).isInstanceOf(RegisterScreen.class);
    }
}
