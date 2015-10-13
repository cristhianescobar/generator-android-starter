package <%= appPackage %>.screen.<%= screenPackage %>;

import <%= appPackage %>.ApplicationComponent;
import <%= appPackage %>.R;
import <%= appPackage %>.app.ActivityComponent;
import <%= appPackage %>.util.dagger.DaggerScope;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.ScreenComponentFactory;

import flow.path.Path;

@Layout(R.layout.screen_<%= screenPackage %>)
public class <%= screenName %>Screen extends Path implements ScreenComponentFactory<ActivityComponent> {
    private <%= screenName %>Component component;

    @Override
    public Object createComponent(ActivityComponent parent) {
        return Dagger<%= screenName %>Screen_<%= screenName %>Component.builder()
                .activityComponent(parent)
                .<%= screenPackage %>Module(new <%= screenName %>Module())
                .build();
    }

    @dagger.Component(dependencies = ActivityComponent.class, modules = <%= screenName %>Module.class)
    @DaggerScope(<%= screenName %>Component.class)
    public interface <%= screenName %>Component extends ApplicationComponent {
        void inject(<%= screenName %>View view);
        void inject(<%= screenName %>Presenter presenter);
    }


    @dagger.Module
    class <%= screenName %>Module {
//        @Provides
//        @DaggerScope(<%= screenName %>Component.class)
//        public <%= screenName %>Presenter providesPresenter(ToolbarOwner toolbarOwner, Validator validator, ApiService apiService, JsonSharedPreferencesRepository prefsRepository, InputMethodManager inputMethodManager) {
//            return new <%= screenName %>Presenter(toolbarOwner, validator, apiService, prefsRepository, inputMethodManager);
//        }
    }
}
