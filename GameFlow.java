import biuoop.DialogManager;
import biuoop.GUI;

import java.io.File;
import java.util.List;

import biuoop.KeyboardSensor;

/**
 * moving from one level to the next in the game.
 */
public class GameFlow {
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private GUI gui;
    private Counter score;
    private Counter lives;
    private HighScoresTable highScores;
    private int initLivesNum = 7;


    /**
     * Constructor.
     *
     * @param ar         : animation runner.
     * @param ks         : keyboard sensor.s
     * @param gui        : screen.
     * @param highScores : high scores table.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable highScores) {
        this.ar = ar;
        this.ks = ks;
        this.gui = gui;
        this.score = new Counter();
        this.score.increase(0); //initial score.
        this.lives = new Counter();
        this.lives.increase(this.initLivesNum);
        this.highScores = highScores;


    }

    /**
     * runs the levels.
     *
     * @param levels : list of levels.
     * @throws Exception problem in saving the player's score in the table.
     */
    public void runLevels(List<LevelInformation> levels) throws Exception {
        int counter = 0;

        for (LevelInformation levelInfo : levels) { //runs the levels.
            GameLevel level = new GameLevel(levelInfo, this.ar, this.ks, this.score, this.lives);
            level.initialize();


            //there are blocks and lives
            while (level.getRemainedBlocks().getValue() > 0 && level.getNumOfLives().getValue() >= 1) {
                level.playOneTurn();
            }
            counter++;
            //game over message.
            if (level.getNumOfLives().getValue() == 0) { //the lives ends.
                GameOver g = new GameOver(level.getScore().getValue());
                this.ar.run(new KeyPressStoppableAnimation(ks, "space", g));
                //save player score if it should be in the table.
                if (this.highScores.getRank(level.getScore().getValue()) <= this.highScores.size()) {
                    //ask for player name
                    DialogManager dialog = this.gui.getDialogManager();
                    String name = dialog.showQuestionDialog("Name", "What is your name?", "");
                    ScoreInfo s = new ScoreInfo(name, score.getValue());
                    this.highScores.add(s);
                    File highScoresTableFile = new File("highscores.txt");
                    try { //saving the score in the table.
                        this.highScores.save(highScoresTableFile);
                    } catch (Exception e) { //problem in saving the score in the table.
                        throw new Exception("Problem saving to high scores file!");
                    }
                }
                this.score = new Counter();
                this.score.increase(0);
                this.lives = new Counter();
                this.lives.increase(initLivesNum);
                break;

            } else if (counter == levels.size()) { //win message , finish the levels.

                WinMessage w = new WinMessage(level.getScore().getValue());
                this.ar.run(new KeyPressStoppableAnimation(this.ks, "space", w)); //run win screen.
                //save player score if it should be in the table.
                if (this.highScores.getRank(level.getScore().getValue()) <= this.highScores.size()) {
                    //ask for name
                    DialogManager dialog = this.gui.getDialogManager();
                    String name = dialog.showQuestionDialog("Name", "What is your name?", "");
                    ScoreInfo s = new ScoreInfo(name, score.getValue());
                    this.highScores.add(s);
                    File highScoresTableFile = new File("highscores.txt");
                    try { //saving the score in the table.
                        this.highScores.save(highScoresTableFile);
                    } catch (Exception e) { //problem in saving.
                        throw new Exception("Problem saving to high scores file!");
                    }
                }

                this.score = new Counter();
                this.score.increase(0);
                this.lives = new Counter();
                this.lives.increase(initLivesNum);
                break;

            }

        }
        //close the screen if pressed space key after win or lost message.
        HighScoresAnimation hs = new HighScoresAnimation(this.highScores);
        this.ar.run(new KeyPressStoppableAnimation(this.ks, "space", hs));

    }

}