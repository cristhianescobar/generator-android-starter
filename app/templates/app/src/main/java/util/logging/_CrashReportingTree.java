package <%= appPackage %>.util.logging;

import android.util.Log;

import timber.log.Timber;

public class CrashReportingTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        // TODO Crashlytics.log(priority, tag, message);

        if (t != null) {
            if (priority == Log.ERROR) {
                // TODO Crashlytics.log(Log.ERROR, tag, message);
            } else if (priority == Log.WARN) {
                // TODO Crashlytics.log(Log.WARN, tag, message);
            }
        }
    }
}
