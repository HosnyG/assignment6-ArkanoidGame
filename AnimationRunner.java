import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * loop that run animation class.
 */
public class AnimationRunner {
    private GUI gui;
    private biuoop.Sleeper sleeper;

    /**
     * Constructor.
     *
     * @param gui : gui.
     */
    public AnimationRunner(GUI gui) {
        this.sleeper = new biuoop.Sleeper();
        this.gui = gui;
    }

    /**
     * loop that runs the animation .
     *
     * @param animation : animation will be run.
     */
    public void run(Animation animation) {
        int framesPerSecond = 60;
        do { // run the animation as long as it should not stop.
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            animation.doOneFrame(d, 1.0 / 60);
            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = (1000 / framesPerSecond) - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        } while (!animation.shouldStop());
    }

}