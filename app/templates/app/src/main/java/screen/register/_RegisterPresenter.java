package <%= appPackage %>.screen.register;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import <%= appPackage %>.SharedPreferencesKeys;
import <%= appPackage %>.actionbar.ActionBarConfig;
import <%= appPackage %>.actionbar.ActionBarOwner;
import <%= appPackage %>.model.User;
import <%= appPackage %>.model.UserWithPassword;
import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import <%= appPackage %>.screen.home.HomeScreen;
import <%= appPackage %>.service.ApiService;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

import android.os.Bundle;
import flow.Flow;
import rx.Subscription;
import rx.functions.Action1;

class RegisterPresenter extends BaseViewPresenter<RegisterView> {

  private final Flow flow;
  private final ActionBarOwner actionBarOwner;
  private final Validator validator;
  private final ApiService apiService;
  private final JsonSharedPreferencesRepository prefsRepository;

  private Subscription registerSubscription;

  @Inject
  RegisterPresenter(Flow flow, ActionBarOwner actionBarOwner, Validator validator, ApiService apiService, JsonSharedPreferencesRepository prefsRepository) {
    this.flow = flow;
    this.actionBarOwner = actionBarOwner;
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
    ActionBarConfig config = new ActionBarConfig.Builder()
      .title("Register")
      .build();
    actionBarOwner.setConfig(config);
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
          flow.resetTo(new HomeScreen());
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
