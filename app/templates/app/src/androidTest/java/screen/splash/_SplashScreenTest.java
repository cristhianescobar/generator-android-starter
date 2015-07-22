package <%= appPackage %>.screen.splash;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import <%= appPackage %>.R;

import <%= appPackage %>.screen.main.MainScreen;
import <%= appPackage %>.test.util.BlueprintVerifier;

@RunWith(RobolectricTestRunner.class)
public class SplashScreenTest {
	@Test
	public void shouldDefineBlueprint() throws Exception {
		BlueprintVerifier.forScreen(new SplashScreen())
				.injectsView(SplashView.class)
				.addsToModule(MainScreen.Module.class)
				.hasLayout(R.layout.view_splash)
				.verify();
	}
}
