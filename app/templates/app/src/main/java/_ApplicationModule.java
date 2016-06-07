package <%= appPackage %>;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import <%= appPackage %>.environment.Environment;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.service.ApiService;
import <%= appPackage %>.service.StubApiService;

import javax.inject.Singleton;
import javax.validation.Validation;
import javax.validation.Validator;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
    JsonSharedPreferencesRepository provideSharedPreferenceRepository(GsonBuilder gsonBuilder, SharedPreferences sharedPreferences) {
        return new JsonSharedPreferencesRepository(gsonBuilder, sharedPreferences);
    }

    @Provides
    @Singleton
    ApiService provideApiService(Environment environment, OkHttpClient client) {
         if (environment.getName().equals("Local")) {
             return new StubApiService();
         } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(environment.getApiHost() + environment.getApiBasePath())
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
         }
     }
 
     @Provides
     @Singleton
     OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides
    @Singleton
    Validator provideValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
