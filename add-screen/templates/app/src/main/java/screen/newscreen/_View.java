package <%= appPackage %>.screen.<%= screenPackage %>;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import <%= appPackage %>.R;
import <%= appPackage %>.analytics.EventTracker;
import <%= appPackage %>.util.widget.BaseRelativeLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class <%= screenName %>View extends BaseRelativeLayout<<%= screenName %>Presenter> {

    @Inject
    <%= screenName %>Presenter presenter;
    @Inject
    EventTracker eventTracker;

    public <%= screenName %>View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public <%= screenName %>Presenter getPresenter() {
        return presenter;
    }
}
