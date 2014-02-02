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

import java.awt.*;
import javax.swing.*;

/**
 * ZombieFrame - ZombieHouse's GUI class.
 * 
 * @author Ramon A. Lovato
 */
public class ZombieFrame extends JFrame {
	/**
	 * Because Eclipse made me!
	 */
	private static final long serialVersionUID = 1L;
	
	private Dimension size;
	
	/**
	 * ZombieFrame's constructor.
	 * 
	 * @param contents Optional JPanel(s) to use as content pane. Arguments > 0
	 *        are ignored.
	 */
	public ZombieFrame(JPanel... contents) {
		super("ZombieHouse");
		
		// Request keyboard focus for the frame.
		setFocusable(true);
		requestFocusInWindow();
		requestFocus();
		
		addKeyListener(new ZombieKeyboard());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        
        // The content pane. Since the content pane is an optional parameter of
        // the constructor, it creates an empty pane with a black background if
        // one isn't provided. This can be changed later with setContentPane.
        if (contents.length > 0) {
            setContentPane(contents[0]);
        } else {
            JPanel emptyPane = new JPanel();
            emptyPane.setBackground(Color.BLACK);
            emptyPane.setOpaque(true);
            setContentPane(emptyPane);
        }
        
        // Get the graphics device information.
        GraphicsEnvironment environment = 
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphics = environment.getDefaultScreenDevice();
        
        pack();
        
        // Go full screen, if supported.
        if (graphics.isFullScreenSupported()) {
            try {
                graphics.setFullScreenWindow(this);
                // Having gone full screen, retrieve the display size.
                size = Toolkit.getDefaultToolkit().getScreenSize();
            } catch (HeadlessException ex) {
                System.err.println("Why would you even play this headless?");
            }
        } else {
            // If full-screen-exclusive mode isn't supported, switch to
            // maximized window mode.
            System.err.println("Full-screen-exclusive mode not supported.");
            setExtendedState(Frame.MAXIMIZED_BOTH);
            setVisible(true);
            size = getSize();
        }
	}
	
	/**
	 * Getter for size.
	 * 
	 * @return Dimension object containing the full-screen window size.
	 */
	public Dimension getSize() {
	    return size;
	}
	
}
