package <%= appPackage %>.android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing Android framework dependencies
 */

@Module
public class AndroidModule {

	@Provides
	SharedPreferences provideSharedPreferences(Application application) {
		return application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
	}

	@Provides
	LayoutInflater provideLayoutInflater(Application application) {
		return (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Provides
    InputMethodManager provideInputMethodManager(Application application) {
		return (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Provides
	ActivityManager provideActivityManager(Application application) {
		return (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
	}
}
