import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;

/**
 * in charge of get levels values (except block values).
 */
public class LevelSpecification {
    private List<String> level;
    private String levelName;
    private List<Velocity> ballsVelocity;
    private Image backgroundImg = null;
    private Color backgroundColor = null;
    private int paddleSpeed;
    private int paddleWidth;
    private int numOfBlock;
    private int rowHeight;
    private int blocksStartX;
    private int blocksStartY;
    private java.io.Reader blocksDef;


    /**
     * Constructor.
     *
     * @param level : list of strings that define level values.
     * @throws Exception problems in reading file or format.
     */
    public LevelSpecification(List<String> level) throws Exception {
        this.level = level;
        this.mapping();
    }

    /**
     * convert the strings of levels def to values.
     *
     * @throws Exception problems in format or reading files.
     */
    private void mapping() throws Exception {
        for (int i = 0; i < this.level.size(); i++) {
            String current = this.level.get(i);
            if (current.startsWith("level_name")) { //get level name.
                try {
                    this.levelName = current.substring(11);
                } catch (Exception e) {
                    throw new Exception("invalid level name");
                }
                continue;
            }
            if (current.startsWith("paddle_width")) { //get paddle width
                try {
                    this.paddleWidth = Integer.parseInt(current.substring(13));
                    if (this.paddleWidth <= 0) {
                        throw new Exception("paddle width must be a positive integer!");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid paddle width value");
                }
                continue;
            }
            if (current.startsWith("paddle_speed")) { //get paddle speed
                try {
                    this.paddleSpeed = Integer.parseInt(current.substring(13));
                    if (this.paddleSpeed < 0) {
                        throw new Exception("paddle speed must be a positive integer!");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid paddle speed value");
                }
                continue;
            }
            if (current.startsWith("num_blocks")) { //get num of blocks to remove
                try {

                    this.numOfBlock = Integer.parseInt(current.substring(11));
                    if (this.numOfBlock <= 0) {
                        throw new Exception("block's num must be a positive integer!");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid block's num value");
                }
                continue;
            }
            if (current.startsWith("blocks_start_x")) { //get blocks x-coordinate of upper left point
                try {
                    this.blocksStartX = Integer.parseInt(current.substring(15));
                    if (this.blocksStartX < 0) {
                        throw new Exception("blocks_start_x must be a positive value");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid value of blocks_start_x");
                }
                continue;
            }
            if (current.startsWith("blocks_start_y")) { //get blocks y-coordinate of upper left point
                try {
                    this.blocksStartY = Integer.parseInt(current.substring(15));
                    if (this.blocksStartY < 0) {
                        throw new Exception("blocks_start_y must be a positive value");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid value of blocks_start_y");
                }
                continue;
            }
            if (current.startsWith("block_definitions")) { //get blocks def file.
                String blockDefString = (current.substring(18));
                BufferedReader br = null;
                try {
                    br = new
                            BufferedReader(new InputStreamReader(ClassLoader.
                            getSystemClassLoader().getResourceAsStream(blockDefString)));
                    this.blocksDef = br;
                } catch (Exception e) {
                    throw new Exception("problem in block_definitions file");
                }
                continue;
            }
            if (current.startsWith("background")) { //get background.
                if (current.contains("color")) {
                    try { //invalid format if color
                        String k = current.substring(17, current.length() - 1);
                        ColorsParser colorsParser = new ColorsParser();
                        this.backgroundColor = colorsParser.colorFromString(k);
                        if (backgroundColor == null) {
                            throw new Exception("invalid back ground color");
                        }
                    } catch (Exception e) {
                        throw new Exception("invalid value of background color");
                    }
                    continue;
                } else if (current.contains("image")) { //if background is an image
                    try {
                        String imageString = current.substring(17, current.length() - 1);
                        Image img;
                        img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(imageString));
                        this.backgroundImg = img;
                    } catch (IOException e) {
                        throw new Exception("invalid background image value or image not found!");
                    }
                }
            }
            if (current.startsWith("row_height")) { //get row height value.
                try {
                    this.rowHeight = Integer.parseInt(current.substring(11));
                    if (this.rowHeight <= 0) {
                        throw new Exception("row height must be a positive integer value");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid value of row_height");
                }
                continue;
            }
            if (current.startsWith("ball_velocities")) { //get list of balls velocities.
                try {
                    String velocities = current.substring(16);
                    List<Integer> commaIndex = new ArrayList();
                    List<Integer> spacesIndex = new ArrayList<>();
                    List<Velocity> l = new ArrayList<>();
                    int angle;
                    int speed;

                    for (int k = 0; k < velocities.length(); k++) { //spaces in the line
                        if (velocities.charAt(k) == ',') {
                            commaIndex.add(k);
                        } else if (velocities.charAt(k) == ' ') {
                            spacesIndex.add(k);
                        }
                    }
                    spacesIndex.add(velocities.length());
                    angle = Integer.parseInt(velocities.substring(0, commaIndex.get(0)));
                    speed = Integer.parseInt(velocities.substring(commaIndex.get(0) + 1, spacesIndex.get(0)));
                    l.add(Velocity.fromAngleAndSpeed(angle, speed));
                    for (int j = 1; j < commaIndex.size(); j++) { //get velocities.
                        angle = Integer.parseInt(velocities.substring(spacesIndex.get(j - 1) + 1, commaIndex.get(j)));
                        speed = Integer.parseInt(velocities.substring(commaIndex.get(j) + 1, spacesIndex.get(j)));
                        l.add(Velocity.fromAngleAndSpeed(angle, speed));
                    }
                    this.ballsVelocity = l;
                } catch (Exception e) {
                    throw new Exception("invalid value in ball velocity");
                }
            }
        }
    }

    /**
     * @return block def file.
     */
    public Reader getBlocksDef() {
        return this.blocksDef;
    }

    /**
     * @return back ground color if there. if not , return null.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @return level name.
     */
    public String getLevelName() {
        return this.levelName;
    }

    /**
     * @return balls velocities.
     */
    public List<Velocity> getBallsVelocity() {
        return this.ballsVelocity;
    }

    /**
     * @return background image if there, null if not.
     */
    public Image getBackgroundImg() {
        return this.backgroundImg;
    }

    /**
     * @return block start x-coordinate.
     */
    public int getBlocksStartX() {
        return this.blocksStartX;
    }

    /**
     * @return block start y-coordinate.
     */
    public int getBlocksStartY() {
        return this.blocksStartY;
    }

    /**
     * @return num of blocks should remove to pass the level.
     */
    public int getNumOfBlock() {
        return this.numOfBlock;
    }

    /**
     * @return paddle speed.
     */
    public int getPaddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * @return paddle width.
     */
    public int getPaddleWidth() {
        return this.paddleWidth;
    }

    /**
     * @return row height.
     */
    public int getRowHeight() {
        return rowHeight;
    }

    /**
     * decrease num of blovk to remove 1.
     */
    public void decreaseNumOfBlocksToRemove() {
        this.numOfBlock--;
    }
}
