package <%= appPackage %>.toolbar;

import rx.functions.Action0;

/**
 * Created by pwner on 9/20/15.
 */
public class ToolbarMenuItem {
    public final CharSequence title;
    public final Action0 action;

    public ToolbarMenuItem(CharSequence title, Action0 action) {
        this.title = title;
        this.action = action;
    }
}
