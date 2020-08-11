import java.awt.Color;
import java.util.Map;

/**
 * Class that creat blocks.
 * blocks factory.
 */
public class BlocksCreatorObject implements BlockCreator {

    private int blockWidth;
    private int blockHeight;
    private int blockHits;
    private Map<Integer, String> hashFill;
    private Color stroke;

    /**
     * Constructor.
     *
     * @param width    : block wdith.
     * @param height   : block height.
     * @param hits     : hit points.
     * @param stroke   : stroke color.
     * @param hashFill : map between hit points and block background (image or color)
     */
    public BlocksCreatorObject(int width, int height, int hits, Color stroke, Map<Integer, String> hashFill) {
        this.blockWidth = width;
        this.blockHeight = height;
        this.blockHits = hits;
        this.hashFill = hashFill;
        this.stroke = stroke;
    }

    /**
     * create one block.
     *
     * @param xpos : x-coordinate of upper left point.
     * @param ypos : y-coordinate of upper left point.
     * @return the new block.
     */
    public Block create(int xpos, int ypos) {
        //create
        Block newBlock = new Block(new Rectangle(new Point(xpos, ypos), this.blockWidth, this.blockHeight)
                , this.stroke, this.hashFill);
        if (newBlock.getHitPoints() != -2 && newBlock.getHitPoints() != -3) { //not ballBlock or death block.
            newBlock.setNumber(this.blockHits);
        }

        return newBlock; //return the new block.
    }

}
