package <%= appPackage %>.app;

import <%= appPackage %>.toolbar.ToolbarOwner;

import dagger.Module;
import dagger.Provides;

@Module
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
