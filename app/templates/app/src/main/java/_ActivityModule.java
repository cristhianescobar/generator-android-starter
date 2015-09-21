package <%= appPackage %>;

import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module(
        addsTo = ApplicationModule.class,
        library = true)
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    public BaseActivity providesActivity() {
        return activity;
    }

    @Provides
    public ToolbarOwner providesToolbarOwner(BaseActivity activity) {
        return activity.toolbarOwner;
    }
}
