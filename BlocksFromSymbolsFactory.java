import java.util.Map;

/**
 * create block according to specific symbol.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;


    /**
     * Constructor.
     *
     * @param spacerWidths  : spacers map.
     * @param blockCreators : block creators.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;

    }


    /**
     * @param s : symbol.
     * @return true if s is space symbol, false otherwise.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * @param s : symbol.
     * @return true if s is block symbol , false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * get blocks according to the symbol.
     *
     * @param s    : symbol.
     * @param xpos : x-coordinate of upper left point.
     * @param ypos : y-coordinate of upper left point.
     * @return new block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * @param s : symbol
     * @return space width.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}