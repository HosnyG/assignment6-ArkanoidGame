
import java.util.List;

/**
 * in charge of run the game.
 */
public class StartGameTask implements Task<Void> {
    private GameFlow gameFlow;
    private List<LevelInformation> levels;

    /**
     * Constructor.
     * @param levels : levels informations.
     * @param gameFlow : game flow.
     */
    public StartGameTask(List<LevelInformation> levels, GameFlow gameFlow) {
        this.gameFlow = gameFlow;
        this.levels = levels;
    }


    /**
     *
     * @return null;
     * @throws Exception problem in running the game.
     */
    public Void run()  throws Exception {
        gameFlow.runLevels(this.levels);
        return null;
    }

    /**
     * set new levels.
     * @param levelsList : new levels list.
     */
    public void setLevels(List<LevelInformation> levelsList) {
        this.levels = levelsList;
    }


}