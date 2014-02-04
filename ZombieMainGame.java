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
import java.awt.Container;
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
    private static final int DELAY = 1000/FPS;
    // Tile size.
    private static final int TILE = 50;
    
    private Timer timer;
    private ZombieFrame frame;
    private ZombieLevel level;
    private ZombieMode mode;
    private ZombieTitle title;
    // TODO: get other values. These are just for testing.
    private Player player = new Player(new Point(100, 100), (float) 5.0, 3,
            (float) 1.5, (float) 1.0);
    // TODO: implement pending completion of zombie class.
    private List<ZombiePlaceholder> zombies;
    
    // Key states variables use wrappers so they can be passed by reference.
    private Boolean up;
    private Boolean down;
    private Boolean left;
    private Boolean right;
    private Boolean run;
    private Boolean action;
    private Boolean esc;
    private Boolean accept;
    
    /**
     * ZombieMainGame's default constructor.
     */
    public ZombieMainGame() {
        up = down = left = right = run = action = esc = accept = false;
        mode = ZombieMode.TITLE;
        
        // Create and start the game clock.
        ActionListener gameClock = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // Perform framewise updates.
                frameUpdate();
            }
        };
        // 30 hz.
        timer = new Timer(DELAY, gameClock);
        timer.setInitialDelay(DELAY);
        timer.addActionListener(gameClock);
        timer.start();
    }
    
    /**
     * Does framey stuff. Called by the timer each frame. Anything that depends
     * on the timer goes in here.
     */
    private void frameUpdate() {
        if (mode == ZombieMode.TITLE) {
            // If in title screen.
        } else if (mode == ZombieMode.PAUSED) {
            // If paused.
        } else {
            // If playing.
            
            // TODO: testing code.
//            if (keys.up) {
//                player.move(0, 1, false);
//            }
//            if (keys.down) {
//                player.move(0, 2, false);
//            }
//            if (keys.left) {
//                player.move(1, 0, false);
//            }
//            if (keys.right) {
//                player.move(2, 0, false);
//            }
        }
        
        // Repaint the GUI.
        frame.repaint();
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
        Container content = frame.getContentPane();
        content.add(title);
        frame.setContentPane(content);
        frame.repaint();
    }
    
    /**
     * Setter for frame. Called by the main program during initialization.
     * 
     * @param frame ZombieFrame in which the game is displayed.
     */
    public void setFrame(ZombieFrame frame) {
        this.frame = frame;
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
     * Exits the program.
     */
    public void shutdown() {
        // Exit with error-free status.
        System.exit(0);
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
