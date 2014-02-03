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
 * ZombieTitle - ZombieHouse's title screen.
 * 
 * @author Ramon A. Lovato
 * @group Danny Gomez
 * @group James Green
 * @group Marcos Lemus
 * @group Mario LoPrinzi
 */
public class ZombieTitle extends JPanel {
    /**
     * Eclipse made me.
     */
    private static final long serialVersionUID = 1L;
    
    private Image titleBackground, startButtonPlain, startButtonSelected;
    private Image exitButtonPlain, exitButtonSelected;
    
    private String selected;
    
    /**
     * Default constructor.
     */
    public ZombieTitle() {
        super(new BorderLayout());
        setBackground(Color.BLACK);
        // Get the images.
        try {
            titleBackground = new ImageIcon(this.getClass().getResource(
                    "/images/title-screen.png")).getImage();
            startButtonPlain = new ImageIcon(this.getClass().getResource(
                    "/images/start-button-plain.png")).getImage();
            startButtonSelected = new ImageIcon(this.getClass().getResource(
                    "/images/start-button-selected.png")).getImage();
            exitButtonPlain = new ImageIcon(this.getClass().getResource(
                    "/images/exit-button-plain.png")).getImage();
            exitButtonSelected = new ImageIcon(this.getClass().getResource(
                    "/images/exit-button-selected.png")).getImage();
        } catch (NullPointerException ex) {
            System.err.println("Error: title screen resources not found.");
        }
        selected = "start";
    }
    
    /**
     * Toggles which button is selected.
     */
    public void switchButton() {
        selected = (selected == "start" ? "exit" : "start");
    }
    
    /**
     * Getter for selected.
     * 
     * @return selected String representation of selected button.
     */
    public String getSelected() {
        return selected;
    }
    
    /**
     * Override of paintComponent for actually drawing the image.
     * 
     * @param g Graphics device on which to do the drawing; provided by system.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graph = (Graphics2D) g;
        graph.drawImage(titleBackground, 0, 0, null);
        if (selected == "start") {
            graph.drawImage(startButtonSelected, 360, 450, null);
            graph.drawImage(exitButtonPlain, 920, 450, null);
        } else {
            graph.drawImage(startButtonPlain, 360, 450, null);
            graph.drawImage(exitButtonSelected, 920, 450, null);
        }
    }

    /**
     * ZombieTitle's main method - used for testing.
     * 
     * @param args String array of command-line arguments.
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
