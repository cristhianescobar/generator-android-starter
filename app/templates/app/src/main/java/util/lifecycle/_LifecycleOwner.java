package <%= appPackage %>.util.lifecycle;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

/**
 * Pass along the Android lifecycle events.
 * Tip to http://stackoverflow.com/questions/21927990/mortar-flow-with-third-party-libraries-hooked-to-activity-lifecycle/21959529?noredirect=1#21959529
 * on this one, mostly.
 */
@Singleton
public class LifecycleOwner implements ActivityLifecycleListener {

  private List<ActivityLifecycleListener> registeredListeners
      = new ArrayList<>();

  @Inject
  public LifecycleOwner() {
  }

  public void register(@NotNull ActivityLifecycleListener listener) {
    registeredListeners.add(listener);
  }

  public void unregister(@NotNull ActivityLifecycleListener listener) {
    registeredListeners.remove(listener);
  }

  @Override public void onActivityResume() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityResume();
    }
  }

  @Override public void onActivityPause() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityPause();
    }
  }

  @Override public void onActivityStart() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityStart();
    }
  }

  @Override public void onActivityStop() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityStop();
    }
  }

  @Override
  public void onLowMemory() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onLowMemory();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityResult(requestCode, resultCode, data);
    }
  }

  /**
   * Creates a copy of the {@link #registeredListeners} list in order to safely iterate the
   * listeners. This was added to avoid {@link java.util.ConcurrentModificationException} that may
   * be triggered when listeners are added / removed during one of the lifecycle events.
   */
  private ActivityLifecycleListener[] getArrayCopyOfRegisteredListeners() {
    ActivityLifecycleListener[] registeredListenersArray =
        new ActivityLifecycleListener[registeredListeners.size()];
    registeredListeners.toArray(registeredListenersArray);
    return registeredListenersArray;
  }
}
