package <%= appPackage %>.actionbar;

import javax.inject.Inject;

import android.content.Context;
import android.os.Bundle;
import mortar.Mortar;
import mortar.MortarScope;
import mortar.Presenter;

/**
 * Allows shared manipulation of the action bar.
 */
public class ActionBarOwner extends Presenter<ActionBarOwner.View> {

	private ActionBarConfig config;

	@Inject
	public ActionBarOwner() {
		this.config = new ActionBarConfig.Builder().build();
	}

	@Override
	protected void onLoad(Bundle savedInstanceState) {
		super.onLoad(savedInstanceState);

		View view = getView();
		if (view == null) {
			return;
		}

		updateView();
	}

	public void setConfig(ActionBarConfig config) {
		if (config == null) {
			this.config = new ActionBarConfig.Builder().build();
		} else {
			this.config = config;
		}

		updateView();
	}

	@Override
	protected MortarScope extractScope(View view) {
		return Mortar.getScope(view.getMortarContext());
	}

	private void updateView() {
		View view = getView();
		if (view != null) {
			view.setActionBarConfig(config);
		}
	}

	public static interface View {
		void setActionBarConfig(ActionBarConfig config);

		Context getMortarContext();
	}
}
