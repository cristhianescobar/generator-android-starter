package <%= appPackage %>.screen.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import <%= appPackage %>.R;
import <%= appPackage %>.analytics.EventTracker;
import <%= appPackage %>.screen.register.RegisterScreen;
import <%= appPackage %>.util.dagger.DaggerService;
import <%= appPackage %>.util.widget.BaseRelativeLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class SplashView extends BaseRelativeLayout<SplashPresenter> {

    @Inject
    SplashPresenter presenter;
    @Inject
    EventTracker eventTracker;

    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.register_button)
    Button registerButton;

    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        DaggerService.<SplashScreen.SplashComponent>getDaggerComponent(context).inject(this);
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        eventTracker.track("Login button clicked");
        presenter.login();
    }

    @OnClick(R.id.register_button)
    public void onRegisterButtonClicked() {
        eventTracker.track("Register button clicked");
        presenter.register();
    }

    @Override
    public SplashPresenter getPresenter() {
        return presenter;
    }
}
