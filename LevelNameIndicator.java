import biuoop.DrawSurface;

/**
 * level name indicator.
 * in charge of display the level name.
 */
public class LevelNameIndicator implements Sprite {

    private String levelName;
    private int blockWidth;
    private int blockHeight;

    /**
     * Constructor.
     *
     * @param levelName   : level name.
     * @param blockWidth  : the width of the block of where the level name will me position.
     * @param blockHeight : the height of the block of where the level name will me position.
     */
    public LevelNameIndicator(String levelName, double blockWidth, double blockHeight) {
        this.blockWidth = (int) blockWidth;
        this.blockHeight = (int) blockHeight;
        this.levelName = levelName;

    }

    /**
     * draw level name in the screen.
     *
     * @param d : given surface
     */
    public void drawOn(DrawSurface d) {
        d.drawText((int) (this.blockWidth * (0.65)), (int) (this.blockHeight * (0.78)),
                "Level Name: " + this.levelName, 17);
    }

    /**
     * ignored.
     * @param dt : seconds passed since the last call. ignored here.
     */
    public void timePassed(double dt) {
    }

    /**
     * add level name as sprite in the game.
     *
     * @param g : game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
