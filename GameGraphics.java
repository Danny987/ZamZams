/***********************************************************
 * Marcos Lemus
 * The MonsterGraphics class will load images, texture all objects, handle animations and lighting.
 *
 **************************************/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameGraphics extends JPanel {
  private static final long serialVersionUID = -6358629070074836633L;

  private BufferedImage background;
  private BufferedImage characters;
  private BufferedImage lights;

  private Graphics2D backgroundGraphics;
  private Graphics2D charactersGraphics;
  private Graphics2D lightsGraphics;
  
  private LightSource redLight;

  private BufferedImage sprites;

  public enum GameImage {
    PLAYER, ZOMBIE, FIRE, FLOOR1, FLOOR2, FLOOR3, WALL, SMALLOBJECT, LARGEOBJECT;
  }

  public enum Animation {
    WALKING, STANDING
  }

  public enum Direction {
    UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;
  }

  public GameGraphics(int width, int height) {
    redLight = new LightSource(Color.RED);
    setSize(width, height);
  }

  public boolean initMap(int[][] mapLayout, GameImage floor) {
    try {
      loadImages();
    } catch (MissingResourceException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return changeMap(mapLayout, floor);
  }

  public boolean changeMap(int[][] mapLayout, GameImage floor) {
    int width = mapLayout[0].length * 50;
    int height = mapLayout.length * 50;

    char wall = 0, floorTile = 1;

    if (width < 1 || height < 1)
      return false;
    else {
      background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      characters = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      lights = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

      backgroundGraphics = background.createGraphics();
      charactersGraphics = characters.createGraphics();
      lightsGraphics = lights.createGraphics();
    }

    BufferedImage foo = null;
    int mapWidth = mapLayout[0].length;
    int mapHeight = mapLayout.length;

    for (int x = 0; x < mapWidth; x++) {
      for (int y = 0; y < mapHeight; y++) {
        if(mapLayout[y][x] != floorTile){
          redLight.addShapes(new Rectangle2D.Float(50*x, 50*y, 50, 50));
        }
        
        
        if (mapLayout[y][x] == wall) {
          
          ///////////////////////////////////////////////////////////
          // Wall Corners
          ///////////////////////////////////////////////////////////
          
          if (x == 0 && y == 0) {
            foo = sprites.getSubimage(0, 0, 50, 50);
          }
          else if (x == mapLayout[0].length - 1 && y == 0) {
            foo = sprites.getSubimage(100, 0, 50, 50);
          }
          else if (x == mapLayout[0].length - 1 && y == mapLayout.length - 1) {
            foo = sprites.getSubimage(100, 100, 50, 50);
          }
          else if (x == 0 && y == mapLayout.length - 1) {
            foo = sprites.getSubimage(0, 100, 50, 50);
          }
          
          ///////////////////////////////////////////////////////////
          // Wall edges
          ///////////////////////////////////////////////////////////
          
          else if (x == 0) {
            foo = sprites.getSubimage(0, 50, 50, 50);
          }
          else if (y == 0) {
            foo = sprites.getSubimage(50, 0, 50, 50);
          }
          else if (mapLayout[y][x] == wall && x == mapLayout[0].length - 1) {
            foo = sprites.getSubimage(100, 50, 50, 50);
          }
          else if (mapLayout[y][x] == wall && y == mapLayout.length - 1) {
            foo = sprites.getSubimage(50, 100, 50, 50);
          }
          
          ///////////////////////////////////////////////////////////
          // Random Walls
          ///////////////////////////////////////////////////////////
          
          else{
            foo = sprites.getSubimage(50, 100, 50, 50);
          }
        }
        else {
          foo = sprites.getSubimage(50, 50, 50, 50);
        }
        backgroundGraphics.drawImage(foo, 50 * x, 50 * y, null);
      }
    }

    return true;
  }

  public boolean drawImage(GameImage image, Animation animation, Direction direction, Point point, LightSource light) {
    // charactersGraphics.drawImage(null, point.x, point.y, null);
    // charactersGraphics.fillRect(point.x, point.y, 10, 10);
    light.update(point.x, point.y, lightsGraphics);
    repaint();
    return false;
  }

  private boolean loadImages() throws MissingResourceException, IOException {
    sprites = ImageIO.read(new File("sprites.png"));
    // wall = ImageIO.read(new File("Walls.png"));
    // corner = ImageIO.read(new File("Corner.png"));
    return false;
  }

  @Override
  public void paint(Graphics g) {
    g.drawImage(background, 0, 0, null);
    g.drawImage(characters, 0, 0, null);
    g.drawImage(lights, 0, 0, null);
  }

  public static void main(String arg[]) {
    int[][] fakeMap = new int[50][50];

    GameGraphics gameGraphics = new GameGraphics(500, 500);
    try {
      assert gameGraphics.loadImages();
    } catch (MissingResourceException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assert gameGraphics.initMap(fakeMap, GameImage.FLOOR1);

    assert gameGraphics.changeMap(fakeMap, GameImage.FLOOR2);
  }
}
