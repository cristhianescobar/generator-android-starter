package <%= appPackage %>.screen.register;

import <%= appPackage %>.R;

import flow.HasParent;
import flow.Layout;
import mortar.Blueprint;
import <%= appPackage %>.screen.main.MainScreen;
import <%= appPackage %>.screen.splash.SplashScreen;

@Layout(R.layout.view_register)
public class RegisterScreen implements Blueprint, HasParent<SplashScreen> {
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
			injects = RegisterView.class,
			addsTo = MainScreen.Module.class
	)
	class Module {}
}
