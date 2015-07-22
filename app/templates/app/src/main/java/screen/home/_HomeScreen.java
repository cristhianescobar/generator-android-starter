package <%= appPackage %>.screen.home;

import mortar.Blueprint;

/**
 * This screen might be where you send user's once they're logged in/registered
 * as a starting point for your app.
 *
 * The implementation is incomplete and left to your imagination.
 */
public class HomeScreen implements Blueprint {
	@Override
	public String getMortarScopeName() {
		return getClass().getName();
	}

	@Override
	public Object getDaggerModule() {
		return new Module();
	}

	@dagger.Module
 	class Module {}
}
