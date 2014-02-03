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

import java.awt.event.*;
import javax.swing.Timer;

/**
 * ZombieMainGame - controls ZombieHouse's main game loop and timing.
 * 
 * @author Ramon A. Lovato
 * @group Danny Gomez
 * @group James Green
 * @group Marcos Lemus
 * @group Mario LoPrinzi
 */
public class ZombieMainGame {
    // Main game timer.
    private static final int FPS = 30;
    private static final int DELAY = FPS * 1000;
    private Timer timer;
    
    // State of the level.
    private ZombieLevel level;
    
    /**
     * ZombieMainGame's default constructor.
     */
    public ZombieMainGame() {
        startTimer();
    }
    
    /**
     * Called by the timer each frame. Anything that depends on the timer goes
     * in here.
     */
    private void frameUpdate() {
        // Do framey stuff.
    }
    
    /**
     * Creates and starts the game timer.
     */
    private void startTimer() {
        ActionListener gameClock = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // Perform framewise updates.
                frameUpdate();
            }
        };
        // 30 hz.
        timer = new Timer(DELAY, gameClock);
        timer.setInitialDelay(DELAY);
    }
    
    /**
     * Gets the state of the current level.
     * 
     * @return level ZombieLevel containing the state of the current level.
     */
    public ZombieLevel getLevel() {
        return level;
    }

    /**
     * ZombieMainGame's main method - used mostly for testing purposes.
     * 
     * @param args String array of command-line arguments.
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

}
