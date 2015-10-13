package <%= appPackage %>;

import android.support.multidex.MultiDex;

import <%= appPackage %>.analytics.AnalyticsModule;
import <%= appPackage %>.android.AndroidModule;
import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.util.dagger.DaggerService;
import <%= appPackage %>.util.gson.GsonModule;
import <%= appPackage %>.util.logging.CrashReportingTree;

import mortar.MortarScope;
import timber.log.Timber;

public class Application extends android.app.Application {

    private MortarScope mortarScope;

    @Override
    public Object getSystemService(String name) {
        return mortarScope.hasService(name) ? mortarScope.getService(name) : super.getSystemService(name);
    }

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        setupTimber(BuildConfig.DEBUG);
        Timber.i("Starting application");


        getComponent().inject(this);

        mortarScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, component)
                .build("Root");
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
