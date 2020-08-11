import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * in charge of reading level set file.
 */
public class LevelSetSpecificationReader {
    private java.io.Reader reader;
    private List<LevelSet> levelSetsList;

    /**
     * Constructor.
     * @param reader level_set file.
     * @throws Exception problems in reading or format.
     */
    public LevelSetSpecificationReader(java.io.Reader reader) throws Exception {
        this.reader = reader;
        this.levelSetsList = new ArrayList<>();
        this.split(); //split it's lines.
    }


    /**
     * reading lines and convert the lines to LevelSet object.
     * @throws Exception problems in reading files or format.
     */
    private void split() throws Exception {
        List<LevelSet> levelSets = new ArrayList<>();
        LineNumberReader lineNumberReader = null;
        String line;
        LevelSet levelSet = null; //initialize
        try { // reading the lines.
            lineNumberReader = new LineNumberReader(this.reader);

            while ((line = lineNumberReader.readLine()) != null) {
                if (lineNumberReader.getLineNumber() % 2 == 1) {
                    levelSet = new LevelSet();
                    String[] split = line.trim().split(":");
                    levelSet.setDescription(split[1]);
                    levelSet.setKey(split[0]);

                } else {
                    levelSet.setPath(line.trim());
                    levelSets.add(levelSet);
                    levelSet = null;
                }
            }
        } catch (Exception e) {
            throw new Exception("reading LevelSet file failed! check the format");
        } finally {
            if (lineNumberReader != null) {
                try { //close
                    lineNumberReader.close();
                    this.levelSetsList = levelSets;
                } catch (IOException e) {
                    throw new Exception("closing the file Levelset failed");
                }
            }
        }
    }

    /**
     *
     * @return  list of levels sets.
     */
    public List<LevelSet> getLevelSets() {
        return this.levelSetsList;
    }

}
