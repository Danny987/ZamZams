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

import java.util.List;
import java.awt.Point;
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
    // Tile size.
    private static final int TILE = 50;
    
    // Game clock.
    private Timer timer;
    // The game's frame.
    private ZombieFrame frame;
    // State of the level.
    private ZombieLevel level;
    // The control key states.
    private ZombieKeyStates keys;
    // Title screen.
    private ZombieTitle title;
    // The player.
    // TODO: get other values. These are just for testing.
    private Player player = new Player(new Point(100, 100), (float) 5.0, 3,
            (float) 1.5, (float) 1.0);
    // The zombies.
    // TODO: implement pending completion of zombie class.
    private List<ZombiePlaceholder> zombies;
    
    /**
     * ZombieMainGame's default constructor.
     */
    public ZombieMainGame() {
        startTimer();
    }
    
    /**
     * Hands over primary control from ZombieHouse to ZombieMainGame. Called by
     * ZombieHouse's main method after completing its initialization routines.
     */
    public void takeControl() {
        // TODO: test code.
        // Show the title screen.
        showTitle();
    }
    
    /**
     * Does framey stuff. Called by the timer each frame. Anything that depends
     * on the timer goes in here.
     */
    private void frameUpdate() {      
        // TODO: testing code.
        if (keys.up) {
            player.move(0, 1, false);
        }
        if (keys.down) {
            player.move(0, 2, false);
        }
        if (keys.left) {
            player.move(1, 0, false);
        }
        if (keys.right) {
            player.move(2, 0, false);
        }
        
        // Repaint the GUI.
        frame.repaint();
    }
    
    /**
     * Creates and starts the game timer.
     */
    private void startTimer() {
        ActionListener gameClock = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // Perform framewise updates.
                frameUpdate();
                
                /****************************************/
                /* TODO: timer not triggering properly. */
                /****************************************/
            }
        };
        // 30 hz.
        timer = new Timer(DELAY, gameClock);
        timer.setInitialDelay(DELAY);
    }
    
    /**
     * Displays the title screen.
     */
    private void showTitle() {
        // TODO: testing code.
        title = new ZombieTitle();
        title.setVisible(true);
        while (frame == null) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        frame.getContentPane().add(title);
        frame.pack();
        frame.repaint();
    }
    
    /**
     * Setter for frame - also automatically gets and sets keys. Called by
     * the main program during initialization.
     * 
     * @param frame ZombieFrame in which the game is displayed.
     */
    public void setFrame(ZombieFrame frame) {
        this.frame = frame;
        keys = frame.getKeyStates();
    }
    
    /**
     * Getter for frame.
     * 
     * @return frame ZombieFrame in which the game is displayed.
     */
    public ZombieFrame getFrame() {
        return frame;
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
