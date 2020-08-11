/**
 * Balls remover.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Constructor.
     *
     * @param gameLevel      : game.
     * @param remainingBalls : remaining balls in the game.
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * remove the ball that hit the death block.
     *
     * @param beingHit : the object being hit.
     * @param hitter   : the Ball that's doing the hitting that will be removed.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() != -2) { //not death block,
            return;
        }
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease(1);
        this.gameLevel.setRemainedBalls(this.remainingBalls);

    }

}
