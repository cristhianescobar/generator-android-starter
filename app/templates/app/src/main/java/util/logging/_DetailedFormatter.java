package <%= appPackage %>.util.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DetailedFormatter extends Formatter {

	private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime();
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * Format a log record with the format %isodatetime %loglevel [%threadid] %loggername: %message
	 *
	 * @param r a log record
	 * @return a formatted log record
	 */
	@Override
	public String format(LogRecord r) {
		StringBuilder sb = new StringBuilder();
		sb.append(ISO_DATE_TIME_FORMATTER.print(r.getMillis())).append(" ");
		sb.append(r.getLevel().getName()).append(" ");
		sb.append("[").append(r.getThreadID()).append("] ");
		sb.append(r.getLoggerName()).append(": ");
		sb.append(formatMessage(r)).append(LINE_SEPARATOR);
		if (r.getThrown() != null) {
			sb.append("Stacktrace: ");
			sb.append(ExceptionUtils.getStackTrace(r.getThrown()));
		}
		return sb.toString();
	}
}
