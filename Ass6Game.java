import biuoop.GUI;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * arkanoid game.
 *
 * @author : Ganaiem Hosny
 */
public class Ass6Game {

    /**
     * main method.
     *
     * @param args : levels number from 1 to 4 ,ignore other wise.
     */
    public static void main(String[] args) {
        if (args.length > 1) {
            return;
        }
        final File highScoresTableFile = new File("highscores.txt");
        final int tableSize = 10;

        final HighScoresTable table;
        try { //creating high scores table if it does not exist.
            if (!highScoresTableFile.exists()) {
                highScoresTableFile.createNewFile();
                table = new HighScoresTable(tableSize);
                table.save(highScoresTableFile);
                table.setSize(tableSize);
            } else { //load it if it exist
                table = HighScoresTable.loadFromFile(highScoresTableFile);
                table.setSize(tableSize);
            }
        } catch (Exception e) {
            System.out.println("problems in load or creating high scores table.");
            return;
        }

        GUI gui = new GUI("Arkanoid Game", 800, 600);
        biuoop.KeyboardSensor ks = gui.getKeyboardSensor();
        AnimationRunner ar = new AnimationRunner(gui);
        GameFlow g = new GameFlow(ar, ks, gui, table);

        //sub menu.
        Menu<Task<Void>> subMenu = new MenuAnimation<>("Level Sets", gui.getKeyboardSensor(), ar);
        //main menu
        Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid Game", gui.getKeyboardSensor(), ar);

        //quit task.
        Task<Void> quit = new Task<Void>() {
            public Void run() {
                System.exit(1);
                return null;
            }
        };

        //if args.length==0 , this is the default path of level sets.
        String defaultLevelSetPath = "level_set.txt";
        List<LevelSet> levelSets = null;
        try {
            levelSets = getLevelSets(args, defaultLevelSetPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            gui.close();
            return;
        }
        List<StartGameTask> jo = new ArrayList<>();
        Map<StartGameTask, String> kol = new HashMap<>();


        for (int i = 0; i < levelSets.size(); i++) { //get levels information
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(ClassLoader.
                    getSystemClassLoader().getResourceAsStream(
                    levelSets.get(i).getPath())));
            LevelSpecificationReader lsr;
            List<LevelInformation> levelInfo;
            try { //reading the levels def and blocks def files.
                lsr = new LevelSpecificationReader();
                levelInfo = lsr.fromReader(reader2);
                StartGameTask startGameTask = new StartGameTask(levelInfo, g);
                subMenu.addSelection(levelSets.get(i).getKey(), levelSets.get(i).getDescription(), startGameTask);
                jo.add(startGameTask);
                kol.put(startGameTask, levelSets.get(i).getPath());

            } catch (Exception e) {
                System.out.println(e.getMessage());
                gui.close();
                return;
            }
        }

        //menu selections
        menu.addSubMenu("s", "Start Game", subMenu);
        menu.addSelection("h", "High Scores"
                , new ShowHighScoresTask(ar, new KeyPressStoppableAnimation(gui.getKeyboardSensor(), "space"
                        , new HighScoresAnimation(table))));
        menu.addSelection("q", "Quit", quit);

        //run the menu until the user pres the key of exit task.
        while (true) {
            ar.run(menu);
            Task<Void> task = menu.getStatus();
            try {
                task.run();
                for (int i = 0; i < jo.size(); i++) { //set new levels(avoid changing in blocks background)
                    jo.get(i).setLevels(getLevels(kol.get(jo.get(i))));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                gui.close();
                return;
            }

        }

    }

    /**
     * get list of level set.
     *
     * @param args                : main args.
     * @param defaultLevelSetPath : default level_set file path.
     * @return list of level sets.
     * @throws Exception problems in format or reading files.
     */
    private static List<LevelSet> getLevelSets(String[] args, String defaultLevelSetPath) throws Exception {
        LevelSetSpecificationReader levelSetsReader;
        List<LevelSet> levelSets;
        if (args.length == 1) { //1 args in command line
            BufferedReader argReader = new
                    BufferedReader(new InputStreamReader(ClassLoader.
                    getSystemClassLoader().getResourceAsStream(args[0])));
            try {
                levelSetsReader = new LevelSetSpecificationReader(argReader);
                levelSets = levelSetsReader.getLevelSets();

            } catch (Exception e) {
                throw e;
            }
        } else { //default level set
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(ClassLoader.getSystemClassLoader().
                    getResourceAsStream(defaultLevelSetPath)));
            try {
                levelSetsReader = new LevelSetSpecificationReader(reader);
                levelSets = levelSetsReader.getLevelSets();
            } catch (Exception e) {
                throw e;
            }

        }
        return levelSets;
    }

    /**
     * given an file path, return levels defined in this file.
     *
     * @param s : file path.
     * @return levels information.
     * @throws Exception problems in reading the file or file's format.
     */
    private static List<LevelInformation> getLevels(String s) throws Exception {
        BufferedReader is;
        LevelSpecificationReader lsr;
        List<LevelInformation> levels;

        try {
            is = new
                    BufferedReader(new InputStreamReader(ClassLoader.
                    getSystemClassLoader().getResourceAsStream(s)));
            lsr = new LevelSpecificationReader();
            try {
                levels = lsr.fromReader(is);
            } catch (Exception e) {
                throw e;

            }
        } catch (Exception e) {

            throw new Exception("file " + s + " mot found!");

        }
        return levels;
    }

}