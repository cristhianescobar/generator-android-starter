package <%= appPackage %>.util.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mortar.ViewPresenter;

/**
 * ViewPresenter that gets lifecycle events.
 */
public class LifecycleViewPresenter<V extends View>
    extends ViewPresenter<V>
    implements ActivityLifecycleListener {

  protected final LifecycleOwner lifecycleOwner;

  public LifecycleViewPresenter(LifecycleOwner lifecycleOwner) {
    this.lifecycleOwner = lifecycleOwner;
  }

  @Override
  protected void onLoad(Bundle savedInstanceState) {
    super.onLoad(savedInstanceState);
    lifecycleOwner.register(this);
    onActivityResume();
  }

  @Override
  public void dropView(V view) {
    onActivityPause();
    super.dropView(view);
  }

  @Override
  protected void onExitScope() {
    super.onExitScope();
    lifecycleOwner.unregister(this);
  }

  @Override
  public void onActivityResume() {
  }

  @Override
  public void onActivityPause() {
  }

  @Override
  public void onActivityStart() {
  }

  @Override
  public void onActivityStop() {
  }

  @Override
  public void onLowMemory() {
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
  }
}
