package <%= appPackage %>;

import static org.mockito.Mockito.mock;

import javax.inject.Singleton;

import com.google.gson.Gson;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.otto.Bus;

import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;
import dagger.Module;
import dagger.Provides;
import flow.Parcer;
import <%= appPackage %>.analytics.EventTracker;
import <%= appPackage %>.util.gson.GsonParcer;
import <%= appPackage %>.util.mortar.BaseView;

@Module(
		injects = BaseView.class,
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
	SharedPreferences provideSharedPreferences() {
		return mock(SharedPreferences.class);
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
	Parcer<Object> provideParcer() {
		return new GsonParcer<>(new Gson());
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
