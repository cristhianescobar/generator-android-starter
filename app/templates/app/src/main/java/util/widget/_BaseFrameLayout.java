package <%= appPackage %>.util.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import <%= appPackage %>.util.dagger.ObjectGraphService;

import butterknife.ButterKnife;
import mortar.Presenter;

/**
 * Custom {@link android.widget.FrameLayout} that has support for {@link mortar.Mortar} and
 * {@link butterknife.ButterKnife}.
 */
public abstract class BaseFrameLayout<T extends Presenter> extends FrameLayout {

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ObjectGraphService.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getPresenter().takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getPresenter().dropView(this);
    }

    public abstract T getPresenter();
}
