package <%= appPackage %>.test.util;

import android.app.Activity;
import android.os.Bundle;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;

public class MortarTestActivity extends Activity {
	MortarActivityScope activityScope;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MortarScope parentScope = Mortar.getScope(getApplication());
		activityScope = Mortar.requireActivityScope(parentScope, new Blueprint() {
			@Override
			public String getMortarScopeName() {
				return getClass().getName();
			}

			@Override
			public Object getDaggerModule() {
				return null;
			}
		});
		activityScope.onCreate(savedInstanceState);
	}

	@Override
	public Object getSystemService(String name) {
		if (Mortar.isScopeSystemService(name)) {
			return activityScope;
		}
		return super.getSystemService(name);
	}
}

