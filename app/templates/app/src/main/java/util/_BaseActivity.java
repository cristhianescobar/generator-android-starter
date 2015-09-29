package <%= appPackage %>.util;

import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.lifecycle.LifecycleActivity;
import <%= appPackage %>.util.lifecycle.LifecycleOwner;

import javax.inject.Inject;

import mortar.MortarScope;


abstract public class BaseActivity extends LifecycleActivity implements ToolbarOwner.Activity {

    @Inject
    LifecycleOwner lifecycleOwner;

    @Inject
    public ToolbarOwner toolbarOwner;

    protected MortarScope activityScope;

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    public MortarScope getMortarScope() {
        return activityScope;
    }
}
