package <%= appPackage %>.util;

import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.lifecycle.LifecycleActivity;
import <%= appPackage %>.util.lifecycle.LifecycleOwner;

import javax.inject.Inject;


abstract public class BaseActivity extends LifecycleActivity implements ToolbarOwner.Activity {

    @Inject
    LifecycleOwner lifecycleOwner;

    @Inject
    public ToolbarOwner toolbarOwner;

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }
}
