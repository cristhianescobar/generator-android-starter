package <%= appPackage %>.screen.home;

import android.content.Context;
import android.util.AttributeSet;

import <%= appPackage %>.analytics.EventTracker;
import <%= appPackage %>.util.widget.BaseRelativeLayout;

import javax.inject.Inject;

public class HomeView extends BaseRelativeLayout<HomePresenter> {

    @Inject
    HomePresenter presenter;

    @Inject
    EventTracker eventTracker;

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public HomePresenter getPresenter() {
        return presenter;
    }
}
