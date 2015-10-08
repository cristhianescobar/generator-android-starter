package <%= appPackage %>.screen.<%= screenPackage %>;

import android.os.Bundle;

import <%= appPackage %>.toolbar.ToolbarConfig;
import <%= appPackage %>.toolbar.ToolbarOwner;
import <%= appPackage %>.util.mortar.BaseViewPresenter;

import javax.inject.Inject;

import flow.Flow;
import timber.log.Timber;

class <%= screenName %>Presenter extends BaseViewPresenter<<%= screenName %>View> {
    private final ToolbarOwner toolbarOwner;

    @Inject
    <%= screenName %>Presenter(ToolbarOwner toolbarOwner) {
        this.toolbarOwner = toolbarOwner;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);

        <%= screenName %>View view = getView();
        if (view != null) {
            hideActionBar();
        }
    }

    private void hideActionBar() {
        ToolbarConfig config = new ToolbarConfig.Builder()
                .visible(false)
                .build();
        toolbarOwner.setConfig(config);
    }
}
