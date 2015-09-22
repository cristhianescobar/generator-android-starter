package <%= appPackage %>.screen.login;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LoginScreenTest {

	private LoginScreen screen;

	@Before
	public void before() throws Exception {
		screen = new LoginScreen();
	}
}
