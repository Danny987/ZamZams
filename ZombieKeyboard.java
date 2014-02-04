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
 * @grokeys.up Danny Gomez
 * @grokeys.up James Green
 * @grokeys.up Marcos Lemus
 * @grokeys.up Mario LoPrinzi
 */
public class ZombieKeyboard extends KeyAdapter {
    private static final boolean PRESSED = true;
    private static final boolean RELEASED = false;
    
    private ZombieKeyStates keys;
    
    /**
     * ZombieKeyboard default constructor.
     * 
     * @param keys ZombieKeyStates object whose key variables should be updated.
     */
    public ZombieKeyboard(ZombieKeyStates keys) {
        this.keys = keys;
    }
    
    /**
     * keyPressed - triggered when a key is pressed.
     * 
     * @param e KeyEvent sent when key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
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
            // keys.up arrow/W - move keys.up.
            case KeyEvent.VK_UP: case KeyEvent.VK_W:
                setState(keys.up, state);
                if (state) {
                    keys.up = RELEASED;
                    assert (keys.up == PRESSED && keys.down ==
                            RELEASED) : "keys.up/keys.down error";
                }
                break;
            // keys.down arrow/S - move keys.down.
            case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
                setState(keys.down, state);
                if (state) {
                    keys.up = RELEASED;
                    assert (keys.down == PRESSED && keys.up ==
                            RELEASED) : "keys.down/keys.up error";
                }
                break;
            // keys.left arrow/A - move keys.left.
            case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
                setState(keys.left, state);
                if (state) {
                    keys.right = RELEASED;
                    assert (keys.left == PRESSED && keys.right ==
                            RELEASED) : "keys.left/keys.right error";
                }
                break;
            // keys.right arrow/D - move keys.right.
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
                setState(keys.right, state);
                if (state) {
                    keys.left = RELEASED;
                    assert (keys.right == PRESSED && keys.left ==
                            RELEASED) : "keys.right/keys.left error";
                }
                break;
            // Shift/R - run.
            case KeyEvent.VK_R: case KeyEvent.VK_SHIFT:
                setState(keys.run, state);
                assert (keys.run == state) : "run error";
                break;
            // P/Space - action/firetraps.
            case KeyEvent.VK_P: case KeyEvent.VK_SPACE:
                setState(keys.action, state);
                assert (keys.action == state) : "action error";
                break;
            // Escape - pause/cancel/exit.
            case KeyEvent.VK_ESCAPE:
                setState(keys.esc, state);
                assert (keys.esc == state) : "pause error";
                break;
            // Enter - accept (menu).
            case KeyEvent.VK_ENTER:
                setState(keys.accept, state);
                assert (keys.accept == state) : "accept error";
                break;
        }
    }
    
    /**
     * A simple helper method for keyHandler that sets key states properly.
     * 
     * @param key Which boolean variable to modify.
     * @param state Boolean of whether this is a key pressed or released event.
     */
    private void setState(boolean key, boolean state) {
        key = (state ? PRESSED : RELEASED);
    }
    
}
