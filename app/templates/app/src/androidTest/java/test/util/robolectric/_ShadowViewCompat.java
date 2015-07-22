package <%= appPackage %>.test.util.robolectric;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Shadow the real {@link android.support.v4.view.ViewCompat} because setElevation isn't supported
 * on SDK 18 which is required by Robolectric.
 */
@Implements(ViewCompat.class)
public class ShadowViewCompat {
	@Implementation
	public static void setElevation(View view, float elevation) {
		// noop
	}
}
