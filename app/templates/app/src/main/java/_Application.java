package <%= appPackage %>;

import <%= appPackage %>.analytics.AnalyticsModule;
import <%= appPackage %>.android.AndroidModule;
import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.util.gson.GsonModule;
import <%= appPackage %>.util.logging.CrashReportingTree;

import timber.log.Timber;

public class Application extends android.app.Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        //MultiDex.install(this);

        setupTimber(BuildConfig.DEBUG);
        Timber.i("Starting application");


        getComponent().inject(this);
    }

    public ApplicationComponent getComponent() {
        if (component == null) {
            component = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .androidModule(new AndroidModule())
                    .gsonModule(new GsonModule())
                    .environmentModule(new EnvironmentModule())
                    .analyticsModule(new AnalyticsModule())
                    .build();
        }
        return component;
    }


    protected void setupTimber(boolean isDebug) {
        if (isDebug) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // TODO Crashlytics.start(this);
            Timber.plant(new CrashReportingTree());
        }
    }
}
