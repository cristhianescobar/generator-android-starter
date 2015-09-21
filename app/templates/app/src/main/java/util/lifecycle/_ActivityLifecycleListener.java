package <%= appPackage %>.util.lifecycle;

import android.content.Intent;

/**
 * Listen to lifecycle events.
 */
public interface ActivityLifecycleListener {

  public void onActivityResume();

  public void onActivityPause();

  public void onActivityStart();

  public void onActivityStop();

  public void onLowMemory();

  public void onActivityResult(int requestCode, int resultCode, Intent data);
}
