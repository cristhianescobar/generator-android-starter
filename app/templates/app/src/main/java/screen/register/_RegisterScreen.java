package <%= appPackage %>.screen.register;

import <%= appPackage %>.ActivityModule;
import <%= appPackage %>.R;
import <%= appPackage %>.util.flow.BasePath;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.WithModule;

@WithModule(RegisterScreen.RegisterModule.class)
@Layout(R.layout.view_register)
public class RegisterScreen extends BasePath {

    @Override
    public Object getDaggerModule() {
        return new RegisterModule();
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @dagger.Module(
            injects = RegisterView.class,
            addsTo = ActivityModule.class
    )
    public class RegisterModule {
    }
}
