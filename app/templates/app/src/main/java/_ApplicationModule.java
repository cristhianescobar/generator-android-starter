package <%= appPackage %>;

import android.content.SharedPreferences;

import <%= appPackage %>.analytics.AnalyticsModule;
import <%= appPackage %>.android.AndroidModule;
import <%= appPackage %>.environment.Environment;
import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.screen.main.MainActivity;
import <%= appPackage %>.service.ApiService;
import <%= appPackage %>.service.StubApiService;
import <%= appPackage %>.util.gson.GsonModule;
import <%= appPackage %>.util.lifecycle.LifecycleOwner;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import javax.inject.Singleton;
import javax.validation.Validation;
import javax.validation.Validator;

import dagger.Module;
import dagger.Provides;
import flow.Flow;
import retrofit.RestAdapter;

@Module(
        includes = {
                AndroidModule.class,
                AnalyticsModule.class,
                EnvironmentModule.class,
                GsonModule.class
        },
        injects = {
                MainActivity.class
        },
        library = true
)
public class ApplicationModule {
    private Application application;

    ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    android.app.Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Bus providesBus() {
        return new Bus(); // my name is Otto and I love to get blotto
    }

    @Provides
    @Singleton
    JsonSharedPreferencesRepository provideSharedPreferenceRepository(GsonBuilder gsonBuilder, SharedPreferences sharedPreferences) {
        return new JsonSharedPreferencesRepository(gsonBuilder, sharedPreferences);
    }

    @Provides
    @Singleton
    ApiService provideApiService(Environment environment) {
        if (environment.getName().equals("Local")) {
            return new StubApiService();
        } else {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(environment.getApiHost() + environment.getApiBasePath())
                    .build();
            return restAdapter.create(ApiService.class);
        }
    }

    @Provides
    @Singleton
    Validator provideValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Provides
    @Singleton
    public LifecycleOwner providesLifeCycleOwner() {
        return new LifecycleOwner();
    }


    @Singleton
    @Provides
    public Flow providesFlow(android.app.Application application) {
        return Flow.get(application);
    }
}
