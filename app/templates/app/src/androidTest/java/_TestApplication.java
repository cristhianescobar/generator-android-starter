package <%= appPackage %>;

import java.lang.reflect.Method;

import org.robolectric.Robolectric;
import org.robolectric.TestLifecycleApplication;

import dagger.ObjectGraph;
import mortar.Mortar;

public class TestApplication extends Application implements TestLifecycleApplication {

	@Override
	public void onCreate() {
		ObjectGraph objectGraph = ObjectGraph.create(new TestApplicationModule(Robolectric.application));
		rootScope = Mortar.createRootScope(false, objectGraph);
	}

	@Override
	public void beforeTest(Method method) {
	}

	@Override
	public void prepareTest(Object test) {
	}

	@Override
	public void afterTest(Method method) {

	}
}
