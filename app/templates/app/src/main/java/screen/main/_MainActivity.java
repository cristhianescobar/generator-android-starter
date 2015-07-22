package <%= appPackage %>.screen.main;

import static android.content.Intent.*;
import static com.atomicleopard.expressive.Expressive.map;

import java.util.Map;

import javax.inject.Inject;

import com.cocosw.bottomsheet.BottomSheet;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import <%= appPackage %>.R;
import <%= appPackage %>.SharedPreferencesKeys;
import <%= appPackage %>.actionbar.ActionBarConfig;
import <%= appPackage %>.actionbar.ActionBarOwner;
import <%= appPackage %>.actionbar.MenuItemSelectionHandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import flow.Flow;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;

public class MainActivity extends ActionBarActivity implements ActionBarOwner.View {

  @Inject MixpanelAPI mixpanel;
  @Inject ActionBarOwner actionBarOwner;
  @Inject SharedPreferences sharedPreferences;

  @InjectView(R.id.app_toolbar) Toolbar actionBarToolbar;
  @InjectView(R.id.container) MainView mainView;

  private MortarActivityScope activityScope;
  private Flow mainFlow;
  private Map<Integer, MenuItemSelectionHandler> menuItemSelectionHandlers;

  @Override
  public Object getSystemService(String name) {
    if (Mortar.isScopeSystemService(name)) {
      return activityScope;
    }
    return super.getSystemService(name);
  }

  /**
   * Inform the view about back events.
   */
  @Override
  public void onBackPressed() {
    // Give the view a chance to handle going back. If it declines the honor, let super do its thing.
    if (!mainFlow.goBack()) {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  /**
   * Inform the view about up events.
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    MenuItemSelectionHandler action = menuItemSelectionHandlers.get(item.getItemId());
    if (action != null) {
      return action.execute();
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void setActionBarConfig(ActionBarConfig config) {
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar == null) {
      return;
    }

    String title = config.getTitle();
    actionBar.setTitle(title != null ? title : getString(R.string.app_name));

    if (config.isVisible() && !actionBar.isShowing()) {
      actionBar.show();

      // since actionbar is in overlay mode, set the container padding to compensate
      mainView.setPadding(0, getActionBarHeight(), 0, 0);
    } else if (!config.isVisible() && actionBar.isShowing()) {
      actionBar.hide();

      // remove padding so we get full bleed when action bar is hidden
      mainView.setPadding(0, 0, 0, 0);
    }

    actionBar.setDisplayHomeAsUpEnabled(config.isHomeAsUpEnabled());
  }

  @Override
  public Context getMortarContext() {
    return this;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (isWrongInstance()) {
      finish();
      return;
    }

    initActivityScope(savedInstanceState);
    inflateViewContainerLayout();
    configureActionBar();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // activityScope may be null in case isWrongInstance() returned true in onCreate()
    if (isFinishing() && activityScope != null) {
      mixpanel.flush();
      actionBarOwner.dropView(this);

      MortarScope parentScope = Mortar.getScope(getApplication());
      parentScope.destroyChild(activityScope);
      activityScope = null;
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    activityScope.onSaveInstanceState(outState);
  }

  /**
   * Dev tools and the play store (and others?) launch with a different intent, and so
   * lead to a redundant instance of this activity being spawned. <a
   * href="http://stackoverflow.com/questions/17702202/find-out-whether-the-current-activity-will-be-task-root-eventually-after-pendin"
   * >Details</a>.
   */
  private boolean isWrongInstance() {
    if (!isTaskRoot()) {
      Intent intent = getIntent();
      boolean isMainAction = intent.getAction() != null && intent.getAction().equals(ACTION_MAIN);
      return intent.hasCategory(CATEGORY_LAUNCHER) && isMainAction;
    }
    return false;
  }

  /**
   * Configure the app activity bar
   */
  private void configureActionBar() {
    setSupportActionBar(actionBarToolbar);

    menuItemSelectionHandlers = map(
      android.R.id.home, new UpSelectionHandler(),
      R.id.action_environment, new SwitchEnvironmentSelectionHandler()
    );

    actionBarOwner.takeView(this);
  }

  /**
   * Inflate the view container layout and inject our view components
   */
  private void inflateViewContainerLayout() {
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    mainFlow = mainView.getFlow();
  }

  /**
   * Initalise the root activity Mortar scope
   */
  private void initActivityScope(Bundle savedInstanceState) {
    MortarScope parentScope = Mortar.getScope(getApplication());
    activityScope = Mortar.requireActivityScope(parentScope, new MainScreen());
    Mortar.inject(this, this);
    activityScope.onCreate(savedInstanceState);
  }

  private int getActionBarHeight() {
    TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
    int height = (int) styledAttributes.getDimension(0, 0);
    styledAttributes.recycle();
    return height;
  }

  private class UpSelectionHandler implements MenuItemSelectionHandler {
    @Override
    public boolean execute() {
      return mainFlow.goUp();
    }
  }

  private class SwitchEnvironmentSelectionHandler implements MenuItemSelectionHandler {
    @Override
    public boolean execute() {
      new BottomSheet.Builder(MainActivity.this)
        .title("Choose Environment")
        .sheet(R.menu.menu_environment)
        .listener(new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            sharedPreferences.edit().putInt(SharedPreferencesKeys.ENVIRONMENT, which).apply();
            restartApp();  // required to reinitialise object graph
          }
        })
        .build()
        .show();
      return true;
    }

    private void restartApp() {
      Context context = MainActivity.this;
      Intent mStartActivity = new Intent(context, MainActivity.class);

      int mPendingIntentId = 123456;
      PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);

      AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
      mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);

      System.exit(0);  // die die die!
    }
  }
}
