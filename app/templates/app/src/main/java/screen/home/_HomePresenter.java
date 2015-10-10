package <%= appPackage %>.screen.home;

import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.dagger.DaggerScope;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

import javax.inject.Inject;

@DaggerScope(HomeScreen.HomeComponent.class)
class HomePresenter extends BaseViewPresenter<HomeView> {
    private final ToolbarOwner toolbarOwner;

    @Inject
    HomePresenter(ToolbarOwner toolbarOwner) {
        this.toolbarOwner = toolbarOwner;
    }
}
