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

import application.Sound2D;

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
	private final static boolean DEBUG = false;

    // Main game timer.
    public static final int FPS = 30;
    public static final int DELAY = 1000/FPS;
    // Number of pixels per tile.
    public static final int TILE = 50;
    
    private Timer timer;
    private ZombieFrame frame;
    private ZombieLevel zombieLevel;
    private ZombieMode mode;
    private ZombieTitle title;
    private ZombieKeyBinds keys;
    private Level level;
    private House house;
    private boolean hasControl;
    private int frameCounter;
    private GameGraphics graphics;
    private Point playerStart;
    private Sound2D sounds;
    
    private int levelNum;
    private long score;
    private long initialScore;
    private int maxLevel;
    private int tileSet;
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
        initialScore = 0;
        maxLevel = 0;
        tileSet = 0;
        
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
        // Update the frame counter. Used by the zombie AI, etc.
        if (frameCounter > FPS) {
            frameCounter = 1;
        }
        
        // Call the right method depending on the game mode.
        switch (mode) {
            case TITLE:
                titleHelper();
                break;
            case PAUSED:
                // If paused.
            	pausedHelper();
                break;
            case PLAYING:
                // If playing.
            	playingHelper();
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
        maxLevel = level.houseList.size() - 1;
        levelNum = 0;
        score = 0;
        initialScore = 0;
        zombieLevel = new ZombieLevel(level.houseList.get(levelNum));
        house = zombieLevel.getHouse();
        player = house.player;
        playerStart = new Point(player.getPosition().x, player.getPosition().y);
        player.buildMap(house);
        zombies = house.zombieList;
        assert (player != null) : "player is null";
        assert (zombies != null) : "zombies is null";
        assert (zombieLevel != null) : "zombieLevel is null";
        
        frame.startGraphics(house, tileSet);
        graphics = frame.getGameGraphics();
        graphics.update();
        
        // Change the music.
        if (sounds != null) {
            sounds.switchMenu();
        }
        
        // DEBUG
        if (DEBUG) {
        	printCurrentLevel();
        }
    }
    
    /**
     * Advances the level if there are more levels available.
     */
    public void nextLevel() {
    	if (levelNum == maxLevel) {
    		winGame();
    	} else {
    		zombieLevel = new ZombieLevel(level.houseList.get(++levelNum));
    		tileSet = levelNum % 5;
    		house = zombieLevel.getHouse();
    		player = house.player;
    		player.buildMap(house);
    		zombies = house.zombieList;
    		graphics.changeHouse(house, tileSet);
    		score += 1000;
    		initialScore = score;
    	}
    }
    
    /**
     * Wins the game.
     */
    public void winGame() {
    	// TODO: do win stuff.
    	
    	// End the game.
    	gameOver();
    }
    
    /**
     * Restarts the current level.
     */
    public void restartLevel() {
        zombieLevel.revert();
        // TODO: get revert methods for both. Will need to reinitialize when
        // restarting the level or game.
        player = zombieLevel.getHouse().player;
        zombies = zombieLevel.getHouse().zombieList;
        player.position = new Point(playerStart.x, playerStart.y);
        score = initialScore;
        pause();
    }
    
    /**
     * Kills the player.
     */
    public void killPlayer() {
        // TODO
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        restartLevel();
    }
    
    /**
     * Pause the game.
     */
    public void pause() {
    	mode = ZombieMode.PAUSED;
    	clearKeyBinds();
    	// This sleep throttles the next frame update so that the key bind
    	// doesn't immediately change back to pressed.
    	try {
    		Thread.sleep(500);
    	} catch (InterruptedException ex) {
    		ex.printStackTrace();
    	}
    }
    
    /**
     * Unpause the game.
     */
    public void unpause() {
    	mode = ZombieMode.PLAYING;
    }
    
    /**
     * Ends the game and returns to tile.
     */
    public void gameOver() {
    	score = 0;
    	levelNum = 0;
        mode = ZombieMode.TITLE;
        frame.replace(title);
        assert (frame.getContentPane().getComponent(0) == title);
        // Throttle the return to the title screen so the key binds don't
        // automatically reactivate.
        try {
        	Thread.sleep(500);
        } catch (InterruptedException ex) {
        	ex.printStackTrace();
        }
        clearKeyBinds();
        // Switch the sound back.
        sounds.switchMenu();
    }
    
    /**
     * Hands over primary control from ZombieHouse to ZombieMainGame. Called by
     * ZombieHouse's main method after completing its initialization routines.
     */
    public void takeControl() {    
        linkToKeyBinds();
        assert (keys != null);
        hasControl = true;
        // Show the title screen.
        showTitle();
        assert (hasControl);
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
     * Sets all key binds to false.
     */
    public void clearKeyBinds() {
    	for (Map.Entry<String, Boolean> entry : input.entrySet()) {
    		input.put(entry.getKey(), false);
    		assert(!input.get(entry.getKey()));
    	}
    }
    
    /**
     * A helper method for player movement and input. Converts key binding
     * states into the format required by player.move.
     */
    private void playingHelper() {
    	boolean run = input.get("run");
    	int leftRight, upDown;
    	// Score increases every frame. Certain other actions, such as killing
    	// zombies and advancing to the next level, provide additional boosts.
    	score++;
    	
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
    	// Action.
    	if (input.get("action")) {
    		// TODO
    	}
    	// Pause.
    	if (input.get("escape")) {
    		pause();
    	}
    	
    	// Move player.
    	if (player != null) {
    		player.move(leftRight, upDown, run);
    	}
    	// Move zombies.
    	if (zombies != null) {
    		if (frameCounter == 1) {
    			// Update zombie AI.
    			for (Zombie zombie : zombies) {
    				zombie.chooseDirection();
    			}
    		}
    		Zombie.zombieWalk(zombies);
    	}
    }
    
    /**
     * Places a fire trap if on an empty tile. Picks up a fire trap if on a
     * fire trap.
     */
    public void useTrap() {
        // TODO
    }
    
    /**
     * Converts pixel positions into tile positions.
     * 
     * @param loc Point object containing the pixel positions to check.
     * @return Point object containing the tile positions.
     */
    public static Point whichTile(Point loc) {
    	return new Point(loc.x/TILE, loc.y/TILE);
    }  
    
    /**
     * Paused helper.
     */
    public void pausedHelper() {
    	// Pressing escape again quits the game. Enter, space or P unpauses.
    	if (input.get("enter") || input.get("action")) {
    		unpause();
    	} else if (input.get("escape")) {
    		gameOver();
    	}
    }
    
    /**
     * A helper method to handle keyboard input on the title screen. Called
     * each frame.
     */
    private void titleHelper() {
        if (title != null) {
            // Controls.
            if (input.get("left") || input.get("right")) {
            	clearKeyBinds();
                title.switchButton();
                // Throttle input so it doesn't constantly switch.
                try {
                	Thread.sleep(200);
                } catch (InterruptedException ex) {
                	ex.printStackTrace();
                }
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
    	mode = ZombieMode.TITLE;
    	if (title == null) {
    		title = new ZombieTitle(frame.getWidth(), frame.getHeight());
    	}
    	assert (title != null);
    	
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
     * Getter for the frame counter.
     * 
     * @return Current frame of the cycle.
     */
    public int getFrameCounter() {
        return frameCounter;
    }
    
    /**
     * Setter for frame. Called by the main program during initialization.
     * 
     * @param frame ZombieFrame in which the game is displayed.
     */
    public void setFrame(ZombieFrame frame) {
        this.frame = frame;
        assert frame != null;
    }
    
    /**
     * Links with the sound module.
     * 
     * @param sounds Sound2D instance of the sound module.
     */
    public void setSound(Sound2D sounds) {
        this.sounds = sounds;
        assert sounds != null;
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
        assert maxLevel >= 1;
    }

    /**
     * Exits the program.
     */
    public void shutdown() {
        // Exit with error-free status.
        System.exit(0);
    }
    
    /**********************/
    /* Debugging methods. */
    /**********************/
    
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