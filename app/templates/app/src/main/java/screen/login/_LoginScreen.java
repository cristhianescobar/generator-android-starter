package <%= appPackage %>.screen.login;

import <%= appPackage %>.ActivityModule;
import <%= appPackage %>.R;
import <%= appPackage %>.util.flow.BasePath;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.WithModule;

@WithModule(LoginScreen.LoginModule.class)
@Layout(R.layout.view_login)
public class LoginScreen extends BasePath {

    @Override
    public Object getDaggerModule() {
        return new LoginModule();
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @dagger.Module(
            injects = LoginView.class,
            addsTo = ActivityModule.class
    )
    public class LoginModule {
    }
}
