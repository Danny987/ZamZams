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
 * Thrown if a level has more than one player start location.
 * 
 * @author Ramon A. Lovato
 */
public class MultiplePlayerStartsException extends Exception {
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public MultiplePlayerStartsException() {
        super("Error: house contains too many player start locations.");
    }
}
