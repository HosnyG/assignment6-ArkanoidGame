import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Menu screen.
 *
 * @param <T> task.
 */
public class MenuAnimation<T> implements Menu<T> {

    private String title;
    private KeyboardSensor ks;
    private List<String> keys;
    private List<String> messages;
    private List<T> returnValList;
    private T status;
    private HashMap<String, Menu<T>> subMenuMap;
    private AnimationRunner ar;


    /**
     * Constructor.
     *
     * @param title : title.
     * @param ks    : keyboard sensor.
     * @param ar    : animation runner.
     */
    public MenuAnimation(String title, KeyboardSensor ks, AnimationRunner ar) {
        this.title = title;
        this.ks = ks;
        this.keys = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.returnValList = new ArrayList<>();
        this.subMenuMap = new HashMap<>();
        this.ar = ar;
    }

    /**
     * draw the menu screen.
     *
     * @param d  : given surface.
     * @param dt : seconds passed since the last call;
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(255, 239, 213));
        d.fillRectangle(0, 0, 800, 600);


        d.setColor(Color.black);
        d.drawText(295, 55, this.title, 30);
        d.setColor(Color.red);
        d.drawText(296, 56, this.title, 30);
        d.setColor(Color.black);
        d.drawText(41, 121, "select one of the following options :", 29);
        d.setColor(new Color(34, 139, 34));
        d.drawText(40, 120, "select one of the following options :", 29);
        d.setColor(Color.black);


        //menu selections
        for (int i = 0; i < this.messages.size(); i++) {
            d.drawLine(40, 160 + (i * 50), 68, 160 + (i * 50));
            d.drawLine(40, 170 + (i * 50), 68, 170 + (i * 50));
            d.drawLine(68, 160 + (i * 50), 68, 152 + (i * 50));
            d.drawLine(68, 170 + (i * 50), 68, 178 + (i * 50));
            d.drawLine(68, 152 + (i * 50), 80, 165 + (i * 50));
            d.drawLine(68, 178 + (i * 50), 80, 165 + (i * 50));
            d.drawText(90, 176 + (i * 50), "(" + this.keys.get(i) + ") " + this.messages.get(i), 25);
        }
    }


    /**
     * @return true if one of the menu selections key is pressed , false other wise.
     */
    public boolean shouldStop() {
        for (int i = 0; i < this.keys.size(); i++) { //check it contains the key that pressed
            if (this.ks.isPressed(this.keys.get(i))) {
                if (this.subMenuMap.containsKey(this.keys.get(i))) { //run the sub menu
                    this.ar.run(this.subMenuMap.get(this.keys.get(i)));
                    T task = this.subMenuMap.get(this.keys.get(i)).getStatus();
                    this.status = task;
                    return true;
                }
                this.status = this.returnValList.get(i);
                return true;
            }
        }

        return false; //does not contain
    }

    /**
     * add selection  to menu (task).
     *
     * @param key       : to be pressed.
     * @param message   : description.
     * @param returnVal : specific task.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnValList.add(returnVal);
    }

    /**
     * @return the task.
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * ass sub menu to the current menu.
     *
     * @param key     : to be pressed.
     * @param message : description.
     * @param subMenu : submenu will be added.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnValList.add(null);
        this.subMenuMap.put(key, subMenu);
    }


}
