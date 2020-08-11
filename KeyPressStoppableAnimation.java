import biuoop.KeyboardSensor;
import biuoop.DrawSurface;

/**
 * wrap an existing animation and add a "waiting-for-key" behavior to it.
 */
public class KeyPressStoppableAnimation implements Animation {


    private KeyboardSensor ks;
    private String key;
    private Animation animation;
    private boolean done;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     *
     * @param ks        : key board sensor.
     * @param key       : key to stop the animation.
     * @param animation : animation.
     */
    public KeyPressStoppableAnimation(KeyboardSensor ks, String key, Animation animation) {
        this.ks = ks;
        this.key = key;
        this.isAlreadyPressed = true;
        this.animation = animation;
    }

    /**
     * draw the animation.
     *
     * @param d  : given surface.
     * @param dt : seconds passed since the last call;
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt); //draw the animation.
        if (this.ks.isPressed(this.key) && !this.isAlreadyPressed) { //check if the specific key is pressed.
            this.done = true;
        }

        if (!this.ks.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * @return true if the key is pressed false other wise.
     */
    public boolean shouldStop() {
        if (this.done) {
            this.done = false;
            return true;
        }
        return false;
    }

}