package <%= appPackage %>.test.util;

import flow.Backstack;
import flow.Flow;

public class FlowTestHelper {

	public static class MockFlowListener implements Flow.Listener {
		public Object lastScreen;
		public Flow.Direction lastDirection;

		@Override
		public void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {
			lastDirection = direction;
			lastScreen = entries.current().getScreen();
		}


	}

	public static Flow createFlow(Object screen, Flow.Listener listener) {
		return new Flow(Backstack.fromUpChain(screen), listener);
	}
}
