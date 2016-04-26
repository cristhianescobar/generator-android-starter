package <%= appPackage %>.screen.splash;

import android.os.Bundle;

import <%= appPackage %>.screen.login.LoginScreen;
import <%= appPackage %>.screen.register.RegisterScreen;
import <%= appPackage %>.toolbar.ToolbarConfig;
import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

import javax.inject.Inject;

import flow.Flow;
import timber.log.Timber;

class SplashPresenter extends BaseViewPresenter<SplashView> {
    private final ToolbarOwner toolbarOwner;

    @Inject
    SplashPresenter(ToolbarOwner toolbarOwner) {
        this.toolbarOwner = toolbarOwner;
    }

    public void login() {
        Timber.i("Navigating to login screen");
        Flow.get(getView()).set(new LoginScreen());
    }

    public void register() {
        Timber.i("Navigating to register screen");
        Flow.get(getView()).set(new RegisterScreen());
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);

        SplashView view = getView();
        if (view != null) {
            hideActionBar();
        }
    }

    void hideActionBar() {
        ToolbarConfig config = new ToolbarConfig.Builder()
                .visible(false)
                .build();
        toolbarOwner.setConfig(config);
    }
}
