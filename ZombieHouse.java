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
 * ZombieHouse's main class.
 * 
 * @author Ramon A. Lovato
 * @group Danny Gomez
 * @group James Green
 * @group Marcos Lemus
 * @group Mario LoPrinzi
 * @version 0.1
 */
public class ZombieHouse {
    // Provide permanent references to active instances of the various modules.
    private ZombieMainGame game;
    private ZombieFrame window;
    // A thread lock for the GUI processes.
    private final Object windowLock = new Object();
    
    /**
     * ZombieHouse's default constructor.
     */
    private ZombieHouse() {
        // OS X-specific tweaks, because I'm a Mac guy, so yeah. Some versions
        // completely ignore these.
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name",
                            "ZombieHouse");
        System.setProperty("apple.awt.fullscreenhidecursor","true");
    }
    
    /**
     * Start the GUI and graphics engine.
     */
    private void startGUI() {
        // Schedule GUI creation on the event-dispatch thread.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ZombieFrame newFrame = new ZombieFrame();
                // Store a reference for later.
                window = newFrame;
                // setFrame is locked from continuing until window has been
                // assigned, so we need to release it. This prevents issues
                // with concurrency.
                synchronized (windowLock) {
                    windowLock.notifyAll();
                }
            }
        });
    }
    
    /**
     * Start the main game loop controller.
     */
    private void startMainGame() {
        game = new ZombieMainGame();
    }
    
    /**
     * Pass the frame to the main game controller.
     */
    private void setFrame() {
        // Since the GUI instantiation is happening on a different thread, it
        // might not have happened yet when setFrame gets called, so we need to
        // synchronize them.
        synchronized (windowLock) {
            try {
                windowLock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        // Now that we're sure the GUI instantiation is finished, we can link
        // the frame to the main game controller.
        game.setFrame(window);
    }
    
    /**
     * Start the XML parser.
     */
    private void startParser() {
        Level level = new Level("config.xml");
        game.linkToLevel(level);
    }
    
    /**
     * Hands off primary control to ZombieMainGame. Called after initialization
     * routines complete.
     */
    private void relinquishControl() {
        game.takeControl();
    }
    
    /**
     * Start the graphics engine.
     */
    private void startGraphics() {
        synchronized (windowLock) {
            try {
                windowLock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        int width = window.getSize().width;
        int height = window.getSize().height;
        GameGraphics graphics = new GameGraphics(width, height);
    }

	/**
	 * ZombieHouse's main method.
	 * 
	 * @param args String array of command-line arguments.
	 */
	public static void main(String[] args) {
        ZombieHouse zombieHouse = new ZombieHouse();
        
        zombieHouse.startGUI();
        zombieHouse.startMainGame();
        zombieHouse.setFrame();
        zombieHouse.startParser();
        
        // Once the initialization routines are complete, ZombieHouse hands off
        // primary control to ZombieMainGame.
        zombieHouse.relinquishControl();
	}
	
}
