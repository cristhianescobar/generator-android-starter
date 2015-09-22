package <%= appPackage %>.toolbar;

/**
 * An action to execute when a menu item is selected.
 */
public interface MenuItemSelectionHandler {
	/**
	 * Handle this menu item action.
	 *
	 * @return boolean return false to allow normal menu processing to
	 *         proceed, true to consume it here.
	 */
	boolean execute();
}
