package <%= appPackage %>.app;

import <%= appPackage %>.ApplicationComponent;
import <%= appPackage %>.screen.main.MainActivity;
import <%= appPackage %>.toolbar.ToolbarOwner;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent extends ApplicationComponent {
    void inject(BaseActivity activity);
    void inject(MainActivity activity);

    ToolbarOwner toolbarOwner();
}
