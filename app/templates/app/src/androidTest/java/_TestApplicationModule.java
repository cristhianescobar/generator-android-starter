package <%= appPackage %>;

import android.view.inputmethod.InputMethodManager;

import <%= appPackage %>.analytics.AnalyticsModule;
import <%= appPackage %>.analytics.EventTracker;
import <%= appPackage %>.android.AndroidModule;
import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.util.gson.GsonModule;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module(
        injects = TestApplication.class,
        includes = {
                AndroidModule.class,
                AnalyticsModule.class,
                EnvironmentModule.class,
                GsonModule.class
        },
        library = true
)
public class TestApplicationModule {
    private android.app.Application application;

    public TestApplicationModule(android.app.Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    android.app.Application provideApplication() {
        return application;
    }


    @Provides
    MixpanelAPI provideMixpanelApi() {
        return mock(MixpanelAPI.class);
    }

    @Provides
    EventTracker provideEventTracker() {
        return mock(EventTracker.class);
    }


    @Provides
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    InputMethodManager provideInputMethodManager() {
        return mock(InputMethodManager.class);
    }
}
