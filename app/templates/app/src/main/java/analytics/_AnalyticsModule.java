package <%= appPackage %>.analytics;

import javax.inject.Singleton;

import org.json.JSONObject;

import com.atomicleopard.expressive.Expressive;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import <%= appPackage %>.environment.Environment;

/**
 * Module providing analytics dependencies.
 */
@Module(
		library = true,
		complete = false
)
public class AnalyticsModule {

	@Provides
	@Singleton
	public MixpanelAPI provideMixpanelApi(Application application, Environment environment) {
		MixpanelAPI mixpanel = MixpanelAPI.getInstance(application, environment.getMixpanelToken());
		mixpanel.registerSuperProperties(new JSONObject(Expressive.map("channel", "Android Payments App")));
		return mixpanel;
	}

	@Provides
	@Singleton
	public EventTracker provideEventTracker(MixpanelAPI mixpanel) {
		return new MixpanelEventTracker(mixpanel);
	}
}
