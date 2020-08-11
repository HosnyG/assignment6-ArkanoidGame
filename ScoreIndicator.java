import biuoop.DrawSurface;

/**
 * A ScoreIndicator class.
 * in charge of Displaying a score is achieved.
 */
public class ScoreIndicator implements Sprite {

    private Counter scoresCounter;
    private int scoreBlockWidth;
    private int scoreBlockHeight;

    /**
     * Constructor .
     *
     * @param scoresCounter    : the counter in charge of updating the score.
     * @param scoreBlockWidth  : score block width.
     * @param scoreBlockHeight : score block height
     */
    public ScoreIndicator(Counter scoresCounter, double scoreBlockWidth, double scoreBlockHeight) {
        this.scoresCounter = scoresCounter;
        this.scoreBlockWidth = (int) scoreBlockWidth;
        this.scoreBlockHeight = (int) scoreBlockHeight;

    }

    /**
     * displaying the score on given surface.
     *
     * @param d : given surface
     */
    public void drawOn(DrawSurface d) {
        d.drawText((int) (this.scoreBlockWidth * (0.4)), (int) (this.scoreBlockHeight * (0.78)),
                "Score: " + Integer.toString(this.scoresCounter.getValue()), 17);
    }

    /**
     * no thing to do.
     * @param dt : seconds passed since the last call,ignored here.
     */
    public void timePassed(double dt) {
    }

    /**
     * add this sprite to the game.
     *
     * @param g : game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
