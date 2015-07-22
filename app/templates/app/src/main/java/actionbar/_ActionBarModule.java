package <%= appPackage %>.actionbar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class ActionBarModule {
	@Provides
	@Singleton
	ActionBarOwner provideActionBarOwner() {
		return new ActionBarOwner();
	}
}
