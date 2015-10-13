package <%= appPackage %>.screen.register;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

import <%= appPackage %>.R;
import <%= appPackage %>.model.UserWithPassword;
import <%= appPackage %>.util.dagger.DaggerService;
import <%= appPackage %>.util.widget.BaseRelativeLayout;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisterView extends BaseRelativeLayout<RegisterPresenter> {

    @Inject
    RegisterPresenter presenter;

    @Bind(R.id.name_field)
    EditText nameEditText;
    @Bind(R.id.email_field)
    EditText emailEditText;
    @Bind(R.id.password_field)
    EditText passwordEditText;
    @Bind(R.id.register_button)
    Button registerButton;

    AlertDialog alertDialog;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        DaggerService.<RegisterScreen.RegisterComponent>getDaggerComponent(context).inject(this);
    }

    @OnClick(R.id.register_button)
    public void onRegisterButtonClicked() {
        registerButton.setEnabled(false);

        presenter.hideKeyboard();

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        presenter.register(name, email, password);
    }

    public void onValidationError(Set<ConstraintViolation<UserWithPassword>> errors) {
        registerButton.setEnabled(true);

        for (ConstraintViolation<UserWithPassword> error : errors) {
            String property = error.getPropertyPath().toString();
            if (property.equals("name")) {
                nameEditText.setError(error.getMessage());
            } else if (property.equals("email")) {
                emailEditText.setError(error.getMessage());
            } else if (property.equals("password")) {
                passwordEditText.setError(error.getMessage());
            }
        }
    }

    public void onRegistrationError(Throwable thrown) {
        registerButton.setEnabled(true);

        alertDialog = presenter.showBasicAlertDialog(getContext().getString(R.string.dialog_error_title), thrown.getMessage());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public RegisterPresenter getPresenter() {
        return presenter;
    }
}
