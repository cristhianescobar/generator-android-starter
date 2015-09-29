package <%= appPackage %>;

import android.support.multidex.MultiDex;

import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.util.dagger.ObjectGraphService;
import <%= appPackage %>.util.logging.CrashReportingTree;

import dagger.ObjectGraph;
import mortar.MortarScope;
import timber.log.Timber;

public class Application extends android.app.Application {

    protected MortarScope rootScope;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        Timber.i("Starting application");

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // TODO Crashlytics.start(this);
            Timber.plant(new CrashReportingTree());
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (rootScope == null) {
            Timber.d("Creating root Mortar scope...");
            rootScope = MortarScope.buildRootScope()
                    .withService(
                            ObjectGraphService.SERVICE_NAME,
                            ObjectGraph.create(EnvironmentModule.getModules(this)))
                    .build("Root");
        }

        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }
}
