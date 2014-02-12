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
 * NoPlayerStartException - thrown when ZombieLevel doesn't find a player start
 * location on the map.
 * 
 * @author Ramon A. Lovato
 */
public class NoPlayerStartException extends Exception {
    /**
     * Eclipse made me.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * NoPlayerStartException constructor.
     */
    public NoPlayerStartException() {
        super("Error: No player start location was found in the level.");
    }
}