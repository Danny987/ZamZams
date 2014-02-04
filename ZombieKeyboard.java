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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * ZombieKeyboard - a keyboard listener for ZombieHouse.
 * 
 * @author Ramon A. Lovato
 * @group Danny Gomez
 * @group James Green
 * @group Marcos Lemus
 * @group Mario LoPrinzi
 */
public class ZombieKeyboard extends KeyAdapter {
    private static final boolean PRESSED = true;
    private static final boolean RELEASED = false;

    // Key states variables use wrappers so they can be passed by reference.
    private Boolean up;
    private Boolean down;
    private Boolean left;
    private Boolean right;
    private Boolean run;
    private Boolean action;
    private Boolean esc;
    private Boolean accept;
    
    /**
     * ZombieKeyboard's default constructor.
     */
    public ZombieKeyboard() {
        up = down = left = right = run = action = esc = accept = false;
    }
    
    /**
     * keyPressed - triggered when a key is pressed.
     * 
     * @param e KeyEvent sent when key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("pressed");
        keyHandler(e, PRESSED);
    }
    
    /**
     * keyReleased - triggered when a key is released.
     * 
     * @param e KeyEvent sent when key is released.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keyHandler(e, RELEASED);
    }
    
    /**
     * Keyboard event handler.
     */
    private void keyHandler(KeyEvent e, boolean state) {
        int keyCode = e.getKeyCode();
        
        // The switch updates the various key states according to whether their
        // button(s) were pressed/released. If a direction was pressed, sets the
        // opposite direction to RELEASED. Verified with asserts.
        switch(keyCode) {
            // up arrow/W - move up.
            case KeyEvent.VK_UP: case KeyEvent.VK_W:
                setState(up, state);
                if (state) {
                    up = RELEASED;
                    assert (up == PRESSED && down ==
                            RELEASED) : "up/down error";
                }
                break;
            // down arrow/S - move down.
            case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
                setState(down, state);
                if (state) {
                    up = RELEASED;
                    assert (down == PRESSED && up ==
                            RELEASED) : "down/up error";
                }
                break;
            // left arrow/A - move left.
            case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
                setState(left, state);
                if (state) {
                    right = RELEASED;
                    assert (left == PRESSED && right ==
                            RELEASED) : "left/right error";
                }
                break;
            // right arrow/D - move right.
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
                setState(right, state);
                if (state) {
                    left = RELEASED;
                    assert (right == PRESSED && left ==
                            RELEASED) : "right/left error";
                }
                break;
            // Shift/R - run.
            case KeyEvent.VK_R: case KeyEvent.VK_SHIFT:
                setState(run, state);
                assert (run == state) : "run error";
                break;
            // P/Space - action/fire traps.
            case KeyEvent.VK_P: case KeyEvent.VK_SPACE:
                setState(action, state);
                assert (action == state) : "action error";
                break;
            // Escape - pause/cancel/exit.
            case KeyEvent.VK_ESCAPE:
                setState(esc, state);
                assert (esc == state) : "pause error";
                break;
            // Enter - accept (menu).
            case KeyEvent.VK_ENTER:
                setState(accept, state);
                assert (accept == state) : "accept error";
                break;
        }
    }
    
    /**
     * A simple helper method for keyHandler that sets key states properly.
     * 
     * @param key Which boolean variable to modify.
     * @param state Boolean of whether this is a key pressed or released event.
     */
    private void setState(Boolean key, boolean state) {
        key = (state ? PRESSED : RELEASED);
    }
    
    /**
     * Links the main game controller's key state variables.
     */
    public void linkToMain(Boolean up, Boolean down, Boolean left,
                Boolean right, Boolean run, Boolean action, Boolean esc,
                Boolean accept) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.run = run;
        this.action = action;
        this.esc = esc;
        this.accept = accept;
    }
    
}
