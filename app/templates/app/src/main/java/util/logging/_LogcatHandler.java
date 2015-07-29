package <%= appPackage %>.util.logging;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.exception.ExceptionUtils;
import timber.log.Timber;
import android.util.Log;

/**
 * A {@link java.util.logging.Handler} that outputs log messages to logcat
 */
public class LogcatHandler extends Handler {
	private static final Formatter THE_FORMATTER = new Formatter() {
		@Override
		public String format(LogRecord r) {
			Throwable thrown = r.getThrown();
			if (thrown != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(r.getMessage()).append("\n");
				sb.append(ExceptionUtils.getStackTrace(thrown));
				return sb.toString();
			} else {
				return r.getMessage();
			}
		}
	};

	public LogcatHandler() {
		setFormatter(THE_FORMATTER);
	}

	@Override
	public void close() {
		// No need to close, but must implement abstract method.
	}

	@Override
	public void flush() {
		// No need to flush, but must implement abstract method.
	}

	@Override
	public void publish(LogRecord record) {
		int level = getAndroidLevel(record.getLevel());

		try {
			String message = getFormatter().format(record);
			switch(level) {
				case Log.ERROR:
					Timber.e(message);
					break;
				case Log.WARN:
					Timber.w(message);
					break;
				case Log.INFO:
					Timber.i(message);
					break;
				case Log.DEBUG:
					Timber.d(message);
					break;
				default:
					break;
			}
		} catch (RuntimeException e) {
			Timber.e("AndroidHandler", "Error logging message.", e);
		}
	}

	/**
	 * Converts a {@link java.util.logging.Logger} logging level into an Android one.
	 *
	 * @param level The {@link java.util.logging.Logger} logging level.
	 *
	 * @return The resulting Android logging level.
	 */
	static int getAndroidLevel(Level level) {
		int value = level.intValue();
		if (value >= 1000) { // SEVERE
			return Log.ERROR;
		} else if (value >= 900) { // WARNING
			return Log.WARN;
		} else if (value >= 800) { // INFO
			return Log.INFO;
		} else {
			return Log.DEBUG;
		}
	}
}
