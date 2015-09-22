package <%= appPackage %>.screen.home;

import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

import javax.inject.Inject;

import flow.Flow;

class HomePresenter extends BaseViewPresenter<HomeView> {

    private final Flow flow;
    private final ToolbarOwner toolbarOwner;

    @Inject
    HomePresenter(Flow flow, ToolbarOwner toolbarOwner) {
        this.flow = flow;
        this.toolbarOwner = toolbarOwner;
    }
}
