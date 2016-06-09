package <%= appPackage %>.app;

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
}
