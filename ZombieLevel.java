/**
 * ZombieHouse - a zombie survival game written in Java for CS 351.
 * 
 * Team members:
 * Ramon A. Lovato
 * Danny Gomez
 * James Green
 * Marcos Lemus
 * Mario LoPrinzi
 */

import java.awt.Dimension;

/**
 * ZombieLevel - a wrapper class to encapsulate all data for a single level.
 * 
 * @author Ramon A. Lovato
 * @group Danny Gomez
 * @group James Green
 * @group Marcos Lemus
 * @group Mario LoPrinzi
 */
public class ZombieLevel {
    /*
     * Char codes (not all utilized):
     *   S - small object
     *   M - medium object
     *   L - large object
     *   P - player
     *   Z - zombie
     *   F - firetrap
     *   W - outer wall
     *   I - inner wall
     *   E - exit
     *   . - floor tile
     * 
     *   B - blackened floor tile (not included in XML - only caused by fire
     *       traps).
     */
    private final Tile[][] original;
    private Tile[][] layout;
    private House house;
    
    /**
     * ZombieLevel's constructor.
     * 
     * @param layout 2-D Tile array representing the level's floor layout.
     */
    public ZombieLevel(House current) {
        this.house = house;
        int width = current.getHouseWidth();
        int length = current.getHouseLength();
        Tile[][] array = current.getHouseLayout();
        // On instantiation, layout is set to a copy of the original array.
        layout = new Tile[width][length];
        original = new Tile[width][length];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                original[x][y] = array[x][y].cloneTile();
                layout[x][y] = original[x][y].cloneTile();
            }
        }
    }
    
    /**
     * Changes a single tile to a blackened floor tile.
     * 
     * @param x X-coordinate of tile to modify.
     * @param y Y-coordinate of tile to modify.
     * @return true if change succeeded; false otherwise.
     */
    public boolean blackenTile(int x, int y) {
        // Exterior walls cannot be modified.
        if (layout[x][y].getChar() != 'W') {
            layout[x][y] = new Tile('B', 1, 1, x, y);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Reverts the level to its original state (if the player gets eaten), but
     * doesn't re-randomize.
     */
    public void revert() {
        Tile[][] newLayout = new Tile[layout.length][layout[0].length];
        // Iterate through original and copy the tile values to the new array.
        for (int x = 0; x <= layout.length; x++) {
            for (int y = 0; y <= layout[0].length; y++) {
                newLayout[x][y] = original[x][y].cloneTile();
            }
        }
        // Redirect layout to point to the new array.
        layout = newLayout;
    }
    
    /**
     * Getter for individual tiles.
     * 
     * @param x X-coordinate of tile to get.
     * @param y Y-coordinate of tile to get
     * @return Tile at requested location as char.
     */
    public Tile getTile(int x, int y) {
        return layout[x][y];
    }
    
    /**
     * Getter for size of the level in tiles.
     * 
     * @return Tile size of level as Dimension.
     */
    public Dimension getSize() {
        return new Dimension(layout.length, layout[0].length);
    }
    
    /**
     * Getter for house.
     * 
     * @return The house object.
     */
    public House getHouse() {
        return house;
    }
    
    /**
     * Getter for layout.
     * 
     * @return layout 2-D Tile array representing the level's floor layout.
     */
    public Tile[][] getLayout() {
        return layout;
    }
}
