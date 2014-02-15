import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.JFrame;

public class GraphicsTestFrame extends JFrame implements MouseMotionListener, KeyListener{
  private GameGraphics game;

  private LightSource light;
  private Level level;

  public GraphicsTestFrame() {
    setSize(1000, 700);
    game = new GameGraphics(1000, 700);

    level = new Level("config.xml");

    House house = level.houseList.get(0);
    house.player.buildMap(house);
    game.initHouse(level.houseList.get(0), 0);
    light = new LightSource(new Color(0, 0, 155, 150));
//    game.drawImage(null, null, null, new Point(250, 250), light);
    this.add(game);
    this.addMouseMotionListener(this);
    this.addKeyListener(this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);

    house.drawHouse(true, true);

  }


  @Override
  public void mouseDragged(MouseEvent arg0){}
  
  @Override
  public void mouseMoved(MouseEvent arg0){
    int x = arg0.getX();
    int y = arg0.getY();

//    level.houseList.get(0).player.move(x, y, false);

//    GameGraphics.GameImage.PLAYER.x = x;
//    GameGraphics.GameImage.PLAYER.y = y;
    
  }
  
  public static void main(String arg[]) {
    new GraphicsTestFrame();
  }


  @Override
  public void keyPressed(KeyEvent arg0) {
    if(arg0.getKeyChar() == 'a')
      level.houseList.get(0).player.move(1, 0, false);
    if(arg0.getKeyChar() == 's')
      level.houseList.get(0).player.move(0, 2, false);
    if(arg0.getKeyChar() == 'd')
      level.houseList.get(0).player.move(2, 0, false);
    if(arg0.getKeyChar() == 'w')
      level.houseList.get(0).player.move(0, 1, false);
    game.update();
    
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
