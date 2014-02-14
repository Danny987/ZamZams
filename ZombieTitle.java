import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

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
 * ZombieTitle - ZombieHouse's title screen.
 * 
 * @author Ramon A. Lovato
 * @group Danny Gomez
 * @group James Green
 * @group Marcos Lemus
 * @group Mario LoPrinzi
 */
public class ZombieTitle extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private Image titleBackground, startButtonPlain, startButtonSelected;
    private Image exitButtonPlain, exitButtonSelected;
    
    private String selected;
    
    private long startTime;
    private long elapsedTime;
    
    /**
     * Default constructor.
     */
    public ZombieTitle() {
        super(new BorderLayout());
        startTime = elapsedTime = System.currentTimeMillis();
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
     * Converts the time since infection display counters to a string.
     * 
     * @return time String representation of the current timers.
     */
    private String getTime() {
        StringBuilder time = new StringBuilder("");
        elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSecs = elapsedTime / 1000;
        long elapsedMins = elapsedSecs / 60;
        long hours = elapsedMins / 60;
        long mins = elapsedMins % 60;
        long secs = elapsedSecs % 60;
        
        // Hours.
        if (hours < 10) {
            time.append("0");
        }
        time.append(hours);
        // Flash the separators.
        if (secs % 2 == 0) {
            time.append(":");
        } else {
            time.append(" ");
        }
        // Minutes.
        if (mins < 10) {
            time.append("0");
        }
        time.append(mins);
        // Flash the separators.
        if (secs % 2 == 0) {
            time.append(":");
        } else {
            time.append(" ");
        }
        // Seconds.
        if (secs < 10) {
            time.append("0");
        }
        time.append(secs);
        
        return time.toString();
    }
    
    /**
     * Toggles which button is selected.
     */
    public void switchButton() {
        selected = (selected == "start" ? "exit" : "start");
        repaint();
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
        super.paintComponent(g);
        g.drawImage(titleBackground, 0, 0, null);
        
        
        if (selected == "start") {
            g.drawImage(startButtonSelected, 360, 450, null);
            g.drawImage(exitButtonPlain, 920, 450, null);
        } else {
            g.drawImage(startButtonPlain, 360, 450, null);
            g.drawImage(exitButtonSelected, 920, 450, null);
        }
        
        g.setColor(Color.GREEN);
        g.drawString("time since infection: " + getTime(), 380, 550);
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
