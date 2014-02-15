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
public class ZombieFrame extends JFrame
{
  /**
   * Because Eclipse made me!
   */
  private static final long serialVersionUID = 1L;

  private Dimension size;
  private Container pane;
  private ZombieKeyBinds keys;
  private GameGraphics graphics;

  /**
   * ZombieFrame's constructor.
   * 
   * @param contents
   *          Optional JPanel(s) to add to content pane. Arguments > 0 are
   *          ignored.
   */
  public ZombieFrame(JPanel... contents)
  {
    super("ZombieHouse");

    // Request keyboard focus for the frame.
    setFocusable(true);
    requestFocusInWindow();
    requestFocus();

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
    if (contents.length > 0)
    {
      pane.add(contents[0]);
    }

    keys = new ZombieKeyBinds((JComponent) pane);

    // Get the graphics device information.
    GraphicsEnvironment environment = GraphicsEnvironment
        .getLocalGraphicsEnvironment();
    GraphicsDevice graphics = environment.getDefaultScreenDevice();

    pack();

    // Go full screen, if supported.
    if (graphics.isFullScreenSupported())
    {
      try
      {
        graphics.setFullScreenWindow(this);
        // Having gone full screen, retrieve the display size.
        size = Toolkit.getDefaultToolkit().getScreenSize();
        // This double-switching of setVisible is to fix a bug with
        // full-screen-exclusive mode on OS X. Versions 10.8 and later
        // don't send keyboard events properly without it.
        setVisible(false);
      }
      catch (HeadlessException ex)
      {
        System.err.println("Error: primary display not set or found. "
            + "Your experience of life may be suboptimal.");
        ex.printStackTrace();
      }
    }
    else
    {
      // If full-screen-exclusive mode isn't supported, switch to
      // maximized window mode.
      System.err.println("Full-screen-exclusive mode not supported.");
      setExtendedState(Frame.MAXIMIZED_BOTH);
      size = getSize();
    }
    setVisible(true);
  }

  /**
   * Start the graphics module.
   * 
   * @param house
   *          House object to draw first.
   * @param tileset
   *          Which tileset to use to draw the house.
   */
  public void startGraphics(House house, int tileset)
  {
    graphics = new GameGraphics(getWidth(), getHeight());
    graphics.initHouse(house, tileset);
    graphics.setOpaque(true);
    for (int i = 0; i < pane.getComponentCount(); i++)
    {
      pane.remove(i);
    }
    pane.add(graphics);
  }

  /**
   * Getter for graphics module.
   * 
   * @return GameGraphics graphics module.
   */
  public GameGraphics getGameGraphics()
  {
    return graphics;
  }

  /**
   * Getter for keys.
   * 
   * @return keys ZombieKeyBinds object containing the key binds.
   */
  public ZombieKeyBinds getKeyBinds()
  {
    return keys;
  }
}
