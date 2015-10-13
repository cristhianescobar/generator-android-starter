package <%= appPackage %>.screen.home;

import android.content.Context;
import android.util.AttributeSet;

import <%= appPackage %>.analytics.EventTracker;
import <%= appPackage %>.util.dagger.DaggerService;
import <%= appPackage %>.util.widget.BaseRelativeLayout;

import javax.inject.Inject;

public class HomeView extends BaseRelativeLayout<HomePresenter> {

    @Inject
    HomePresenter presenter;

    @Inject
    EventTracker eventTracker;

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        DaggerService.<HomeScreen.HomeComponent>getDaggerComponent(context).inject(this);
    }


    @Override
    public HomePresenter getPresenter() {
        return presenter;
    }
}
