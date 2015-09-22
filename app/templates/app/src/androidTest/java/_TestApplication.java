package <%= appPackage %>;

import <%= appPackage %>.util.dagger.ObjectGraphService;

import org.robolectric.Robolectric;
import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

import dagger.ObjectGraph;
import mortar.MortarScope;

public class TestApplication extends Application implements TestLifecycleApplication {

    @Override
    public void onCreate() {
        rootScope = MortarScope.buildRootScope()
                .withService(
                        ObjectGraphService.SERVICE_NAME,
                        ObjectGraph.create(new TestApplicationModule(Robolectric.application)))
                .build("Root");
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
