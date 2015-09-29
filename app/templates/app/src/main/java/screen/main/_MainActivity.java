package <%= appPackage %>.screen.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cocosw.bottomsheet.BottomSheet;
import <%= appPackage %>.ActivityModule;
import <%= appPackage %>.R;
import <%= appPackage %>.SharedPreferencesKeys;
import <%= appPackage %>.model.User;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.screen.home.HomeScreen;
import <%= appPackage %>.screen.splash.SplashScreen;
import <%= appPackage %>.toolbar.MenuItemSelectionHandler;
import <%= appPackage %>.toolbar.ToolbarConfig;
import <%= appPackage %>.toolbar.ToolbarMenuItem;
import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.BaseActivity;
import <%= appPackage %>.util.dagger.ObjectGraphService;
import <%= appPackage %>.util.flow.FlowHistoryDevHelper;
import <%= appPackage %>.util.flow.GsonParceler;
import <%= appPackage %>.util.flow.HandlesBack;
import com.google.gson.Gson;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Bind;
import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.path.Path;
import flow.path.PathContainerView;
import mortar.MortarScope;
import mortar.MortarScopeDevHelper;
import mortar.bundler.BundleServiceRunner;
import timber.log.Timber;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_LAUNCHER;
import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
import static com.atomicleopard.expressive.Expressive.map;
import static mortar.bundler.BundleServiceRunner.getBundleServiceRunner;

public class MainActivity extends BaseActivity implements Flow.Dispatcher {

    @Inject
    MixpanelAPI mixpanel;


    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    JsonSharedPreferencesRepository jsonSharedPreferencesRepository;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private ToolbarMenuItem toolbarMenuItem;
    private PathContainerView container;
    private HandlesBack containerAsHandlesBack;
    private FlowDelegate flowDelegate;

    private Map<Integer, MenuItemSelectionHandler> menuItemSelectionHandlers;


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
        Path newScreen = traversal.destination.top();
        String title = newScreen.getClass().getSimpleName();
        toolbarOwner.setConfig(new ToolbarConfig.Builder().enableHomeAsUp(false).title(title).visible(true).build());
        container.dispatch(traversal, callback);
    }

    //region lifecycle events

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isWrongInstance()) {
            finish();
            return;
        }

        GsonParceler parceler = new GsonParceler(new Gson());
        @SuppressWarnings("deprecation") FlowDelegate.NonConfigurationInstance nonConfig =
                (FlowDelegate.NonConfigurationInstance) getLastNonConfigurationInstance();

        initActivityScope(savedInstanceState);

        ObjectGraphService.inject(this, this);

        getBundleServiceRunner(activityScope).onCreate(savedInstanceState);

        inflateViewContainerLayout();
        configureToolbar();
        container = (PathContainerView) findViewById(R.id.container);
        containerAsHandlesBack = (HandlesBack) container;
        flowDelegate = FlowDelegate.onCreate(
                nonConfig,
                getIntent(),
                savedInstanceState,
                parceler,
                History.single(getFirstScreen()), this);
    }

    protected Path getFirstScreen() {
        if (userAccountExists()) {
            return new HomeScreen();
        } else {
            Timber.w("No user account found, redirecting to splash screen");
            return new SplashScreen();
        }
    }

    protected boolean userAccountExists() {
        return jsonSharedPreferencesRepository.getObject(SharedPreferencesKeys.USER_ACCOUNT, User.class) != null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowDelegate.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowDelegate.onResume();
    }

    @Override
    protected void onPause() {
        flowDelegate.onPause();
        super.onPause();
    }

    @Override
    public Object getSystemService(String name) {
        if (flowDelegate != null) {
            Object flowService = flowDelegate.getSystemService(name);
            if (flowService != null) return flowService;
        }

        return activityScope != null && activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowDelegate.onSaveInstanceState(outState);
        getBundleServiceRunner(this).
                onSaveInstanceState(outState);
    }

    /**
     * Inform the view about back events.
     */
    @Override
    public void onBackPressed() {
        if (!containerAsHandlesBack.onBackPressed()) super.onBackPressed();
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

    /**
     * Configure the action bar menu as required by {@link ToolbarOwner.Activity}.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        if (toolbarMenuItem != null) {
            menu.add(toolbarMenuItem.title)
                    .setShowAsActionFlags(SHOW_AS_ACTION_ALWAYS)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            toolbarMenuItem.action.call();
                            return true;
                        }
                    });
        }
        menu.add("Log Scope Hierarchy")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Timber.d(MortarScopeDevHelper.scopeHierarchyToString(activityScope));
                        return true;
                    }
                });
        menu.add("Log Flow History")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Timber.d(FlowHistoryDevHelper.flowHistoryToString(Flow.get(getContext()).getHistory()));
                        return true;
                    }
                });
        return true;
    }

    @Override
    protected void onDestroy() {
        // activityScope may be null in case isWrongInstance() returned true in onCreate()
        if (isFinishing() && activityScope != null) {
            mixpanel.flush();
            toolbarOwner.dropView(this);
            toolbarOwner.setConfig(null);

            activityScope.destroy();
            activityScope = null;
        }
        super.onDestroy();
    }

    //endregion


    @Override
    public void setMenu(ToolbarMenuItem menu) {
        if (menu != toolbarMenuItem) {
            toolbarMenuItem = menu;
            invalidateOptionsMenu();
        }
    }

    @Override
    public void setToolbarConfig(ToolbarConfig config) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        CharSequence title = config.getTitle();
        actionBar.setTitle(title != null ? title : getString(R.string.app_name));

        if (config.isVisible() && !actionBar.isShowing()) {
            actionBar.show();

            // since actionbar is in overlay mode, set the container padding to compensate
            //mainView.setPadding(0, getToolbarHeight(), 0, 0);
        } else if (!config.isVisible() && actionBar.isShowing()) {
            actionBar.hide();

            // remove padding so we get full bleed when action bar is hidden
            //mainView.setPadding(0, 0, 0, 0);
        }

        actionBar.setDisplayHomeAsUpEnabled(config.isHomeAsUpEnabled());
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
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
    private void configureToolbar() {
        setSupportActionBar(toolbar);

        menuItemSelectionHandlers = map(
                android.R.id.home, new UpSelectionHandler(),
                R.id.action_environment, new SwitchEnvironmentSelectionHandler()
        );

        toolbarOwner = new ToolbarOwner();
        toolbarOwner.takeView(this);
    }

    /**
     * Inflate the view container layout and inject our view components
     */
    private void inflateViewContainerLayout() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * Initalise the root activity Mortar scope
     */
    protected void initActivityScope(Bundle savedInstanceState) {
        MortarScope parentScope = MortarScope.getScope(getApplication());
        String scopeName = getLocalClassName() + "-task-" + getTaskId();
        activityScope = parentScope.findChild(scopeName);
        if (activityScope == null) {
            activityScope = parentScope.buildChild()
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(
                            ObjectGraphService.SERVICE_NAME,
                            ObjectGraphService.create(parentScope, new ActivityModule(this)))
                    .build(scopeName);
        }
    }



    private int getToolbarHeight() {
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return height;
    }

    private class UpSelectionHandler implements MenuItemSelectionHandler {
        @Override
        public boolean execute() {
            return containerAsHandlesBack.onBackPressed();
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
