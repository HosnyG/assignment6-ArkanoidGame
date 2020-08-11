/**
 * a BlockRemover Class.
 * in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Constructor.
     *
     * @param gameLevel       : game.
     * @param remainingBlocks : remaining blocks counter.
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed from the gameLevel.
     * Remember to remove this listener from the block
     * that is being removed from the gameLevel.
     *
     * @param beingHit : the object being hit.
     * @param hitter   : the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() != 0) {
            return;
        }
        beingHit.removeFromGame(this.gameLevel);
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
        this.gameLevel.setRemainedBlocks(this.remainingBlocks);
    }

}