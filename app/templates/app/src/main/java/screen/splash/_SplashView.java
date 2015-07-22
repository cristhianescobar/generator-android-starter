package <%= appPackage %>.screen.splash;

import javax.inject.Inject;

import <%= appPackage %>.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import butterknife.InjectView;
import butterknife.OnClick;
import mortar.Presenter;
import <%= appPackage %>.analytics.EventTracker;
import <%= appPackage %>.util.mortar.BaseView;

public class SplashView extends BaseView {

	@Inject SplashPresenter presenter;
	@Inject EventTracker eventTracker;

	@InjectView(R.id.login_button) Button loginButton;
	@InjectView(R.id.register_button) Button registerButton;

	public SplashView(Context context, AttributeSet attrs) {
		super(context, attrs);
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
	protected Presenter getPresenter() {
		return presenter;
	}
}
