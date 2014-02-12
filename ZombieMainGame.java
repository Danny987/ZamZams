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

import java.util.*;
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
    private ZombieKeyBinds keys;
    private boolean hasControl;
    private int frameCounter;
    // TODO: get other values. These are just for testing.
    private Player player = new Player(new Point(100, 100), (float) 5.0, 3,
            (float) 1.5, (float) 1.0);
    // TODO: implement pending completion of zombie class.
    private List<ZombiePlaceholder> zombies;
    
    // A hash map of key states booleans for the inputs.
    private Map<String, Boolean> input;
    
    /**
     * ZombieMainGame's default constructor.
     */
    public ZombieMainGame() {
        hasControl = false;
        
        input = new HashMap<String, Boolean>();
        input.put("up", false);
        input.put("down", false);
        input.put("left", false);
        input.put("right", false);
        input.put("run", false);
        input.put("action", false);
        input.put("escape", false);
        input.put("accept", false);
        
        mode = ZombieMode.TITLE;
        
        frameCounter = 1;
        
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
        // Update the frame counter.
        frameCounter = (frameCounter + 1 > FPS ? 1 : frameCounter++);
        
        // Call the right method depending on the game mode.
        if (mode == ZombieMode.TITLE) {
            titleHelper();
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
        linkToKeyBinds();
        hasControl = true;
        // TODO: test code.
        // Show the title screen.
        showTitle();
    }
    
    /**
     * Updates the input map based on the provided input. Called by key binds.
     * 
     * @param str String representation of the key state change.
     */
    public void updateInput(String str) {
        boolean state = (str.startsWith("released") ? false : true);
        String key = (state ? str : str.substring(10));
        input.put(key, state);
        assert(input.get(key) == state);
    }
    
    /**
     * A helper method to handle keyboard input on the title screen. Called
     * each frame.
     */
    private void titleHelper() {
        title.incrementTime();
        System.out.println("test");
        // Controls.
        if (input.get("left") || input.get("right")) {
            title.switchButton();
        }
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
     * Links the main game controller with an instance of ZombieKeyBinds. The
     * frame must already be set.
     */
    public void linkToKeyBinds() {
        try {
            keys = frame.getKeyBinds();
            keys.linkToMainGame(this);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Getter for timer.
     * 
     * @return timer Main game timer.
     */
    public Timer getTimer() {
        return timer;
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
     * Getter for frameCounter.
     * 
     * @return frameCounter Number of frames elapsed this second.
     */
    public int getFrameCounter() {
        return frameCounter;
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
