package <%= appPackage %>.test.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import <%= appPackage %>.ActivityModule;
import <%= appPackage %>.util.dagger.ObjectGraphService;

import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class MortarTestActivity extends AppCompatActivity {
    MortarScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MortarScope parentScope = MortarScope.getScope(getApplication());
        String scopeName = getLocalClassName() + "-task-" + getTaskId();
        activityScope = parentScope.findChild(scopeName);
        if (activityScope == null) {
            activityScope = parentScope.buildChild()
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(
                            ObjectGraphService.SERVICE_NAME,
                            ObjectGraphService.create(parentScope, new ActivityModule(MortarTestActivity.this)))
                    .build(scopeName);
        }
    }

    @Override
    public Object getSystemService(String name) {
        return activityScope != null && activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }
}

