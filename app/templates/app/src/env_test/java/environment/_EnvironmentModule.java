package <%= appPackage %>.environment;

import android.content.SharedPreferences;

import <%= appPackage %>.R;
import <%= appPackage %>.SharedPreferencesKeys;

import java.util.Collections;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

import static com.atomicleopard.expressive.Expressive.map;


@Module
public class EnvironmentModule {
    private static final Environment LOCAL_ENVIRONMENT = new LocalEnvironment();
    private static final Environment DEVELOPMENT_ENVIRONMENT = new DevelopmentEnvironment();
    private static final Environment UAT_ENVIRONMENT = new UatEnvironment();

    private static Map<Integer, Environment> ENVIRONMENTS = map(
            R.id.action_environment_local, LOCAL_ENVIRONMENT,
            R.id.action_environment_development, DEVELOPMENT_ENVIRONMENT,
            R.id.action_environment_uat, UAT_ENVIRONMENT
    );

    @Provides
    @Singleton
    public Environment provideEnvironment(SharedPreferences preferences) {
        int environmentId = preferences.getInt(SharedPreferencesKeys.ENVIRONMENT, -1);
        Environment environment = getEnvironment(environmentId);
        Timber.i("Environment is set to <%s>", environment.getName());
        return environment;
    }

    @Provides
    @Singleton
    public Map<Integer, Environment> provideEnvironments() {
        return Collections.unmodifiableMap(ENVIRONMENTS);
    }

    /**
     * Get the environment with the given name or {@link EnvironmentModule#LOCAL_ENVIRONMENT} if no match found.
     *
     * @param environmentId the environment id.
     * @return the corresponding environment.
     */
    private Environment getEnvironment(int environmentId) {
        Environment environment = ENVIRONMENTS.get(environmentId);
        return environment != null ? environment : LOCAL_ENVIRONMENT;
    }
}
