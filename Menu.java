/**
 * menu interface.
 *
 * @param <T> task.
 */
public interface Menu<T> extends Animation {
    /**
     * add selection to the menu.
     *
     * @param key       : to be pressed.
     * @param message   : description.
     * @param returnVal : specific task.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return the task according the key thar pressed.
     */
    T getStatus();

    /**
     * add submenu as selection.
     * @param key : to be pressed.
     * @param message : description.
     * @param subMenu : submenu will be added.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}