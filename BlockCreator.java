/**
 * Block Creator class.
 */
public interface BlockCreator {
    // Create a block at the specified location.

    /**
     * create one block.
     *
     * @param xpos : x-coordinate of upper left point.
     * @param ypos : y-coordinate of upper left point.
     * @return the new block.
     */
    Block create(int xpos, int ypos);
}