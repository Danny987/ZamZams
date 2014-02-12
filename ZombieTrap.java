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
 * ZombieTrap - a class for handling ZombieHouse's fire traps.
 * CS 351 - Lab 1: Unit Testing Project
 * 
 * Story: As the main game loop and trap designer, I want the player to be able
 * to drop the traps if he/she has any traps in inventory and the current
 * location is valid, so that they can be used against the zombies.
 * 
 * @author Ramon A. Lovato
 * @version 0.1
 */
public class ZombieTrap {
    private int numTraps;
    
    /**
     * Zombietrap's constructor.
     */
    public ZombieTrap() {
        numTraps = 0;
    }
    
    /**
     * Drops a trap at the provided location (if possible). Generally called
     * using the player's current position.
     * 
     * @param x X-coordinate of the tile on which the player is standing.
     * @param y Y-coordinate of the tile on which the player is standing.
     * @param state Numeric value of the target tile. Used to determine what
     *        (if anything) is currently at that location.
     * @return boolean Whether the trap drop was successful.
     */
    public boolean dropTrap(int x, int y, int state) {
        // Check if passed location is valid and if player has any traps. If
        // able, drop a trap and decrement numTraps.
        return false;
    }
    
    /**
     * Checks if the provided location is valid (free of obstructions, etc.).
     * 
     * @param x X-coordinate of tile to test.
     * @param y Y-coordinate of tile to test.
     * @param state Numeric value of the target tile. Used to determine what
     *        (if anything) is currently at that location.
     * @return boolean Whether the provided location is valid.
     */
    private boolean isLocValid(int x, int y, int state) {
        // Test to see if passed tile is valid (most likely map[x][y] == 0).
        return false;
    }
    
    /**
     * ZombieTrap's main method - used for testing.
     * 
     * @param args String array of command-line arguments.
     */
    public static void main(String[] args) {
        // Create an empty "dummy" level. Once the Lexar and logic modules are
        // finished, the actual level tracking will need to be handled
        // externally.
        int dummyLevel[][] = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                dummyLevel[i][j] = 0;
            }
        }
        // Create a new instance of the trap module for testing.
        ZombieTrap traps = new ZombieTrap();
        // Test first with traps.
        traps.numTraps = 2;
        // Test if drop trap will be successful on an empty square.
        assert (traps.isLocValid(0, 0, dummyLevel[0][0])) :
                "empty square test failed";
        // Make a square not empty, then run the test again and make sure it
        // fails (hence the not).
        dummyLevel[1][1] = 1;
        assert (!traps.isLocValid(1, 1, dummyLevel[1][1])) :
                "filled square check passed (test failed)";
        // Since the player has traps, test the valid location to see if drop
        // is successful.
        assert (traps.dropTrap(0, 0, dummyLevel[0][0])) :
                "valid drop with traps failed";
        // Test to make sure it fails with all traps removed.
        traps.numTraps = 0;
        assert (!traps.dropTrap(0,0, dummyLevel[0][0])) :
                "valid drop location with no traps succeeded (test failed)";
    }

}
