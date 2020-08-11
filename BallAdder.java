import java.awt.Color;
import java.util.Random;

/**
 * ball adder class.
 */
public class BallAdder implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;
    private Point ballCenter;

    /**
     * Constructor.
     *
     * @param gameLevel      : game.
     * @param remainingBalls : remaining balls in the game.
     * @param ballCenter : ballscenter.
     */
    public BallAdder(GameLevel gameLevel, Counter remainingBalls, Point ballCenter) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
        this.ballCenter = ballCenter;
    }

    /**
     * add ball to game if "ball" block being hit.
     *
     * @param beingHit : the object being hit.
     * @param hitter   : the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() != -3) { //not "ball" block
            return;
        }

        Random rand = new Random(); // random dx and dy.
        double dx = (rand.nextInt(120) + 190);
        double dy = -(rand.nextInt(100) + 370);
        Velocity velocity = new Velocity(dx, dy);
        Ball newBall = new Ball(this.ballCenter, 4, Color.yellow);
        newBall.setBoundaries(0, 600, 800, 0);
        newBall.setVelocity(velocity);
        newBall.addToGame(this.gameLevel); //add to game
        newBall.setEnvironment(this.gameLevel.getEnvironment());

        this.remainingBalls.increase(1); //increase the remaining balls.
        this.gameLevel.setRemainedBlocks(this.remainingBalls); //update in the game.

    }

}
