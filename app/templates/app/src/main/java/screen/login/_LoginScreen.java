package <%= appPackage %>.screen.login;

import <%= appPackage %>.ApplicationComponent;
import <%= appPackage %>.R;
import <%= appPackage %>.app.ActivityComponent;
import <%= appPackage %>.util.dagger.DaggerScope;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.ScreenComponentFactory;

import flow.path.Path;

@Layout(R.layout.screen_login)
public class LoginScreen extends Path implements ScreenComponentFactory<ActivityComponent> {

    @Override
    public Object createComponent(ActivityComponent parent) {
        return DaggerLoginScreen_LoginComponent.builder()
                .activityComponent(parent)
                .loginModule(new LoginModule())
                .build();
    }

    @dagger.Component(dependencies = ActivityComponent.class, modules = LoginModule.class)
    @DaggerScope(LoginComponent.class)
    public interface LoginComponent extends ApplicationComponent {
        void inject(LoginView view);
        void inject(LoginPresenter presenter);
    }


    @dagger.Module
    public class LoginModule {
//        @Provides
//        @DaggerScope(LoginComponent.class)
//        public LoginPresenter providesPresenter(ToolbarOwner toolbarOwner) {
//            return new LoginPresenter(toolbarOwner);
//        }

    }
}
