package <%= appPackage %>.screen.login;

import android.content.Context;
import android.util.AttributeSet;

import <%= appPackage %>.util.widget.BaseRelativeLayout;

import javax.inject.Inject;

public class LoginView extends BaseRelativeLayout<LoginPresenter> {

    @Inject
    LoginPresenter presenter;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LoginPresenter getPresenter() {
        return presenter;
    }
}
