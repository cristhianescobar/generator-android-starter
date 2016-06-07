package <%= appPackage %>;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.GsonBuilder;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import <%= appPackage %>.analytics.AnalyticsModule;
import <%= appPackage %>.analytics.EventTracker;
import <%= appPackage %>.android.AndroidModule;
import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.service.ApiService;
import <%= appPackage %>.util.gson.GsonModule;

import javax.inject.Singleton;
import javax.validation.Validator;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                AndroidModule.class,
                AnalyticsModule.class,
                EnvironmentModule.class,
                GsonModule.class
        }
)
public interface ApplicationComponent {

    void inject(Application app);

    Context context();

    EventTracker eventTracker();

    MixpanelAPI mixpanelApi();

    ApiService apiService();

    Validator validator();

    JsonSharedPreferencesRepository jsonSharedPreferencesRepository();

    GsonBuilder gsonBuilder();

    SharedPreferences sharedPreferences();

    InputMethodManager inputMethodManager();

}
