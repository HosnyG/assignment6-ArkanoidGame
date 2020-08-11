/**
 * Score info class.
 * player name and his score in the game.
 */
public class ScoreInfo {
    private String playerName;
    private int score;

    /**
     * Constructor.
     *
     * @param name  : player name.
     * @param score : player score.
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.score = score;
    }

    /**
     * @return player name.
     */
    public String getName() {
        return playerName;
    }

    /**
     * @return player score.
     */
    public int getScore() {
        return score;
    }
}