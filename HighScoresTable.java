import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * High Scores Table class.
 * saving players scores in table.
 */
public class HighScoresTable {
    private int size;
    private List<ScoreInfo> table;

    /**
     * Constructor.
     *
     * @param size : table size , max scores to display.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.table = new ArrayList<>(); //initialize the list.
    }

    /**
     * add component to the table.
     * order it from high to low scores.
     *
     * @param score : player name and score.
     */
    public void add(ScoreInfo score) {
        int index;
        if (this.table.size() == 0) {
            index = 0;
        } else {
            index = -1;
        }
        for (int i = 0; i < this.size; i++) {
            if (i < this.table.size()) {
                ScoreInfo currentScore = this.table.get(i);
                if (currentScore.getScore() < score.getScore()) { //order
                    index = i;
                    break;
                }
            } else {
                index = i;
                break;
            }
        }
        if (index != -1) {
            this.table.add(index, score);
        }

        while (this.table.size() > this.size) { //remove one component if it exceed table's size.
            this.table.remove(this.table.size() - 1);
        }
    }


    /**
     * @return table size.
     */
    public int size() {
        return this.size;
    }

    /**
     * @return the table.
     */
    public List<ScoreInfo> getHighScores() {
        return this.table;
    }

    /**
     * clear the table.
     */
    public void clear() {
        this.table.clear();
    }

    /**
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     *
     * @param score : player score.
     * @return position in the table.
     */
    public int getRank(int score) {
        for (int i = 0; i < this.size; i++) { //counter.
            if (i < this.table.size()) {
                if (this.getHighScores().get(i).getScore() < score) {
                    return i + 1;
                }
            } else {
                return i + 1;
            }
        }
        return 1 + this.table.size();
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename : where to save.
     * @throws IOException closing the file.
     */
    public void save(File filename) throws IOException {
        PrintWriter pw = null;
        try { //saving
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename)));
            for (int i = 0; i < this.table.size(); i++) {
                pw.println(this.table.get(i).getName());
                pw.println(this.table.get(i).getScore());
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (pw != null) { //closing
                pw.close();
            }
        }
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename : load the table from.
     * @throws IOException closing and reading problems.
     */
    public void load(File filename) throws IOException {
        this.clear();
        BufferedReader br = null;
        int flag = 0;
        String name = null;
        int score;
        try { //reading the data.
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filename)));
            String line;
            while ((line = br.readLine()) != null) {
                if (flag == 0) { //odd lines. player name
                    name = line;
                    flag = 1;
                    continue;
                }
                if (flag == 1) { //even lines. player score
                    score = Integer.parseInt(line);
                    ScoreInfo s = new ScoreInfo(name, score);
                    this.add(s);
                    flag = 0;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) { //closing
                try {
                    br.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename : load the table from it.
     * @return new table , or null if faces a problem in reading or vlosing.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable t = new HighScoresTable(10);
        try {
            t.load(filename);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param s : table size.
     */
    public void setSize(int s) {
        this.size = s;
    }
}