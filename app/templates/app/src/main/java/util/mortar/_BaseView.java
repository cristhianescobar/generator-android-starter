package <%= appPackage %>.util.mortar;

import javax.inject.Inject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.SpannedString;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.ButterKnife;
import mortar.Mortar;
import mortar.Presenter;

/**
 * Convenience base class which sets up Mortar and ButterKnife injection and handles presenter view
 * accounting. Also plays nice with the UI designer.
 */
public abstract class BaseView extends RelativeLayout {

	public static final boolean CANCELLABLE = true;
	public static final boolean NOT_CANCELLABLE = false;
	public static final boolean INDETERMINATE = true;
	public static final boolean DETERMINATE = false;

	@Inject InputMethodManager inputMethodManager;

	/**
	 * Constructor
	 */
	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (isInEditMode()) {
			return;
		}  // Skip further initialisation so we can render in UI designer

		Mortar.inject(context, this);
	}

	/**
	 * Convenience wrapper for {@link android.content.Context#getText}.
	 */
	public CharSequence getText(int resId) {
		return getContext().getText(resId);
	}

	/**
	 * Convenience wrapper for {@link android.content.Context#getString}.
	 */
	public String getString(int resId) {
		return getContext().getString(resId);
	}

	/**
	 * Convenience wrapper for {@link android.content.Context#getString(int, Object...)}.
	 */
	public String getString(int resId, Object... formatArgs) {
		return getContext().getString(resId, formatArgs);
	}

	/**
	 * Return a localized, HTML styled CharSequence from the application's package's
	 * default string table.
	 *
	 * @param resId Resource id for the CharSequence text
	 */
	public CharSequence getHtml(int resId) {
		return getText(resId);
	}

	/**
	 * Return a localized, HTML styled CharSequence from the application's package's
	 * default string table. Supports {@link String#format} style substitution.
	 *
	 * @param resId Resource id for the format string
	 * @param formatArgs The format arguments that will be used for substitution.
	 */
	public CharSequence getHtml(int resId, Object... formatArgs) {
		SpannedString spanned = SpannedString.valueOf(getText(resId));
		String html = Html.toHtml(spanned);
		String formattedHtml = String.format(html, formatArgs);
		return Html.fromHtml(formattedHtml);
	}

	public ProgressDialog showProgressDialog(boolean indeterminate, boolean cancellable, int resId, Object... formatArgs) {
		return showProgressDialog(indeterminate, cancellable, getString(resId), formatArgs);
	}

	public ProgressDialog showProgressDialog(boolean indeterminate, boolean cancellable, String message, Object... formatArgs) {
		return ProgressDialog.show(getContext(), null, String.format(message, formatArgs), indeterminate, cancellable);
	}

	/**
	 * Show a basic alert dialog with the given title and message.
	 *
	 * @param title the title
	 * @param message the message
	 */
	public AlertDialog showBasicAlertDialog(String title, String message) {
		return new AlertDialog.Builder(getContext())
				.setTitle(title)
				.setMessage(message)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.show();
	}

	/**
	 * Show a {@link android.widget.Toast} notification with the given message.
	 *
	 * @param resId a string resource ID to set as the message
	 * @param duration the message duration
	 */
	public void showToast(int resId, int duration) {
		showToast(getString(resId), duration);
	}

	/**
	 * Show a {@link android.widget.Toast} notification with the given message with a
	 * set duration.
	 *
	 * @param message the message to display
	 * @param duration the message duration
	 */
	public void showToast(String message, int duration) {
		Toast.makeText(getContext(), message, duration).show();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (isInEditMode()) {
			return;
		}  // Skip further initialisation so we can render in UI designer

		ButterKnife.inject(this);

		onFinishInjectViews();

		getPresenter().takeView(this);
	}

	/**
	 * Called after all injected views have been injectected but prior to the presenter
	 * taking the view. Override if there's any post view injection initialisation work to do.
	 * <p/>
	 * By default this is a noop.
	 */
	protected void onFinishInjectViews() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		hideKeyboard();

		getPresenter().dropView(this);
	}

	/**
	 * Hides the soft keyboard from the screen if visible.
	 */
	protected void hideKeyboard() {
		inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
	}

	/**
	 * Returns the presenter that is managing this view.
	 *
	 * @return an instance of {@link mortar.Presenter}
	 */
	protected abstract Presenter getPresenter();
}
