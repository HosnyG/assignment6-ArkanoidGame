/**
 * in charge of showing the high score table.
 */
public class ShowHighScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;


    /**
     * Constructor.
     * @param runner : animation runner.
     * @param highScoresAnimation : high score table animation.
     */
    public ShowHighScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * run the animation.
     * @return null.
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}