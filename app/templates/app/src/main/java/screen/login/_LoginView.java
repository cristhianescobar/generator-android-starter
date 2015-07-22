package <%= appPackage %>.screen.login;

import javax.inject.Inject;

import android.content.Context;
import android.util.AttributeSet;
import mortar.Presenter;
import <%= appPackage %>.util.mortar.BaseView;

public class LoginView extends BaseView {

	@Inject LoginPresenter presenter;

	public LoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected Presenter getPresenter() {
		return presenter;
	}
}
