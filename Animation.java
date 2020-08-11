import biuoop.DrawSurface;

/**
 * An animation interface.
 */
public interface Animation {

    /**
     * do one frame.
     *
     * @param d : given surface.
     * @param dt : seconds passed since the last call;
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * if the "loop" should stop.
     *
     * @return true if should stop, false else.
     */
    boolean shouldStop();
}