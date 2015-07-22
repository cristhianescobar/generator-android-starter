package <%= appPackage %>.screen.login;

import javax.inject.Inject;

import android.os.Bundle;
import <%= appPackage %>.actionbar.ActionBarConfig;
import <%= appPackage %>.actionbar.ActionBarOwner;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

class LoginPresenter extends BaseViewPresenter<LoginView> {

	private ActionBarOwner actionBarOwner;

	@Inject
	LoginPresenter(ActionBarOwner actionBarOwner) {
		this.actionBarOwner = actionBarOwner;
	}

	@Override
	protected void onLoad(Bundle savedInstanceState) {
		super.onLoad(savedInstanceState);

		LoginView view = getView();
		if (view != null) {
			configureActionBar();
		}
	}

	private void configureActionBar() {
		ActionBarConfig config = new ActionBarConfig.Builder()
				.title("Login")
				.build();
		actionBarOwner.setConfig(config);
	}
}
