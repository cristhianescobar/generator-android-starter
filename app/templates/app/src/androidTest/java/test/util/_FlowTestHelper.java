package <%= appPackage %>.test.util;

import flow.Flow;
import flow.FlowDelegate;
import flow.History;

public class FlowTestHelper {

    public static class MockFlowDispatcher implements Flow.Dispatcher {
        public Object lastScreen;
        public Flow.Direction lastDirection;

        @Override
        public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
            lastDirection = traversal.direction;
            lastScreen = traversal.origin.top();
        }
    }

    public static FlowDelegate createFlow(Object screen, Flow.Dispatcher dispatcher) {
        //return new Flow(Backstack.fromUpChain(screen), listener);
        return FlowDelegate.onCreate(
                null,
                null,
                null,
                null,
                History.single(screen), dispatcher);
    }
}
