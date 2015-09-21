package <%= appPackage %>.screen.splash;

import <%= appPackage %>.R;
import <%= appPackage %>.TestApplicationModule;
import <%= appPackage %>.util.dagger.ObjectGraphService;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.WithModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import flow.path.Path;

import static <%= appPackage %>.test.util.ViewTestHelper.createView;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class SplashViewTest {

	@Inject SplashPresenter presenter;

	private SplashView view;

	@Before
	public void before() throws Exception {
		view = createView(SplashView.class).forScreen(new MockSplashScreen());
        ObjectGraphService.inject(view.getContext(), this);
	}

	@Test
	public void shouldLogon() throws Exception {
		view.loginButton.performClick();

		verify(presenter).login();
	}

	@Test
	public void shouldRegister() throws Exception {
		view.registerButton.performClick();

		verify(presenter).register();
	}

	@WithModule(MockSplashScreen.MockSplashModule.class)
	@Layout(R.layout.view_splash)
	static class MockSplashScreen extends Path {

		@dagger.Module(
				injects = { SplashView.class, SplashViewTest.class },
				addsTo = TestApplicationModule.class
		)
		class MockSplashModule {
			@Provides
			@Singleton
			SplashPresenter provideMockPresenter() {
				return mock(SplashPresenter.class, RETURNS_DEEP_STUBS);
			}
		}
	}
}
