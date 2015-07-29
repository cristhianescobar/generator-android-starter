package <%= appPackage %>.screen.splash;

import javax.inject.Inject;

import android.os.Bundle;
import flow.Flow;
import timber.log.Timber;

import <%= appPackage %>.actionbar.ActionBarConfig;
import <%= appPackage %>.actionbar.ActionBarOwner;
import <%= appPackage %>.screen.login.LoginScreen;
import <%= appPackage %>.screen.register.RegisterScreen;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

class SplashPresenter extends BaseViewPresenter<SplashView> {

	private final Flow flow;
	private final ActionBarOwner actionBarOwner;

	@Inject
	SplashPresenter(Flow flow, ActionBarOwner actionBarOwner) {
		this.flow = flow;
		this.actionBarOwner = actionBarOwner;
	}

	public void login() {
		Timber.i("Navigating to login screen");
		flow.goTo(new LoginScreen());
	}

	public void register() {
		Timber.i("Navigating to register screen");
		flow.goTo(new RegisterScreen());
	}

	@Override
	protected void onLoad(Bundle savedInstanceState) {
		super.onLoad(savedInstanceState);

		SplashView view = getView();
		if (view != null) {
			hideActionBar();
		}
	}

	private void hideActionBar() {
		ActionBarConfig config = new ActionBarConfig.Builder()
				.visible(false)
				.build();
		actionBarOwner.setConfig(config);
	}
}
