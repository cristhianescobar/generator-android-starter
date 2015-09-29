package <%= appPackage %>.screen.<%= screenPackage %>;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import <%= appPackage %>.BaseTest;
import <%= appPackage %>.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class <%= screenName %>ScreenTest extends BaseTest {

    /**
     * General test pattern:
     * <p/>
     * 1. Perform action - navigation, button click etc
     * 2. Take screenshot of the view you've got to
     * 3. Verify view you've got is correct by matching views
     * making checks on them
     * <p/>
     * This way when something goes wrong you can visually check
     * what went wrong with your assertions by looking at
     * screenshots in test results
     */
    @Test
    public void testBasicNavigation() throws Throwable {
        String tag;

    }
}
