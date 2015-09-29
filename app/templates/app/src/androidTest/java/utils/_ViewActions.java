package <%= appPackage %>.utils;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.view.View;

import org.hamcrest.Matcher;

import java.io.File;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static <%= appPackage %>.utils.ViewMatchers.anyView;

public class ViewActions {

    /**
     * Perform action of waiting for a specific view id.
     * <p/>
     * E.g.:
     * onView(isRoot()).perform(waitId(R.id.dialogEditor, Sampling.SECONDS_15));
     *
     * @param viewId
     * @param millis
     * @return
     */
    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

    /**
     * Perform action of waiting for a specific time. Useful when you need
     * to wait for animations to end and Espresso fails at waiting.
     * <p/>
     * E.g.:
     * onView(isRoot()).perform(waitAtLeast(Sampling.SECONDS_15));
     *
     * @param millis
     * @return
     */
    public static ViewAction waitAtLeast(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return anyView();
            }

            @Override
            public String getDescription() {
                return "wait for at least " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    /**
     * Perform action of waiting until UI thread is free.
     * <p/>
     * E.g.:
     * onView(isRoot()).perform(waitUntilIdle());
     *
     * @return
     */
    public static ViewAction waitUntilIdle() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return anyView();
            }

            @Override
            public String getDescription() {
                return "wait until UI thread is free";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

    public static ViewAction screenshot(final String description) {
        final Thread testThread = Thread.currentThread();
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return anyView();
            }

            @Override
            public String getDescription() {
                return "Takes a screenshot of matching view";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                String tag = description.replaceAll("[^a-zA-Z0-9_-]", "_");

                final File dir = ScreenshotTaker.obtainScreenshotDirectory(view.getContext(), testThread);
                final String file = ScreenshotTaker.obtainScreenshotName(tag);

                try {
                    new ScreenshotTaker(dir, file).takeScreenShot(view);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
