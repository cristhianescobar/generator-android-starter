package <%= appPackage %>.screen.login;

import android.content.Context;
import android.util.AttributeSet;

import <%= appPackage %>.util.dagger.DaggerService;
import <%= appPackage %>.util.widget.BaseRelativeLayout;

import javax.inject.Inject;

public class LoginView extends BaseRelativeLayout<LoginPresenter> {

    @Inject
    LoginPresenter presenter;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        DaggerService.<LoginScreen.LoginComponent>getDaggerComponent(context).inject(this);
    }

    @Override
    public LoginPresenter getPresenter() {
        return presenter;
    }
}
