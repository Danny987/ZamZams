/***********************************************************
 * Marcos Lemus
 * The MonsterGraphics class will load images, texture all objects, handle animations and lighting.
 *
 *To implement make new GameGraphics with arguments for width and height
 *then call initHouse, takes House object and some int, 0 for now.
 **************************************/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameGraphics extends JPanel {
  private static final long serialVersionUID = -6358629070074836633L;

  private BufferedImage background;
  private BufferedImage characters;
  private BufferedImage lights;
  private BufferedImage HUD;

  private Graphics2D backgroundGraphics;
  private Graphics2D charactersGraphics;
  private Graphics2D lightsGraphics;
  private Graphics2D HUDGraphics;

  public List<Shape> shapes = new ArrayList<>();
  public List<LightSource> lightSources = new ArrayList<>();

  private House house;

  private BufferedImage sprites;
  private BufferedImage largeSprite;
  private BufferedImage mediumSprite;
  private BufferedImage smallSprite;
  private BufferedImage playerSprite;
  private BufferedImage zombieSprite;

  public enum Animation {
    WALKING, STANDING
  }

  public enum Direction {
    UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;
  }

  public GameGraphics(int width, int height) {
    LightSource player = new LightSource(Color.RED);
    LightSource fire = new LightSource(Color.ORANGE);
    lightSources.add(player);
    lightSources.add(fire);

    setSize(width, height);
  }

  public boolean initHouse(House house, int floor) {
    try {
      loadImages();
    } catch (MissingResourceException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return changeHouse(house, floor);
  }

  public boolean changeHouse(House house, int floor) {
    this.house = house;
    int width = house.getHouseWidth() * 50;
    int height = house.getHouseLength() * 50;

    if (width < 1 || height < 1)
      return false;
    else {
      background = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
      characters = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      lights = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      HUD = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

      backgroundGraphics = background.createGraphics();
      charactersGraphics = characters.createGraphics();
      lightsGraphics = lights.createGraphics();
      HUDGraphics = HUD.createGraphics();

      charactersGraphics.setBackground(new Color(0,0,0,0));
      HUDGraphics.setBackground(new Color(0, 0, 0, 0));
      lightsGraphics.setBackground(new Color(0,0,0,0));
    }

    BufferedImage sprite = null;
    int mapWidth = house.getHouseWidth();
    int mapHeight = house.getHouseLength();

    for (int x = 0; x < mapWidth; x++) {
      for (int y = 0; y < mapHeight; y++) {

        // /////////////////////////////////////////////////////////
        // Wall Corners
        // /////////////////////////////////////////////////////////

        if (x == 0 && y == 0) {
          sprite = sprites.getSubimage(0, 0, 50, 50);
        }
        else if (x == mapWidth - 1 && y == 0) {
          sprite = sprites.getSubimage(100, 0, 50, 50);
        }
        else if (x == mapWidth - 1 && y == mapHeight - 1) {
          sprite = sprites.getSubimage(100, 100, 50, 50);
        }
        else if (x == 0 && y == mapHeight - 1) {
          sprite = sprites.getSubimage(0, 100, 50, 50);
        }

        // /////////////////////////////////////////////////////////
        // Wall edges
        // /////////////////////////////////////////////////////////

        else if (x == 0) {
          sprite = sprites.getSubimage(0, 50, 50, 50);
        }
        else if (y == 0) {
          sprite = sprites.getSubimage(50, 0, 50, 50);
        }
        else if (x == mapWidth - 1) {
          sprite = sprites.getSubimage(100, 50, 50, 50);
        }
        else if (y == mapHeight - 1) {
          sprite = sprites.getSubimage(50, 100, 50, 50);
        }

        // /////////////////////////////////////////////////////////
        // Random Walls
        // /////////////////////////////////////////////////////////

        else {
          sprite = sprites.getSubimage(50, 50, 50, 50);
        }
        backgroundGraphics.drawImage(sprite, 50 * x, 50 * y, null);
      }
    }
    update();
    return true;
  }

  public void explode(ZombieTrap trap) {
    // get which fire trap is going to explode and check around it.
  }

  private boolean loadImages() throws MissingResourceException, IOException {
    sprites = ImageIO.read(new File("sprites.png"));
//    largeSprite = sprites.getSubimage(0, 0, 1800, 300);
//    mediumSprite = sprites.getSubimage(0, 300, 150, 150);
//    smallSprite = null;
    // wall = ImageIO.read(new File("Walls.png"));
    // corner = ImageIO.read(new File("Corner.png"));
    return false;
  }

  public void update() {
    List<Zombie> zombies = house.zombieList;
    Rectangle2D box;
    int x, y, width, height;
    
    charactersGraphics.clearRect(0, 0, characters.getWidth(), characters.getHeight());
    
    backgroundGraphics.setColor(Color.BLUE);
    for (Tile t : house.tileList) {
      x = (int) t.getX() * 50;
      y = (int) t.getY() * 50;
      width = (int) t.getWidth() * 50;
      height = (int) t.getHeight() * 50;
      backgroundGraphics.fillRect(x, y, width, height);
    }

    charactersGraphics.setColor(Color.RED);
    for (Zombie z : zombies) {
      box = z.getHitbox();
      x = (int) box.getX();
      y = (int) box.getY();
      charactersGraphics.fillRect(x, y, 50, 50);
    }

    Point p = house.player.getPosition();
    x = p.x;
    y = p.y;

    HUDGraphics.clearRect(0, 0, this.getWidth(), this.getHeight());
    HUDGraphics.setColor(Color.ORANGE);
    HUDGraphics.fillRect(10, this.getHeight() - 40, 20, 20);
    HUDGraphics.drawString(Integer.toString(house.player.getFireTrapCount()), 40, this.getHeight() - 40);
    
    charactersGraphics.setColor(Color.GREEN);
    charactersGraphics.fillRect(x, y, 50, 50);
    repaint();
  }

  @Override
  public void paint(Graphics g) {
    int backgroundX, backgroundY;
    backgroundX = backgroundY = 0;
    
    Point p;
    p = house.player.getPosition();
    
    backgroundX = this.getWidth()/2 - p.x;
    backgroundY = this.getHeight()/2 - p.y;

    if(backgroundX > 0) backgroundX = 0;
    else if(backgroundX + background.getWidth() < this.getWidth())
      backgroundX -= backgroundX + background.getWidth() - this.getWidth();
    
    if(backgroundY > 0) backgroundY = 0;
    else if(backgroundY + background.getHeight() < this.getHeight())
      backgroundY -= backgroundY + background.getHeight() - this.getHeight();
    
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.drawImage(background, backgroundX, backgroundY, null);
    g.drawImage(characters, backgroundX, backgroundY, null);
    // g.drawImage(lights, backgroundX, backgroundY, null);
    g.drawImage(HUD, 0, 0, null);
  }
}
