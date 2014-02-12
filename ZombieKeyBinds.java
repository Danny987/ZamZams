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

import java.awt.Container;
import java.awt.event.*;

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
public class ZombieKeyBinds {
    private ZombieMainGame game;
    /**
     * ZombieKeyBinds' constructor.
     * 
     * @param pane JComponent whose InputMap and ActionMap should be updated.
     */
    public ZombieKeyBinds(JComponent pane) {
        InputMap iMap = pane.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap aMap = pane.getActionMap();
        KeyStroke key;
        
        // Up/W.
        key = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false);
        iMap.put(key, "Up");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);
        iMap.put(key,  "released Up");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false);
        iMap.put(key, "Up");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true);
        iMap.put(key, "released Up");
        aMap.put("Up", new KeyAction("Up"));
        aMap.put("released Up", new KeyAction("released Up"));
        
        // Down/S.
        key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false);
        iMap.put(key, "Down");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);
        iMap.put(key,  "released Down");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false);
        iMap.put(key, "Down");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true);
        iMap.put(key,  "released Down");
        aMap.put("Down", new KeyAction("Down"));
        aMap.put("released Down", new KeyAction("released Down"));
        
        // Left/A.
        key = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false);
        iMap.put(key, "Left");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);
        iMap.put(key, "released Left");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false);
        iMap.put(key, "Left");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true);
        iMap.put(key, "released Left");
        aMap.put("Left", new KeyAction("Left"));
        aMap.put("released Left", new KeyAction("released Left"));
        
        // Right/D.
        key = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false);
        iMap.put(key, "Right");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
        iMap.put(key, "released Right");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false);
        iMap.put(key, "Right");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true);
        iMap.put(key,  "released Right");
        aMap.put("Right", new KeyAction("Right"));
        aMap.put("released Right", new KeyAction("released Right"));
        
        // R.
        key = KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, false);
        iMap.put(key, "Run");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, true);
        iMap.put(key,  "released Run");
        aMap.put("Run", new KeyAction("Run"));
        aMap.put("released Run", new KeyAction("released Run"));
        
        // P/Space.
        key = KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false);
        iMap.put(key, "Action");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true);
        iMap.put(key, "released Action");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false);
        iMap.put(key, "Action");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true);
        iMap.put(key, "released Action");
        aMap.put("Action", new KeyAction("Action"));
        aMap.put("released Action", new KeyAction("released Action"));
        
        // Escape.
        key = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        iMap.put(key, "Escape");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
        iMap.put(key, "released Escape");
        aMap.put("Escape", new KeyAction("Escape"));
        aMap.put("released Escape", new KeyAction("released Escape"));
        
        // Enter.
        key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        iMap.put(key, "Enter");
        key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
        iMap.put(key, "released Enter");
        aMap.put("Enter", new KeyAction("Enter"));
        aMap.put("released Enter", new KeyAction("released Enter"));
    }
    
    public class KeyAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private String action;

        public KeyAction(String action) {
            this.action = action;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Test to make sure the main game has been linked.
            if (game != null) {
                game.updateInput(action.toLowerCase());
            }
        }
    }
    
    /**
     * Links the ZombieKeyBinds object with an instance of the main game
     * controller.
     * 
     * @param game ZombieMainGame with which to link.
     */
    public void linkToMainGame(ZombieMainGame game) {
        this.game = game;
    }

    /**
     * ZombieKeyBinds main method - used for testing.
     * 
     * @param args String array of command-line arguments.
     */
    public static void main(String[] args) {
        // Testing code.
        JFrame frame = new JFrame();
        frame.setPreferredSize(new java.awt.Dimension(640, 480));
        frame.setBackground(java.awt.Color.BLACK);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new java.awt.Dimension(640, 480));
        panel.setOpaque(true);
        frame.setContentPane(panel);
        ZombieKeyBinds binds = new ZombieKeyBinds(panel);
        frame.pack();
        frame.setVisible(true);
    }

}
