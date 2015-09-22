package <%= appPackage %>.toolbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import mortar.Presenter;
import mortar.bundler.BundleService;

import static mortar.bundler.BundleService.getBundleService;

/**
 * Allows shared manipulation of the action bar.
 */
public class ToolbarOwner extends Presenter<ToolbarOwner.Activity> {

    public static interface Activity {
        void setMenu(ToolbarMenuItem menu);

        void setToolbarConfig(ToolbarConfig config);

        Toolbar getToolbar();

        Context getContext();
    }

    private ToolbarConfig config;

    @Inject
    public ToolbarOwner() {
        this.config = new ToolbarConfig.Builder().build();
    }

    @Override
    protected BundleService extractBundleService(Activity activity) {
        return getBundleService(activity.getContext());
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);

        Activity view = getView();
        if (view == null) {
            return;
        }

        updateView();
    }

    public void setConfig(ToolbarConfig config) {
        if (config == null) {
            this.config = new ToolbarConfig.Builder().build();
        } else {
            this.config = config;
        }

        updateView();
    }

    private void updateView() {
        if (!hasView()) return;
        Activity activity = getView();
        if (activity != null && config != null) {
            activity.setToolbarConfig(config);
            activity.setMenu(config.menu);
        }
    }
}
