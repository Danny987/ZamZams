/***********************************************************
 * Marcos Lemus
 * The MonsterGraphics class will load images, texture all objects, handle animations and lighting.
 *
 *To implement make new GameGraphics with arguments for width and height
 *then call initHouse, takes House object and some int, 0 for now.
 **************************************/

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameGraphics extends JPanel {
  private static final long serialVersionUID = -6358629070074836633L;

  private BufferedImage background;
  private BufferedImage characters;
  private BufferedImage lights;
  private BufferedImage HUD;
  private BufferedImage black;

  private Graphics2D backgroundGraphics;
  private Graphics2D charactersGraphics;
  private Graphics2D lightsGraphics;
  private Graphics2D HUDGraphics;

  public List<RoundRectangle2D.Float> shapes = new ArrayList<>();
  public List<LightSource> lightSources = new ArrayList<>();

  private House house;

  private BufferedImage sprites;
  private Map<Rectangle2D, BufferedImage> images = new HashMap<>();
  
  private int frameCount = 0;
  private int frame = 0;
  private int zombieFrame = 0;
  private int playerFrame = 0;

  public GameGraphics(int width, int height) {
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

    background = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
    characters = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    lights = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    HUD = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    black = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    backgroundGraphics = background.createGraphics();
    charactersGraphics = characters.createGraphics();
    lightsGraphics = lights.createGraphics();
    HUDGraphics = HUD.createGraphics();

    charactersGraphics.setBackground(new Color(0, 0, 0, 0));
    HUDGraphics.setBackground(new Color(0, 0, 0, 0));
    lightsGraphics.setBackground(new Color(0, 0, 0, 0));
    
    Graphics g = black.createGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, black.getWidth(), black.getHeight());

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
          sprite = sprites.getSubimage(250, 0, 50, 50);
        }
        else if (x == mapWidth - 1 && y == mapHeight - 1) {
          sprite = sprites.getSubimage(250, 250, 50, 50);
        }
        else if (x == 0 && y == mapHeight - 1) {
          sprite = sprites.getSubimage(0, 250, 50, 50);
        }

        // /////////////////////////////////////////////////////////
        // Wall edges
        // /////////////////////////////////////////////////////////

        else if (x == 0) {
          sprite = sprites.getSubimage(0, 200, 50, 50);
        }
        else if (y == 0) {
          sprite = sprites.getSubimage(50, 0, 50, 50);
        }
        else if (x == mapWidth - 1) {
          sprite = sprites.getSubimage(250, 50, 50, 50);
        }
        else if (y == mapHeight - 1) {
          sprite = sprites.getSubimage(200, 250, 50, 50);
        }

        // /////////////////////////////////////////////////////////
        // Floors
        // /////////////////////////////////////////////////////////

        else {
          floor = 4;
          sprite = sprites.getSubimage(300 + floor*50, 250, 50, 50);
        }
        backgroundGraphics.drawImage(sprite, 50 * x, 50 * y, null);
      }
    }

    BufferedImage subimage = null;
    for(Tile t: house.tileList){
      if(t.getChar() != t.FIRETRAP && t.getChar() != t.BLOCK){
        if(t.getWidth() == t.getHeight()){
            sprite = sprites.getSubimage(1050, 0, 300*6, 300);
            subimage = new BufferedImage(t.getWidth()*50*6, t.getHeight()*50, BufferedImage.TYPE_INT_ARGB);
            subimage.getGraphics().drawImage(sprite, 0, 0, t.getWidth()*50*6, t.getHeight()*50, null);
        }
        else{
          sprite = sprites.getSubimage(600, 150, 150, 150);
          subimage = new BufferedImage(t.getWidth()*50, t.getHeight()*50, BufferedImage.TYPE_INT_ARGB);
          subimage.getGraphics().drawImage(sprite, 0, 0, t.getWidth()*50, t.getHeight()*50, null);
        }
      }
      images.put(t.getHitbox(), subimage);
      if(t.getChar() != t.FIRETRAP){
        shapes.add(new RoundRectangle2D.Float(t.getX(), t.getY(), t.getWidth(), t.getHeight(), t.getWidth(), t.getHeight()));
      }
    }
    
    LightSource player = new LightSource(new Color(255,0,0,0), lights.getWidth(), lights.getHeight());
    LightSource fire = new LightSource(Color.ORANGE, lights.getWidth(), lights.getHeight());
    lightSources.add(player);
    lightSources.add(fire);
    
    update();
    return true;
  }
  
  public void explode(Point p) {
    
  }

  private boolean loadImages() throws MissingResourceException, IOException {
    sprites = ImageIO.read(new File("bettersprites.png"));
    return true;
  }

  public void update() {
    List<Zombie> zombies = house.zombieList;
    Point point;
    BufferedImage sprite = null;
    int x, y, width, height;
    int direction = 0;

    charactersGraphics.clearRect(0, 0, characters.getWidth(), characters.getHeight());

    
    backgroundGraphics.setColor(Color.BLUE);
    for (Tile t : house.tileList) {
      x = (int) t.getX() * 50;
      y = (int) t.getY() * 50;
      width = (int)Math.round(t.getWidth() * 50);
      height = (int)Math.round(t.getHeight() * 50);

      if(t.getChar() == t.H_WALL){
        sprite = sprites.getSubimage(50, 0, 50, 50);
        for(int i = 0; i < width/50; i++){
          backgroundGraphics.drawImage(sprite, x, y, null);
          x += 50;
        }
      }
      else if(t.getChar() == t.V_WALL){
        sprite = sprites.getSubimage(250, 50, 50, 50);
        for(int i = 0; i < height/50; i++){
          backgroundGraphics.drawImage(sprite, x, y, null);
          y += 50;
        }
      }
      else if(t.getChar() == t.FIRETRAP){
        sprite = sprites.getSubimage(50, 200, 50, 50);
        backgroundGraphics.drawImage(sprite, x, y, null);
      }
      else{
        sprite = images.get(t.getHitbox());
        if(sprite.getWidth() > width){
          int a = frameCount % 5 == 0? ++frame%6: frame%6;
//          charactersGraphics.drawImage(sprite, x, y, null);
          backgroundGraphics.drawImage(sprite.getSubimage(width * a, 0, width, height), x, y, null);
        }
        else{
          backgroundGraphics.drawImage(sprite, x, y, null);
        }
      }
    }

    charactersGraphics.setColor(Color.RED);
    for (Zombie z : zombies) {
      point = z.getPosition();
      x = (int) point.getX();
      y = (int) point.getY();
      direction = z.getDirection();
      
      if(direction == 1) sprite = sprites.getSubimage(300, 0, 200, 50);
      else if(direction == 2 || direction == 5 || direction == 6) sprite = sprites.getSubimage(450, 50, 150, 50);
      else if(direction == 3) sprite = sprites.getSubimage(500, 0, 200, 50);
      else if(direction == 4 || direction == 7 || direction == 8) sprite = sprites.getSubimage(300, 50, 150, 50);
      
      
      zombieFrame = frameCount % 30 == 0 ? ++zombieFrame: zombieFrame;
      sprite = sprite.getSubimage(50 * (zombieFrame % (sprite.getWidth()/50)), 0, 50, 50);
      
      charactersGraphics.drawImage(sprite, x, y, null);
    }

    Point p = house.player.getPosition();
    x = p.x;
    y = p.y;
    
    direction = house.player.getDirection();
    
    if(house.player.getMoveFlag() != 0) playerFrame = frameCount % 3 == 0 ? ++playerFrame: playerFrame;
    
    if(direction == 1) sprite = sprites.getSubimage(775, 100, 200, 50);
    else if(direction == 2 || direction == 5 || direction == 6) sprite = sprites.getSubimage(775, 150, 200, 50);
    else if(direction == 3) sprite = sprites.getSubimage(775, 50, 200, 50);
    else if(direction == 4 || direction == 7 || direction == 8) sprite = sprites.getSubimage(775, 200, 200, 50);
    
    sprite = sprite.getSubimage(50 * (playerFrame % 4), 0, 50, 50);
    charactersGraphics.drawImage(sprite, x, y, null);
    
    HUDGraphics.clearRect(0, 0, this.getWidth(), this.getHeight());
    HUDGraphics.drawImage(sprites.getSubimage(50, 200, 50, 50), 50, this.getHeight() - 50, null);
    HUDGraphics.drawString(Integer.toString(house.player.getFireTrapCount()), 90, this.getHeight() - 30);

    updateLight();
    frameCount++;
    repaint();
  }

  //update LightSource
  public void updateLight(){
    Point playerPosition = house.player.getPosition();
    lightsGraphics.drawImage(black, 0, 0, null);
    lightsGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
//    lightSources.get(1).update(500, 500, shapes, 0, lightsGraphics);
    lightSources.get(0).update(playerPosition.x + 25, playerPosition.y, shapes, house.player.getSight(), lightsGraphics);
  }
  
  
  @Override
  public void paintComponent(Graphics g) {
    int backgroundX, backgroundY;
    backgroundX = backgroundY = 0;

    Point p;
    p = house.player.getPosition();

    backgroundX = this.getWidth() / 2 - p.x;
    backgroundY = this.getHeight() / 2 - p.y;

    if (backgroundX > 0)
      backgroundX = 0;
    else if (backgroundX + background.getWidth() < this.getWidth())
      backgroundX -= backgroundX + background.getWidth() - this.getWidth();

    if (backgroundY > 0)
      backgroundY = 0;
    else if (backgroundY + background.getHeight() < this.getHeight())
      backgroundY -= backgroundY + background.getHeight() - this.getHeight();
    
    g.setClip(new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight()));
    
    g.drawImage(background, backgroundX, backgroundY, null);
    g.drawImage(characters, backgroundX, backgroundY, null);
    g.drawImage(lights, backgroundX, backgroundY, null);
    g.drawImage(HUD, 0, 0, null);
  }
}
