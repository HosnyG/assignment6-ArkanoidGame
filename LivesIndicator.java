import biuoop.DrawSurface;

/**
 * ndicate the number of lives.
 */
public class LivesIndicator implements Sprite {

    private Counter livesCounter;
    private int livesBlockWidth;
    private int livesBlockHeight;

    /**
     * Constructor.
     *
     * @param livesCounter     : lives counter.
     * @param livesBlockWidth  : lives block width.
     * @param livesBlockHeight : lives block height.
     */
    public LivesIndicator(Counter livesCounter, double livesBlockWidth, double livesBlockHeight) {
        this.livesCounter = livesCounter;
        this.livesBlockWidth = (int) livesBlockWidth;
        this.livesBlockHeight = (int) livesBlockHeight;

    }

    /**
     * draw the word lives and the remained lives.
     *
     * @param d : given surface
     */
    public void drawOn(DrawSurface d) {
        d.drawText((int) (this.livesBlockWidth * (0.1)), (int) (this.livesBlockHeight * (0.78)),
                "Lives: " + Integer.toString(this.livesCounter.getValue()), 17);
    }

    /**
     * ignored.
     *
     * @param dt : seconds passed since the last call,ignored here.
     */
    public void timePassed(double dt) {
    }

    /**
     * add this indicator to the game as sprite.
     *
     * @param g : game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
