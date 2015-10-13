package <%= appPackage %>.screen.register;

import <%= appPackage %>.ApplicationComponent;
import <%= appPackage %>.R;
import <%= appPackage %>.app.ActivityComponent;
import <%= appPackage %>.util.dagger.DaggerScope;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.ScreenComponentFactory;

import flow.path.Path;


@Layout(R.layout.screen_register)
public class RegisterScreen extends Path implements ScreenComponentFactory<ActivityComponent> {
    private RegisterComponent component;

    @Override
    public Object createComponent(ActivityComponent parent) {
        return DaggerRegisterScreen_RegisterComponent.builder()
                .activityComponent(parent)
                .registerModule(new RegisterModule())
                .build();
    }

    @dagger.Component(dependencies = ActivityComponent.class, modules = RegisterModule.class)
    @DaggerScope(RegisterComponent.class)
    public interface RegisterComponent extends ApplicationComponent {
        void inject(RegisterView view);
        void inject(RegisterPresenter presenter);
    }


    @dagger.Module
    class RegisterModule {
//        @Provides
//        @DaggerScope(RegisterComponent.class)
//        public RegisterPresenter providesPresenter(ToolbarOwner toolbarOwner, Validator validator, ApiService apiService, JsonSharedPreferencesRepository prefsRepository, InputMethodManager inputMethodManager) {
//            return new RegisterPresenter(toolbarOwner, validator, apiService, prefsRepository, inputMethodManager);
//        }
    }
}
