package <%= appPackage %>.screen.login;

import <%= appPackage %>.R;

import flow.HasParent;
import flow.Layout;
import mortar.Blueprint;
import <%= appPackage %>.screen.main.MainScreen;
import <%= appPackage %>.screen.splash.SplashScreen;

@Layout(R.layout.view_login)
public class LoginScreen implements Blueprint, HasParent<SplashScreen> {
	@Override
	public String getMortarScopeName() {
		return getClass().getName();
	}

	@Override
	public Object getDaggerModule() {
		return new Module();
	}

	@Override
	public SplashScreen getParent() {
		return new SplashScreen();
	}

	@dagger.Module(
			injects = LoginView.class,
			addsTo = MainScreen.Module.class
	)
	class Module {}
}
