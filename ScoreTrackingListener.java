/**
 * A ScoreTrackingListener class.
 * in charge of update a given score according to blocks hit.
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;

    /**
     * Constructor.
     *
     * @param scoreCounter : initial score counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * this method is called whenever the being hit object is hit.
     * hitting a block is worth 5 points.
     * destroying a block is worth and additional 10 points
     *
     * @param beingHit : the object being hit.
     * @param hitter   : the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == -2) { // not death block.
            return;
        }
        currentScore.increase(5); // hitting a block +5 points.
        if (beingHit.getHitPoints() == 0) { //destroying a block +10 points.
            currentScore.increase(10);
        }
    }
}