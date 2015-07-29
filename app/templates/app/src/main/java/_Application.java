package <%= appPackage %>;

import <%= appPackage %>.BuildConfig;

import dagger.ObjectGraph;
import mortar.Mortar;
import mortar.MortarScope;
import timber.log.Timber;
import android.support.multidex.MultiDex;
import <%= appPackage %>.util.logging.CrashReportingTree;

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

		Timber.d("Initialising application object graph...");
		ObjectGraph objectGraph = ObjectGraph.create(new ApplicationModule(this));

		// Eagerly validate development builds (too slow for production).
		Timber.d("Creating root Mortar scope...");
		rootScope = Mortar.createRootScope(BuildConfig.DEBUG, objectGraph);
	}

	@Override
	public Object getSystemService(String name) {
		if (Mortar.isScopeSystemService(name)) {
			return rootScope;
		}
		return super.getSystemService(name);
	}
}
