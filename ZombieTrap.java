import java.util.ArrayList;

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
    private Player player;
    private ArrayList<Zombie> zombies;
    private ZombieLevel level;
    private Tile[][] map;
    // Needed in order to trigger explosion animation.
    private GameGraphics graphics;
    
    /**
     * Zombietrap's constructor.
     * 
     * @param player Player object of the current player.
     * @param zombies ArrayList<Zombie> list of all current zombies.
     * @param level ZombieLevel holding current level layout.
     * @param graphics GameGraphics object to draw the explosion animation.
     * @param game ZombieMainGame instance of the main game controller.
     */
    public ZombieTrap(Player player, ArrayList<Zombie> zombies,
                      ZombieLevel level, GameGraphics graphics) {
        this.player = player;
        this.zombies = zombies;
        this.level = level;
        this.graphics = graphics;
        map = level.getLayout();
    }
    
    /**
     * Drops a trap at the provided location (if possible). Generally called
     * using the player's current position.
     * 
     * @param x X-coordinate of the tile on which the player is standing.
     * @param y Y-coordinate of the tile on which the player is standing.
     * @return boolean Whether the trap drop was successful.
     */
    public boolean dropTrap(int x, int y) {
        // Tries to drop a trap at passed location. Since ZombieLevel has a
        // method in place for this that checks if the location is valid,
        // this just off-loads the grunt work to that.
        if (player.getFireTrapCount() > 0) {
            if (level.dropTrap(x, y)) {
                player.useFireTrap();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Detonates a trap at the passed position. Chains to traps in adjacent
     * tiles.
     * 
     * @param x X-coordinate of the trap tile to detonate.
     * @param y Y-coordinate of the trap tile to detonate.
     * @return Whether the detonation succeeded.
     */
    public boolean detonate(int x, int y) {
        // Fire trap keycode: 'F'.
        if (isTrap(x, y)) {
            // TODO
            // graphics.blowUp(x, y);
            
            // Check adjacent squares for chainable traps to detonate.
            for (int xOff = -1; xOff <= 1; x++) {
                for (int yOff = -1; yOff <= 1; y++) {
                    if (isTrap(x + xOff, y + yOff)) {
                        chainDetonate(x + xOff, y + yOff);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * A helper method for chain detonations. Uses a small delay to spread out
     * the explosions, then calls detonate on the new trap.
     * 
     * @param x X-coordinate to chain detonate.
     * @param y Y-coordinate to chain detonate.
     * @return Whether the detonation succeeded.
     */
    public boolean chainDetonate(int x, int y) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return detonate(x, y);
    }
    
    /**
     * Just a simple helper that checks the passed tile for a trap.
     * 
     * @param x X-coordinate to check for trap.
     * @param y Y-coordinate to check for trap.
     * @return Boolean if tile contains a trap.
     */
    public boolean isTrap(int x, int y) {
        // Fire trap key code = 'F'.
        return map[x][y].getChar() == 'F';
    }
    
//    /**
//     * ZombieTrap's main method - used for testing.
//     * 
//     * @param args String array of command-line arguments.
//     */
//    public static void main(String[] args) {
//        // Create an empty "dummy" level. Once the Lexar and logic modules are
//        // finished, the actual level tracking will need to be handled
//        // externally.
//        int dummyLevel[][] = new int[5][5];
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                dummyLevel[i][j] = 0;
//            }
//        }
//        // Create a new instance of the trap module for testing.
//        ZombieTrap traps = new ZombieTrap();
//        // Test first with traps.
//        traps.numTraps = 2;
//        // Test if drop trap will be successful on an empty square.
//        assert (traps.isLocValid(0, 0, dummyLevel[0][0])) :
//                "empty square test failed";
//        // Make a square not empty, then run the test again and make sure it
//        // fails (hence the not).
//        dummyLevel[1][1] = 1;
//        assert (!traps.isLocValid(1, 1, dummyLevel[1][1])) :
//                "filled square check passed (test failed)";
//        // Since the player has traps, test the valid location to see if drop
//        // is successful.
//        assert (traps.dropTrap(0, 0, dummyLevel[0][0])) :
//                "valid drop with traps failed";
//        // Test to make sure it fails with all traps removed.
//        traps.numTraps = 0;
//        assert (!traps.dropTrap(0,0, dummyLevel[0][0])) :
//                "valid drop location with no traps succeeded (test failed)";
//    }

}
