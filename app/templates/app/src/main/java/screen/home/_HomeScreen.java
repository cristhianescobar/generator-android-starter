package <%= appPackage %>.screen.home;

import <%= appPackage %>.ApplicationComponent;
import <%= appPackage %>.R;
import <%= appPackage %>.app.ActivityComponent;
import <%= appPackage %>.util.dagger.DaggerScope;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.ScreenComponentFactory;

import flow.path.Path;

/**
 * This screen might be where you send user's once they're logged in/registered
 * as a starting point for your app.
 * <p/>
 * The implementation is incomplete and left to your imagination.
 */
@Layout(R.layout.screen_home)
public class HomeScreen extends Path implements ScreenComponentFactory<ActivityComponent> {
    @Override
    public Object createComponent(ActivityComponent parent) {
        return DaggerHomeScreen_HomeComponent.builder()
                .activityComponent(parent)
                .homeModule(new HomeModule())
                .build();
    }

    @dagger.Component(dependencies = ActivityComponent.class, modules = HomeModule.class)
    @DaggerScope(HomeComponent.class)
    public interface HomeComponent extends ApplicationComponent {
        void inject(HomeView view);
        void inject(HomePresenter presenter);
    }


    @dagger.Module
    class HomeModule {
//        @Provides
//        @DaggerScope(HomeComponent.class)
//        public HomePresenter providesPresenter(ToolbarOwner toolbarOwner) {
//            return new HomePresenter(toolbarOwner);
//        }
    }
}
