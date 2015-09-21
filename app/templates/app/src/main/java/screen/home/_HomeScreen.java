package <%= appPackage %>.screen.home;

import <%= appPackage %>.ActivityModule;
import <%= appPackage %>.R;
import <%= appPackage %>.util.flow.BasePath;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.WithModule;

/**
 * This screen might be where you send user's once they're logged in/registered
 * as a starting point for your app.
 * <p/>
 * The implementation is incomplete and left to your imagination.
 */
@WithModule(HomeScreen.HomeModule.class)
@Layout(R.layout.view_home)
public class HomeScreen extends BasePath {

    @Override
    public Object getDaggerModule() {
        return new HomeModule();
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @dagger.Module(
            injects = HomeView.class,
            addsTo = ActivityModule.class
    )
    class HomeModule {
    }
}
