package <%= appPackage %>.screen.register;

import android.os.Bundle;

import <%= appPackage %>.SharedPreferencesKeys;
import <%= appPackage %>.model.User;
import <%= appPackage %>.model.UserWithPassword;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.screen.home.HomeScreen;
import <%= appPackage %>.service.ApiService;
import <%= appPackage %>.toolbar.ToolbarConfig;
import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.flow.Utils;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import rx.Subscription;
import rx.functions.Action1;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

class RegisterPresenter extends BaseViewPresenter<RegisterView> {
    private final ToolbarOwner toolbarOwner;
    private final Validator validator;
    private final ApiService apiService;
    private final JsonSharedPreferencesRepository prefsRepository;

    private Subscription registerSubscription;

    @Inject
    RegisterPresenter(ToolbarOwner toolbarOwner, Validator validator, ApiService apiService, JsonSharedPreferencesRepository prefsRepository) {
        this.toolbarOwner = toolbarOwner;
        this.validator = validator;
        this.apiService = apiService;
        this.prefsRepository = prefsRepository;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);

        RegisterView view = getView();
        if (view != null) {
            configureActionBar();
        }
    }

    @Override
    public void dropView(RegisterView view) {
        if (registerSubscription != null && !registerSubscription.isUnsubscribed()) {
            registerSubscription.unsubscribe();
        }

        super.dropView(view);
    }

    private void configureActionBar() {
        ToolbarConfig config = new ToolbarConfig.Builder()
                .title("Register")
                .build();
        toolbarOwner.setConfig(config);
    }

    public void register(String name, String email, String password) {
        RegisterView view = getView();

        UserWithPassword user = new UserWithPassword(trimToEmpty(name), trimToEmpty(email), trimToEmpty(password));
        Set<ConstraintViolation<UserWithPassword>> errors = validator.validate(user);

        if (errors.isEmpty()) {
            registerWithApi(user);
        } else {
            view.onValidationError(errors);
        }
    }

    private void registerWithApi(UserWithPassword user) {
        registerSubscription = apiService.register(user)
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        storeUser(user);
                        Utils.replaceTop(getContext(), new HomeScreen());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable thrown) {
                        getView().onRegistrationError(thrown);
                    }
                });
    }

    private void storeUser(User user) {
        prefsRepository.putObject(SharedPreferencesKeys.USER_ACCOUNT, user);
    }
}
