package <%= appPackage %>.environment;

import <%= appPackage %>.R;

import java.util.Collections;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

import static com.atomicleopard.expressive.Expressive.map;

@Module
public class EnvironmentModule {

    private static final Environment PRODUCTION_ENVIRONMENT = new ProductionEnvironment();

    private static Map<Integer, Environment> ENVIRONMENTS = map(
            R.id.action_environment_production, PRODUCTION_ENVIRONMENT
    );

    @Provides
    @Singleton
    public Environment provideEnvironment() {
        Environment environment = ENVIRONMENTS.get(R.id.action_environment_production);
        Timber.i("Environment is set to <%s>", environment.getName());
        return environment;
    }

    @Provides
    @Singleton
    public Map<Integer, Environment> provideEnvironments() {
        return Collections.unmodifiableMap(ENVIRONMENTS);
    }
}

