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
 * @version 0.1
 */
public class ZombieMainGame {
    // Main game timer.
    private static final int FPS = 30;
    private static final int DELAY = 1000/FPS;
    // Tile size.
    private static final int TILE = 50;
    
    private Timer timer;
    private ZombieFrame frame;
    private ZombieLevel zombieLevel;
    private ZombieMode mode;
    private ZombieTitle title;
    private ZombieKeyBinds keys;
    private Level level;
    private boolean hasControl;
    private int frameCounter;
    private GameGraphics graphics;
    
    private int levelNum;
    private long score;
    private int maxLevel;
    private Player player;
    private ArrayList<Zombie> zombies;
    
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
        input.put("enter", false);
        
        mode = ZombieMode.TITLE;
        
        levelNum = 0;
        score = 0;
        maxLevel = 0;
        
        frameCounter = 1;
        
        // Create and start the game clock.
        ActionListener gameClock = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // Perform framewise updates.
                if (hasControl) {
                    frameUpdate();
                }
            }
        };
        // The FPS is set to 30, but this is a little misleading. Since the
        // timer delay is an int, 1000 ms/30 FPS = 33.33 repeating rounds down
        // to 33. Consequently, the timer gains one frame for every three
        // so the effective frame rate is actually more like 40.
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
        if (++frameCounter > FPS) {
            frameCounter = 1;
        }
        
        // Call the right method depending on the game mode.
        switch (mode) {
            case TITLE:
                titleHelper();
                break;
            case PAUSED:
                // If paused.
                break;
            case PLAYING:
                // If playing.
            	movementHelper();
            	if (graphics != null) {
            		graphics.update();
            	}
                break;
            default:
                break;
        }
        
        // Repaint the GUI.
        frame.repaint();
    }
    
    /**
     * Starts game play.
     */
    public void startGame() {
        // Start the game.
        mode = ZombieMode.PLAYING;
        levelNum = 0;
        score = 0;
        zombieLevel = new ZombieLevel(level.houseList.get(levelNum));
        player = zombieLevel.getHouse().player;
        player.buildMap(zombieLevel.getHouse());
        zombies = zombieLevel.getHouse().zombieList;
        assert (player != null) : "player is null";
        assert (zombies != null) : "zombies is null";
        assert (zombieLevel != null) : "zombieLevel is null";
        
        frame.startGraphics(zombieLevel.getHouse(), 0);
        graphics = frame.getGameGraphics();
        graphics.update();
        
        // Main game loop.
        
        // TEST
        printCurrentLevel();
    }
    
    /**
     * Advances the level if there are more levels available.
     */
    public void nextLevel() {
    	// TODO
    }
    
    /**
     * Restarts the current level.
     */
    public void restartLevel() {
    	mode = ZombieMode.PAUSED;
        zombieLevel.revert();
        // TODO: get revert methods for both.
        player = zombieLevel.getHouse().player;
        zombies = zombieLevel.getHouse().zombieList;
    }
    
    /**
     * Kills the player.
     */
    public void killPlayer() {
        // TODO
    }
    
    /**
     * Ends the game and returns to tile.
     */
    public void gameOver() {
    	score = 0;
        mode = ZombieMode.TITLE;
    }
    
    /**
     * Hands over primary control from ZombieHouse to ZombieMainGame. Called by
     * ZombieHouse's main method after completing its initialization routines.
     */
    public void takeControl() {    
        linkToKeyBinds();
        hasControl = true;
        // Show the title screen.
        showTitle();
    }
    
    /**
     * Updates the input map based on the provided input. Called by key binds.
     * 
     * @param str String representation of the key state change.
     */
    public void updateInput(String str) {
        Boolean state = (str.startsWith("released") ? false : true);
        String key = (state ? str : str.substring(9));
        input.put(key, state);
        // If one of the directions was pressed, automatically release the
        // opposite direction.
        if (state) {
            switch (str) {
                case "up":
                    input.put("down", false);
                    break;
                case "down":
                    input.put("up", false);
                    break;
                case "left":
                    input.put("right", false);
                    break;
                case "right":
                    input.put("left", false);
                    break;
                case "action":
                	input.put("run", false);
                default:
                    break;
            }
        }
        
        assert(input.get(key) == state);
    }
    
    /**
     * A helper method for player movement. Converts key binding states into
     * the format required by player.move.
     */
    private void movementHelper() {
    	boolean run = input.get("run");
    	int leftRight, upDown;
    	
    	// Left/right.
    	if (input.get("left")) {
    		leftRight = 1;
    	} else if (input.get("right")) {
    		leftRight = 2;
    	} else {
    		leftRight = 0;
    	}
    	// Up/down.
    	if (input.get("up")) {
    		upDown = 1;
    	} else if (input.get("down")) {
    		upDown = 2;
    	} else {
    		upDown = 0;
    	}
    	
    	if (player != null) {
    		player.move(leftRight, upDown, run);
    	}
    }
    
    /**
     * A helper method to handle keyboard input on the title screen. Called
     * each frame.
     */
    private void titleHelper() {
        if (title != null) {
            // Controls. Uses the frame counter to throttle button switches.
            if ((input.get("left") || input.get("right")) &&
                    frameCounter % 7 == 0) {
                title.switchButton();
            }
            if (input.get("enter") || input.get("action")) {
                if (title.getSelected() == "start") {
                    startGame();
                } else {
                    shutdown();
                }
            }
            if (input.get("escape")) {
                shutdown();
            }
        }
    }
    
    /**
     * Displays the title screen.
     */
    private void showTitle() {
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
        assert (keys != null) : "keys is null";
    }
    
    /**
     * Links the game controller with the level object containing all houses.
     * 
     * @param level Level to link with.
     */
    public void linkToLevel(Level level) {
        this.level = level;
        maxLevel = level.houseList.size();
    }
    
    /**
     * Exits the program.
     */
    public void shutdown() {
        // Exit with error-free status.
        System.exit(0);
    }
    
    /**
     * A debugging method that prints out the input states.
     */
    public void printInput() {
        System.out.println("---input map---");
        for (Map.Entry<String, Boolean> entry : input.entrySet()) {
            String key = entry.getKey();
            String value = (entry.getValue() ? "true" : "false");
            System.out.println(key + ": " + value);
        }
    }
    
    /**
     * A debugging method that prints the current level to the console.
     */
    public void printCurrentLevel() {
    	Tile[][] array = zombieLevel.getLayout();
        for(int y = 0; y < array.length; y++)
        {
            for(int x = 0; x < array[y].length; x++)
                System.out.print(array[x][y].getChar());
            System.out.print("\n");
        }
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
