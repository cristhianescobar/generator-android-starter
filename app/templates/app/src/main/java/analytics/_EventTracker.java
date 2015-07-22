package <%= appPackage %>.analytics;

import java.util.Map;

/**
 * Application event tracker interface.
 */
public interface EventTracker {
	void track(String format, Object... args);

	void track(Map<String, Object> properties, String format, Object... args);

	void trackError(String action, String errorFormat, Object... args);

	void trackError(Map<String, Object> properties, String action, String errorFormat, Object... args);

	void identify(String identifier);
}
