package <%= appPackage %>.analytics;

import com.atomicleopard.expressive.Expressive;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import <%= appPackage %>.environment.Environment;

import org.json.JSONObject;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing analytics dependencies.
 */
@Module
public class AnalyticsModule {

	@Provides
	@Singleton
	public MixpanelAPI provideMixpanelApi(android.app.Application application, Environment environment) {
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
