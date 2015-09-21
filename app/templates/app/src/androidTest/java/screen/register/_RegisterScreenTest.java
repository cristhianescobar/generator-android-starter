package <%= appPackage %>.screen.register;

import <%= appPackage %>.R;
import <%= appPackage %>.test.util.BlueprintVerifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RegisterScreenTest {
    private RegisterScreen screen;

    @Before
    public void before() throws Exception {
        screen = new RegisterScreen();
    }

    @Test
    public void shouldDefineBlueprint() throws Exception {
        BlueprintVerifier.forScreen(screen)
                .injectsView(RegisterView.class)
                .addsToModule(RegisterScreen.RegisterModule.class)
                .hasLayout(R.layout.view_register)
                .verify();
    }
}
