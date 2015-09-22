package <%= appPackage %>.screen.splash;

import <%= appPackage %>.ActivityModule;
import <%= appPackage %>.R;
import <%= appPackage %>.util.flow.BasePath;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.WithModule;

@WithModule(SplashScreen.SplashModule.class)
@Layout(R.layout.view_splash)
public class SplashScreen extends BasePath {

    @Override
    public Object getDaggerModule() {
        return new SplashModule();
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @dagger.Module(
            injects = SplashView.class,
            addsTo = ActivityModule.class
    )
    public class SplashModule {
    }
}
