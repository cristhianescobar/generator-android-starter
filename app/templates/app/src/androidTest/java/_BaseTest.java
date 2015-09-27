package <%= appPackage %>;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;
import <%= appPackage %>.screen.main.MainActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import flow.Flow;
import retrofit.MockRestAdapter;

@RunWith(AndroidJUnit4.class)
abstract public class BaseTest {

    @Inject
    protected MockRestAdapter mockAdapter;

    @Inject
    protected Flow flow;


    @Rule
    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<>(MainActivity.class);


    protected void screenshot(String description) throws Throwable {
        Thread.sleep(100);
        Spoon.screenshot(main.getActivity(), description.replaceAll("[^a-zA-Z0-9_-]", "_"));
    }
}