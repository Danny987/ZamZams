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

/**
 * ZombieLevel - a wrapper class to encapsulate all data for a single level.
 * 
 * @author Ramon A. Lovato
 */
public class ZombieLevel {
    final int[][] original;
    int[][] layout;
    
    /**
     * ZombieLevel's constructor.
     * 
     * @param layout 2-D int array representing the level's floor layout.
     */
    public ZombieLevel(int[][] layout) {
        // On instantiation, layout is set to the original level layout.
        this.layout = original = layout;
    }
    
    /**
     * Changes the state of a single tile.
     * 
     * @param x X-coordinate of tile to modify.
     * @param y Y-coordinate of tile to modify.
     * @param val New int value to set for tile.
     * @return true if change succeeded; false otherwise.
     */
    public boolean change(int x, int y, int val) {
        // Exterior walls cannot be modified.
        
        // TODO Implement.
        
        return false;
    }
    
    /**
     * Getter for individual tiles.
     * 
     * @param x X-coordinate of tile to get.
     * @param y Y-coordinate of tile to get
     * @return State of tile at requested location.
     */
    public int getTile(int x, int y) {
        return layout[x][y];
    }
    
    /**
     * Reverts the level to its original state (if the player gets eaten), but
     * doesn't re-randomize.
     */
    public void revert() {
        layout = original;
    }
    
    /**
     * Getter for layout.
     * 
     * @return layout 2-D int array representing the level's floor layout.
     */
    public int[][] getLayout() {
        return layout;
    }
    
    /**
     * ZombieLevel's main method - primarily for testing.
     * 
     * @param args String array of command-line arguments.
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
