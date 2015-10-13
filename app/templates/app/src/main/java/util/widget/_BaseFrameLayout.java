package <%= appPackage %>.util.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


import butterknife.ButterKnife;
import mortar.Presenter;

/**
 * Custom {@link android.widget.FrameLayout} that has support for {@link mortar.Mortar} and
 * {@link butterknife.ButterKnife}.
 */
public abstract class BaseFrameLayout<T extends Presenter> extends FrameLayout {

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) return;
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) return;
        getPresenter().takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isInEditMode()) return;
        getPresenter().dropView(this);
    }

    public abstract T getPresenter();
}
