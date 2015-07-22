package <%= appPackage %>.screen.main;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.squareup.otto.Bus;

import android.os.Bundle;
import flow.Parcer;
import mortar.Blueprint;
import <%= appPackage %>.SharedPreferencesKeys;
import <%= appPackage %>.model.User;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.screen.home.HomeScreen;
import <%= appPackage %>.screen.splash.SplashScreen;
import <%= appPackage %>.util.flow.FlowOwner;
import <%= appPackage %>.util.logging.Logger;

@Singleton
public class MainPresenter extends FlowOwner<Blueprint, MainView> {
	private static final Logger LOG = Logger.getLogger(MainPresenter.class);

	private final JsonSharedPreferencesRepository sharedPreferences;
	private final Bus bus;

	@Inject
	public MainPresenter(Parcer<Object> parcer, JsonSharedPreferencesRepository sharedPreferences, Bus bus) {
		super(parcer);
		this.sharedPreferences = sharedPreferences;
		this.bus = bus;
	}

	@Override public void onLoad(Bundle savedInstanceState) {
		LOG.debug("Registering with otto to receive bus events");
		bus.register(this);

		super.onLoad(savedInstanceState);
	}

	@Override public void dropView(MainView view) {
		LOG.debug("Unregistering with otto");
		bus.unregister(this);

		super.dropView(view);
	}

	@Override
	protected Blueprint getFirstScreen() {
		if (userAccountExists()) {
			return new HomeScreen();
		} else {
			LOG.warn("No user account found, redirecting to splash screen");
			return new SplashScreen();
		}
	}

	protected boolean userAccountExists() {
		return sharedPreferences.getObject(SharedPreferencesKeys.USER_ACCOUNT, User.class) != null;
	}
}
