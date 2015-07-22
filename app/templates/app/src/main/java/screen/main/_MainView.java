package <%= appPackage %>.screen.main;

import javax.inject.Inject;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;
import <%= appPackage %>.util.flow.CanShowScreen;
import <%= appPackage %>.util.flow.ScreenConductor;

public class MainView extends FrameLayout implements CanShowScreen<Blueprint> {

	@Inject MainPresenter presenter;

	private ScreenConductor<Blueprint> screenConductor;

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (isInEditMode()) { return; }

		Mortar.inject(context, this);

		screenConductor = new ScreenConductor<>(context, this);
	}

	@Override
	public void showScreen(Blueprint screen, Flow.Direction direction) {
		screenConductor.showScreen(screen, direction);
	}

	public Flow getFlow() {
		return presenter.getFlow();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (isInEditMode()) { return; }

		presenter.takeView(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		presenter.dropView(this);
	}
}
