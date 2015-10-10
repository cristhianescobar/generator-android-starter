package <%= appPackage %>.screen.splash;

import <%= appPackage %>.ApplicationComponent;
import <%= appPackage %>.R;
import <%= appPackage %>.app.ActivityComponent;
import <%= appPackage %>.util.dagger.DaggerScope;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.ScreenComponentFactory;

import flow.path.Path;

@Layout(R.layout.screen_splash)
public class SplashScreen extends Path implements ScreenComponentFactory<ActivityComponent> {
    @Override
    public Object createComponent(ActivityComponent parent) {
        return DaggerSplashScreen_SplashComponent.builder()
                .activityComponent(parent)
                .splashModule(new SplashModule())
                .build();
    }

    @dagger.Component(dependencies = ActivityComponent.class, modules = SplashModule.class)
    @DaggerScope(SplashComponent.class)
    public interface SplashComponent extends ApplicationComponent {
        void inject(SplashView view);
        void inject(SplashPresenter presenter);
    }


    @dagger.Module
    class SplashModule {
//        @Provides
//        @DaggerScope(SplashComponent.class)
//        public SplashPresenter providesPresenter(ToolbarOwner toolbarOwner) {
//            return new SplashPresenter(toolbarOwner);
//        }
    }
}
