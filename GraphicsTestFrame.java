import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.JFrame;

public class GraphicsTestFrame extends JFrame implements MouseMotionListener, KeyListener{
  private GameGraphics game;

  private LightSource light;

  public GraphicsTestFrame() {
    setSize(1300, 700);
    game = new GameGraphics(1000, 700);

    int[][] fakeMap = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
                        { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } ,
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    
    game.initMap(fakeMap, GameGraphics.GameImage.FLOOR1);
    light = new LightSource(new Color(0, 0, 155, 150));
    game.drawImage(null, null, null, new Point(250, 250), light);
    this.add(game);
    this.addMouseMotionListener(this);
    this.addKeyListener(this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);


  }


  @Override
  public void mouseDragged(MouseEvent arg0){}
  
  @Override
  public void mouseMoved(MouseEvent arg0){
    int x = arg0.getX();
    int y = arg0.getY();

    GameGraphics.GameImage.PLAYER.x = x;
    GameGraphics.GameImage.PLAYER.y = y;
    
    game.drawImage(null, null, null, new Point(x, y), light);
  }
  
  public static void main(String arg[]) {
    new GraphicsTestFrame();
  }


  @Override
  public void keyPressed(KeyEvent arg0) {
    // TODO Auto-generated method stub
    
  }


  @Override
  public void keyReleased(KeyEvent arg0) {
    // TODO Auto-generated method stub
    
  }


  @Override
  public void keyTyped(KeyEvent arg0) {
    System.out.println(arg0.getKeyChar());
    
  }
}
