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
import java.awt.Point;
import java.util.ArrayList;

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
     * S - small object
     * M - medium object
     * L - large object
     * P - player
     * Z - zombie
     * F - firetrap
     * W - outer wall
     * I - inner wall
     * E - exit
     * . - floor tile
     * 
     * B - blackened floor tile (not included in XML - only caused by fire
     *     traps).
     */
    private final Point player;
    private final ArrayList<ZombiePlaceholder> zombies;
    private final char[][] original;
    private char[][] layout;
    
    /**
     * ZombieLevel's constructor.
     * 
     * @param layout 2-D char array representing the level's floor layout.
     * @throws NoPlayerStartException Missing player start location.
     * @throws MultiplePlayerStartsException Too many player start locations.
     */
    public ZombieLevel(char[][] layout) throws NoPlayerStartException,
        MultiplePlayerStartsException {
        // Before storing the original level, we need to go through and grab
        // the player and zombie locations and remove them from the array.
        try {
            player = setPlayer();
        } catch (NoPlayerStartException | MultiplePlayerStartsException ex) {
            throw ex;
        }
        zombies = setZombies();
        // On instantiation, layout is set to a copy of the original array.
        this.layout = new char[layout.length][layout[0].length];
        original = new char[layout.length][layout[0].length];
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y < layout[0].length; y++) {
                this.layout[x][y] = original[x][y] = layout[x][y];
            }
        }
    }
    
    /**
     * Finds the player start location in the array and returns it, changing
     * the tile at that location to a basic floor tile.
     * 
     * @throws NoPlayerStartException Missing player start location.
     * @throws MultiplePlayerStartsException Too many player start locations.
     * @return Point containing the player start position (in tiles).
     */
    private Point setPlayer() throws NoPlayerStartException,
        MultiplePlayerStartsException {
        Point player = null;
        // Searches the array until it finds the player start location.
        for (int x = 0; x < getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                if (layout[x][y] == 'P') {
                    // Once it finds the player, it needs to remove the player
                    // marker from the array and replace it with a normal
                    // floor tile. If player isn't null, then there are multiple
                    // start locations, so the level is invalid.
                    if (player == null) {
                        layout[x][y] = '.';
                        player = new Point(x, y);
                    } else {
                        throw new MultiplePlayerStartsException();
                    }
                }
            }
        }
        // If it didn't find a start location, then the level is invalid.
        if (player == null) {
            throw new NoPlayerStartException();
        }
        return player;
    }
    
    /**
     * Finds the zombie start locations, generates new zombies from them, and
     * places them into a collection.
     * 
     * @return ArrayList<Zombie> containing the zombies.
     */
    private ArrayList<ZombiePlaceholder> setZombies() {
        ArrayList<ZombiePlaceholder> zombies = 
                new ArrayList<ZombiePlaceholder>();
        // Searches the array for the zombie start locations and uses them to
        // create new zombies. Since the extremes of the array are always
        // exterior walls or the exit, we can start at 1 instead of 0 and end
        // at width/length - 1, slightly reducing the search area.
        for (int x = 1; x < getSize().width - 1; x++) {
            for (int y = 1; y < getSize().height - 1; y++) {
                if (layout[x][y] == 'Z') {
                    // TODO: Update this once you get the real zombie class.
                    zombies.add(new ZombiePlaceholder());
                }
            }
        }
        
        return zombies;
    }
    
    /**
     * Changes the state of a single tile.
     * 
     * @param x X-coordinate of tile to modify.
     * @param y Y-coordinate of tile to modify.
     * @param val New char value to set for tile.
     * @return true if change succeeded; false otherwise.
     */
    public boolean change(int x, int y, char val) {
        // Exterior walls cannot be modified.
        if (layout[x][y] != 'W' && layout[x][y] != 'E') {
            layout[x][y] = val;
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
        char[][] newLayout = new char[layout.length][layout[0].length];
        // Iterate through original and copy the tile values to the new array.
        for (int x = 0; x <= layout.length; x++) {
            for (int y = 0; y <= layout[0].length; y++) {
                newLayout[x][y] = original[x][y];
            }
        }
        // Redirect layout to point to the new array.
        layout = newLayout;
    }
    
    /**
     * Getter for the player start location.
     * 
     * @return player Point containing the player start location.
     */
    public Point getPlayer() {
        return player;
    }
    
    /**
     * Getter for the list of zombies.
     * 
     * @return zombies ArrayList<Zombie> containing the zombies.
     */
    public ArrayList<ZombiePlaceholder> getZombies() {
        return zombies;
    }
    
    /**
     * Getter for individual tiles.
     * 
     * @param x X-coordinate of tile to get.
     * @param y Y-coordinate of tile to get
     * @return State of tile at requested location as char.
     */
    public char getTile(int x, int y) {
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
     * Getter for layout.
     * 
     * @return layout 2-D int array representing the level's floor layout.
     */
    public char[][] getLayout() {
        return layout;
    }
}
