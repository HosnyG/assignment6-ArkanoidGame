import java.util.List;
import java.awt.Image;
import java.awt.Color;

/**
 * specifies the information required to fully describe a level.
 */
public interface LevelInformation {

    /**
     * @return number of balls.
     */
    int numberOfBalls();

    /**
     * @return list of initial balls velocity.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return paddle speed.
     */
    int paddleSpeed();

    /**
     * @return paddle width.
     */
    int paddleWidth();

    /**
     * @return level name.
     */
    String levelName();

    /**
     * @return level's background.
     */
    Color getBackgroundColor();

    /**
     * @return background img.
     */
    Image getBackgroundImg();

    /**
     * @return blocks in the level.
     */
    List<Block> blocks();

    /**
     * @return number of blocks must be destroyed yo pass the level.
     */
    int numberOfBlocksToRemove();


}