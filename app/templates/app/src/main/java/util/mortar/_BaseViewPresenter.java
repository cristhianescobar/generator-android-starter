package <%= appPackage %>.util.mortar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.SpannedString;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import javax.inject.Inject;

import mortar.ViewPresenter;

/**
 * A view presenter that's friendly to our *WithViewCallback classes.
 *
 * @param <V> the {@link BaseView} that this presenter is for
 */
public abstract class BaseViewPresenter<V extends View> extends ViewPresenter<V> {

    @Inject
    InputMethodManager inputMethodManager;

    /**
     * Hides the soft keyboard from the screen if visible.
     */
    public void hideKeyboard() {
        if (!hasView()) return;
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    protected Context getContext() {
        if (hasView()) return getView().getContext();
        return null;
    }

    /**
     * Show a basic alert dialog with the given title and message.
     *
     * @param title   the title
     * @param message the message
     */
    public AlertDialog showBasicAlertDialog(String title, String message) {
        if (!hasView()) return null;
        return new AlertDialog.Builder(getView().getContext())
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
     * @param resId    a string resource ID to set as the message
     * @param duration the message duration
     */
    public void showToast(int resId, int duration) {
        showToast(getString(resId), duration);
    }

    /**
     * Show a {@link android.widget.Toast} notification with the given message with a
     * set duration.
     *
     * @param message  the message to display
     * @param duration the message duration
     */
    public void showToast(String message, int duration) {
        if (getContext() == null) return;
        Toast.makeText(getContext(), message, duration).show();
    }

    /**
     * Convenience wrapper for {@link android.content.Context#getText}.
     */
    public CharSequence getText(int resId) {
        if (getContext() == null) return null;
        return getContext().getText(resId);
    }

    /**
     * Convenience wrapper for {@link android.content.Context#getString}.
     */
    public String getString(int resId) {
        if (getContext() == null) return null;
        return getContext().getString(resId);
    }

    /**
     * Convenience wrapper for {@link android.content.Context#getString(int, Object...)}.
     */
    public String getString(int resId, Object... formatArgs) {
        if (getContext() == null) return null;
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
}
