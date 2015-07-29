package <%= appPackage %>.analytics;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.json.JSONObject;

import com.atomicleopard.expressive.Expressive;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import timber.log.Timber;

/**
 * Track application events via Mixpanel
 */
public class MixpanelEventTracker implements EventTracker {

	private final MixpanelAPI mixpanel;

	@Inject
	public MixpanelEventTracker(MixpanelAPI mixpanel) {
		this.mixpanel = mixpanel;
	}

	@Override
	public void track(String format, Object... args) {
		trackInternal(null, format, args);
	}

	@Override
	public void track(Map<String, Object> properties, String format, Object... args) {
		trackInternal(properties, format, args);
	}

	@Override
	public void trackError(String action, String errorFormat, Object... formatArgs) {
		Map<String, Object> properties = Expressive.map(
				"Action", action,
				"Error", String.format(errorFormat, formatArgs)
		);
		trackInternal(properties, "Error %s", action);
	}

	@Override
	public void trackError(Map<String, Object> properties, String action, String errorFormat, Object... formatArgs) {
		if (properties == null) {
			properties = Expressive.map();
		}

		Map<String, Object> errorProperties = new HashMap<>(properties);
		errorProperties.put("Action", action);
		errorProperties.put("Error", String.format(errorFormat, formatArgs));

		trackInternal(errorProperties, "Error %s", action);
	}

	@Override
	public void identify(String identifier) {
		mixpanel.identify(identifier);
		mixpanel.getPeople().identify(identifier);
		mixpanel.getPeople().set("$email", identifier);
	}

	private void trackInternal(Map<String, Object> properties, String format, Object... formatArgs) {
		if (properties == null) {
			properties = Expressive.map();
		}

		StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
		properties.put("Source", stackTraceElements[2].getClassName());

		String eventName = String.format(format, formatArgs);
		mixpanel.track(eventName, new JSONObject(properties));

		Timber.i("Tracked event `%s` with properties: %s", eventName, properties);
	}
}
