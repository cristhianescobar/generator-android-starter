package <%= appPackage %>;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import <%= appPackage %>.environment.Environment;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.service.ApiService;
import <%= appPackage %>.service.StubApiService;
import <%= appPackage %>.util.lifecycle.LifecycleOwner;

import javax.inject.Singleton;
import javax.validation.Validation;
import javax.validation.Validator;

import dagger.Module;
import dagger.Provides;
import flow.Flow;
import retrofit.RestAdapter;

@Module
public class ApplicationModule {
    private android.app.Application application;

    public ApplicationModule(android.app.Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    android.app.Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getApplicationContext();
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
