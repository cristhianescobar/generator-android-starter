package <%= appPackage %>.util.lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public abstract class LifecycleActivity extends AppCompatActivity {

  public abstract LifecycleOwner getLifecycleOwner();

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);
  }
  @Override
  protected void onStart() {
    super.onStart();
    getLifecycleOwner().onActivityStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    getLifecycleOwner().onActivityResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    getLifecycleOwner().onActivityPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    getLifecycleOwner().onActivityStop();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    getLifecycleOwner().onActivityResult(requestCode, resultCode, data);
  }
}
