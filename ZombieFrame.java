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
 * @group Danny Gomez
 * @group James Green
 * @group Marcos Lemus
 * @group Mario LoPrinzi
 */
public class ZombieFrame extends JFrame {
	/**
	 * Because Eclipse made me!
	 */
	private static final long serialVersionUID = 1L;
	
	private Dimension size;
	private Container pane;
	private ZombieKeyboard keyboard = new ZombieKeyboard();
	
	/**
	 * ZombieFrame's constructor.
	 * 
	 * @param contents Optional JPanel(s) to add to content pane. Arguments > 0
	 *        are ignored.
	 */
	public ZombieFrame(JPanel... contents) {
		super("ZombieHouse");
		
		// Request keyboard focus for the frame.
		setFocusable(true);
		requestFocusInWindow();
		requestFocus();
		
		addKeyListener(keyboard);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setBackground(Color.BLACK);
        
        // The content pane. An optional JPanel may be passed into the
        // constructor. It creates an empty pane with a black background if
        // one isn't provided.
        pane = getContentPane();
        pane.setBackground(Color.BLACK);
        pane.setFocusable(false);
        pane.setVisible(true);
        if (contents.length > 0) {
            pane.add(contents[0]);
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
                ex.printStackTrace();;
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
	
	/**
	 * Getter for keyboard.
	 * 
	 * @return keyboard ZombieKeyboard object containing the keyboard listener.
	 */
	public ZombieKeyboard getKeyboard() {
	    return keyboard;
	}
	
}
