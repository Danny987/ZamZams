import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.JFrame;

public class GraphicsTestFrame extends JFrame implements MouseMotionListener{
  private GameGraphics game;

  private LightSource light;

  public GraphicsTestFrame() {
    setSize(500, 500);
    game = new GameGraphics(500, 500);

    int[][] fakeMap = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } ,
                        { 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } ,
                        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } ,
                        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } ,
                        { 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } ,
                        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } ,
                        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } ,
                        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }};

    
    game.initMap(fakeMap, GameGraphics.GameImage.FLOOR1);
    light = new LightSource(Color.GREEN);
    game.drawImage(null, null, null, new Point(250, 250), light);
    this.add(game);
    this.addMouseMotionListener(this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);


  }


  @Override
  public void mouseDragged(MouseEvent arg0){}
  
  @Override
  public void mouseMoved(MouseEvent arg0){
    int x = arg0.getX();
    int y = arg0.getY();

    game.drawImage(null, null, null, new Point(x, y), light);
  }
  
  public static void main(String arg[]) {
    new GraphicsTestFrame();
  }
}
