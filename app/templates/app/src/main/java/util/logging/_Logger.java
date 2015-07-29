package <%= appPackage %>.util.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Convenience wrapper around {@link java.util.logging.Logger} that configures the logger
 * appropriately and provides sane log method names.
 */
public class Logger {

	public static void installLoggerHandler() {
		java.util.logging.Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(Level.FINE);

		// Remove stinkin android handler plus any other garbage we haven't explicitly configured.
		for (Handler handler : rootLogger.getHandlers()) {
			rootLogger.removeHandler(handler);
		}

		rootLogger.addHandler(new LogcatHandler());
	}
}
