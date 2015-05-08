package <%= appPackage %>;

import <%= appPackage %>.BuildConfig;

import dagger.ObjectGraph;
import mortar.Mortar;
import mortar.MortarScope;
import <%= appPackage %>.util.logging.Logger;

public class Application extends android.app.Application {
	private static final Logger LOG = Logger.getLogger(Application.class);

	protected MortarScope rootScope;

	@Override
	public void onCreate() {
		super.onCreate();
		MultiDex.install(this);

		LOG.info("Starting application");

		LOG.debug("Initialising application object graph...");
		ObjectGraph objectGraph = ObjectGraph.create(new ApplicationModule(this));

		// Eagerly validate development builds (too slow for production).
		LOG.debug("Creating root Mortar scope...");
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
