package <%= appPackage %>.util.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Convenience wrapper around {@link java.util.logging.Logger} that configures the logger
 * appropriately and provides sane log method names.
 */
public class Logger {

	static {
		java.util.logging.Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(Level.FINE);

		// Remove stinkin android handler plus any other garbage we haven't explicitly configured.
		for (Handler handler : rootLogger.getHandlers()) {
			rootLogger.removeHandler(handler);
		}

		rootLogger.addHandler(new LogcatHandler());
	}

	public static <T> Logger getLogger(Class<T> type) {
		return new Logger(java.util.logging.Logger.getLogger(type.getName()));
	}

	private java.util.logging.Logger logger;

	Logger(java.util.logging.Logger logger) {
		this.logger = logger;
	}

	public void debug(String content) {
		if (shouldLogDebug()) {
			logger.fine(content);
		}
	}

	public void debug(String format, Object... args) {
		if (shouldLogDebug()) {
			logger.fine(String.format(format, args));
		}
	}

	public void info(String content) {
		if (shouldLogInfo()) {
			logger.info(content);
		}
	}

	public void info(String format, Object... args) {
		if (shouldLogInfo()) {
			logger.info(String.format(format, args));
		}
	}

	public void warn(String content) {
		if (shouldLogWarn()) {
			logger.warning(content);
		}
	}

	public void warn(String format, Object... args) {
		if (shouldLogWarn()) {
			logger.warning(String.format(format, args));
		}
	}

	public void warn(Throwable error, String format, Object... args) {
		if (shouldLogWarn()) {
			logger.log(Level.WARNING, String.format(format, args), error);
		}
	}

	public void error(String content) {
		if (shouldLogError()) {
			logger.severe(content);
		}
	}

	public void error(String format, Object... args) {
		if (shouldLogError()) {
			logger.severe(String.format(format, args));
		}
	}

	public void error(Throwable error, String format, Object... args) {
		if (shouldLogError()) {
			logger.log(Level.SEVERE, String.format(format, args), error);
		}
	}

	public boolean shouldLogDebug() {
		return logger.isLoggable(Level.FINE);
	}

	public boolean shouldLogInfo() {
		return logger.isLoggable(Level.INFO);
	}

	public boolean shouldLogWarn() {
		return logger.isLoggable(Level.WARNING);
	}

	public boolean shouldLogError() {
		return logger.isLoggable(Level.SEVERE);
	}
}
