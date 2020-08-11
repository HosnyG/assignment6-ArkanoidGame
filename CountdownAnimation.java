import biuoop.DrawSurface;

import java.awt.Color;


/**
 * counting down from given number for specific seconds.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private int screenWidth;
    private int screenHeight;
    private long time;
    private long timeForEachNum;
    private long end;
    private int currNum;
    private int flag = 0;


    /**
     * Constructor.
     *
     * @param numOfSeconds : count time.
     * @param countFrom    : count down from this num to 0;
     * @param gameScreen   : sprites in the screen.
     * @param screenWidth  : screen width.
     * @param screenHeight : screen height.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen
            , int screenWidth, int screenHeight) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.currNum = this.countFrom; //initialize.
    }

    /**
     * do one frame function.
     * this will draw the sprites for the game and the counting.
     *
     * @param d : given surface.
     * @param dt : seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (flag == 0) { //first call
            this.time = System.currentTimeMillis();
            this.timeForEachNum = (long) (this.numOfSeconds * 1000) / (this.countFrom + 1);
            this.end = time + (long) (this.numOfSeconds * 1000);
            this.flag++;
        }
        // decrease the current number.
        if (System.currentTimeMillis() >= time + (timeForEachNum * (countFrom - currNum + 1))) {
            if (this.currNum != 0) {
                this.currNum--;
            }
        }
        this.gameScreen.drawAllOn(d);
        d.setColor(new Color(1.000f, 0.271f, 0.000f));
        d.drawText((int) (screenWidth * 0.47), (int) (screenHeight * 0.7), Integer.toString(currNum), 50);

    }

    /**
     * @return true must stop i.e if the counting last the given seconds,false continue.
     */
    public boolean shouldStop() {
        if (System.currentTimeMillis() >= end) {
            return true;
        }
        return false;
    }
}