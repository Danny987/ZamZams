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
	 * ZombieHouse's main method.
	 * 
	 * @param args String array of command-line arguments.
	 */
	public static void main(String[] args) {
        ZombieHouse zombieHouse = new ZombieHouse();
        zombieHouse.startGUI();
        zombieHouse.startMainGame();
	}
	
}
