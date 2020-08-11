import java.awt.Color;
import java.io.IOException;
import java.io.BufferedReader;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * given a file ,reading it and return appropriate levels.
 */
public class LevelSpecificationReader {
    private List<List<String>> blocksLayout;

    /**
     * given a specific file . return a list of levels information.
     *
     * @param reader : levels definition file.
     * @return list of level information that this file define.
     * @throws Exception problems in reading or closing the file or invalid format.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) throws Exception {
        List<LevelInformation> levelsInformation = new ArrayList<>(); //initialize

        //split every level into list of strings.
        List<List<String>> levels = this.splitIntoLevels(reader);
        for (int i = 0; i < levels.size(); i++) { //loop of each level info
            LevelSpecification l = new LevelSpecification(levels.get(i)); //from strings to values.
            BlocksDefinitionReader br = new BlocksDefinitionReader(); //reading blocks def
            BlocksFromSymbolsFactory bf = br.fromReader(l.getBlocksDef()); //factory with maps according to symbols.
            LevelInformation li = this.getLevelInfo(l, bf, i); //get level-information object;
            levelsInformation.add(li); //add to the list.
        }

        return levelsInformation; //return the list of the levels info.
    }

    /**
     * split the file into levels separately.
     *
     * @param reader : levels def file.
     * @return list of list of string , every list contain one level info.
     * @throws Exception problems in format , or reading and closing the file.
     */
    private List<List<String>> splitIntoLevels(java.io.Reader reader) throws Exception {
        List<List<String>> levels = new ArrayList<>();
        List<String> level = null;
        this.blocksLayout = new ArrayList<>();
        List<String> blockLayout = null;
        BufferedReader br = (BufferedReader) reader;
        String line;
        int flag = 0;
        int flag1 = 0;
        int checker = 0;
        try {
            while ((line = br.readLine()) != null) {
                if (line.equals("") || line.startsWith("#")) { //ignore empty line and notes.
                    continue;
                }
                if (line.equals("END_BLOCKS")) { //end block layout def.
                    flag1 = 0; //ready to receive one more
                    this.blocksLayout.add(blockLayout); //add block layout to the list.
                    checker++;

                }
                if (flag1 == 1) {
                    blockLayout.add(line);

                }
                if (line.equals("START_BLOCKS") && flag1 == 0) { //start block layout def.
                    blockLayout = new ArrayList<>();
                    flag1 = 1;
                    checker++;
                }

                if (line.equals("END_LEVEL")) { //end level definitions.
                    flag = 0;
                    checker++;
                    if (checker != 4) {
                        throw new Exception("invalid levels defintion format!");
                    }
                    levels.add(level);
                    checker = 0;
                }
                if (line.equals("START_LEVEL")) { //start level definitions.
                    flag = 1;
                    level = new ArrayList<>();
                    checker++;
                    continue;
                }
                if (flag == 1) {
                    level.add(line);
                }


            }
        } catch (IOException e) {
            throw new Exception("problems in reading levels definition file.");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new Exception("closing levels definitions failed");
                }
            }
        }

        return levels;
    }

    /**
     * reading block definition layout in levels def file and get level information.
     *
     * @param ls : Levels values.
     * @param bf : blocks values.
     * @param i  : number in the list.
     * @return level information.
     */
    private LevelInformation getLevelInfo(LevelSpecification ls, BlocksFromSymbolsFactory bf, int i) {
        List<Block> blocks = new ArrayList<>();
        String line;
        int blockX = ls.getBlocksStartX();
        int blockY = ls.getBlocksStartY();
        int rowHeight = ls.getRowHeight();
        int rowsCounter = 0;
        boolean flag = false;
        for (int j = 0; j < this.blocksLayout.get(i).size(); j++) { //reading blocksLayout

            line = this.blocksLayout.get(i).get(j);
            if (line.isEmpty() || line.startsWith("#")) { //empty or note lines.
                continue;
            } else {
                if (flag) {
                    rowsCounter++;
                } else {
                    flag = true;
                }
            }
            if (bf.isSpaceSymbol(line)) {
                continue;
            }
            char[] charArr = line.toCharArray();
            for (int k = 0; k < charArr.length; k++) {
                if (bf.isSpaceSymbol(charArr[k] + "")) {
                    blockX += bf.getSpaceWidth(charArr[k] + "");
                } else if (bf.isBlockSymbol(charArr[k] + "")) {
                    Block newBlock = bf.getBlock(charArr[k] + "", blockX, blockY + (rowHeight * rowsCounter));
                    blocks.add(newBlock);
                    blockX += newBlock.getWidth();
                }
            }
            blockX = ls.getBlocksStartX();

        }

        for (int j = 0; j < blocks.size(); j++) { //death blocks or Balls-Producer block.
            if (blocks.get(j).getHitPoints() == -3 || blocks.get(j).getHitPoints() == -2) {
                ls.decreaseNumOfBlocksToRemove();
            }
        }

        //get level information.
        LevelInformation l = new LevelInformation() {
            @Override
            public int numberOfBalls() {
                List<Velocity> m = ls.getBallsVelocity();
                return m.size();
            }

            @Override
            public List<Velocity> initialBallVelocities() {
                return ls.getBallsVelocity();
            }

            @Override
            public int paddleSpeed() {
                return ls.getPaddleSpeed();
            }

            @Override
            public int paddleWidth() {
                return ls.getPaddleWidth();
            }

            @Override
            public String levelName() {
                return ls.getLevelName();
            }

            @Override
            public Color getBackgroundColor() {
                return ls.getBackgroundColor();
            }

            @Override
            public Image getBackgroundImg() {
                return ls.getBackgroundImg();
            }

            @Override
            public List<Block> blocks() {
                return blocks;
            }

            @Override
            public int numberOfBlocksToRemove() {
                return ls.getNumOfBlock();
            }
        };
        return l;
    }


}