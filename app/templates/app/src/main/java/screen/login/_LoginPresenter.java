package <%= appPackage %>.screen.login;

import android.os.Bundle;

import <%= appPackage %>.toolbar.ToolbarConfig;
import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

import javax.inject.Inject;

class LoginPresenter extends BaseViewPresenter<LoginView> {

	private ToolbarOwner toolbarOwner;

	@Inject
	LoginPresenter(ToolbarOwner toolbarOwner) {
		this.toolbarOwner = toolbarOwner;
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
		ToolbarConfig config = new ToolbarConfig.Builder()
				.title("Login")
				.build();
		toolbarOwner.setConfig(config);
	}
}
