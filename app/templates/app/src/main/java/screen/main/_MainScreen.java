package <%= appPackage %>.screen.main;

import dagger.Provides;
import flow.Flow;
import mortar.Blueprint;
import <%= appPackage %>.ApplicationModule;
import <%= appPackage %>.actionbar.ActionBarModule;

/**
 * The main screen for the application. This screen is responsible for setting the first
 * screen as well as creating the parent MortarScope for the application.
 */
public class MainScreen implements Blueprint {

	@Override
	public String getMortarScopeName() {
		return getClass().getName();
	}

	@Override
	public Object getDaggerModule() {
		return new Module();
	}

	@dagger.Module(
			includes = { ActionBarModule.class },
			injects = { MainActivity.class, MainView.class },
			addsTo = ApplicationModule.class,
			library = true
	)
	public static class Module {
		@Provides Flow provideFlow(MainPresenter presenter) {
			return presenter.getFlow();
		}
	}
}
