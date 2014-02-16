import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class LightSource {
  private Color color;
  private int intensity = 500;
  private int width, height;
  private BufferedImage black;

  public LightSource(Color color, int width, int height) {
    this.color = color;
    this.width = width;
    this.height = height;
    black = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics g = black.createGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, width, height);;
  }

  public void update(int x, int y, List<RoundRectangle2D.Float> shapes, double intensity, Graphics2D g) {
    Area shadows = generateShadows(x, y, shapes);
    Area light = new Area(new Rectangle2D.Float(0,0,black.getWidth(), black.getHeight()));
    light.subtract(shadows);
    
//    g.drawImage(black, 0, 0, null);
    
//    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
    
    g.setClip(light);
    float[] foo = { 0, 1f };
    Color[] bar = { color, new Color(0,0,0,255) };
    g.setPaint(new RadialGradientPaint(x, y, (int)((intensity*100 + 2)/2), foo, bar));
//    g.fillRect(0, 0, width, height);
    g.fillOval(x - (int)(intensity*100/2), y - (int)(intensity*100/2), (int)(intensity*100), (int)(intensity*100));
    
    g.setColor(Color.BLACK);
//    g.fillOval(x - 200, y - 200, 400, 400);
    
//    g.fill(light);
//    g.fill(shadows);
    
  }

  private Area generateShadows(int x, int y, List<RoundRectangle2D.Float> shapes) {
    Area shadows = new Area();
    
    float shapeX, shapeY, width, height, arcWidth, arcHeight;

    for (RoundRectangle2D.Float s : shapes) {
      shapeX = s.x * 50;
      shapeY = s.y * 50;
      width = s.width * 50;
      height = s.height * 50;
      arcWidth = s.arcwidth;
      arcHeight = s.archeight;
      
      if(Math.sqrt((x - shapeX)*(x - shapeX) + (y - shapeY)*(y - shapeY)) < intensity){
        if(arcHeight == arcWidth){
          shadows.add(new Area(rectangleShadow(x, y, (int)shapeX, (int)shapeY, (int)width, (int)height)));
        }
      }

    }

    return shadows;
  }

  private Polygon rectangleShadow(int x, int y, int shapeX, int shapeY, int width, int height) {
    int[] xPoints = {0, 0, 0, 0, 0};
    int[] yPoints = {0, 0, 0, 0, 0};
    //Left side of screen
    if (x > shapeX + width) {
      
      //top
      if (y > shapeY + height) {
        xPoints = new int[] {shapeX,
                             shapeX,
                             shapeX + width,
                             shapeX + width - (x - (shapeX + width))*10,
                             shapeX - (x - shapeX)*10};
        
        yPoints = new int[] {shapeY + height, 
                             shapeY, 
                             shapeY,
                             shapeY - (y - shapeY)*10,
                             shapeY + height - (y - (shapeY + height))*10};
      }
      
      //bottom
      else if (y < shapeY) {
          xPoints = new int[] {shapeX,
                               shapeX,
                               shapeX + width,
                               shapeX + width - (x - (shapeX + width))*10,
                               shapeX - (x - shapeX)*10};

          yPoints = new int[] {shapeY, 
                               shapeY + height, 
                               shapeY + height,
                               shapeY + height - (y - (shapeY + height))*10,
                               shapeY - (y - shapeY)*10};
      }
      
      //left
      else {
        xPoints = new int[]{shapeX + width, 
                            shapeX, 
                            shapeX, 
                            shapeX + width, 
                            shapeX + width - (x - (shapeX + width))*10, 
                            shapeX + width - (x - (shapeX + width))*10};
      
        yPoints = new int[]{shapeY + height, 
                            shapeY + height, 
                            shapeY, 
                            shapeY,
                            shapeY - (y - shapeY)*10,
                            shapeY + height - (y - (shapeY + height))*10};
      } 
    
    }

      //Right side of screen
    else if (x < shapeX) {
        
        //top
        if (y > shapeY + height) {
          xPoints = new int[] {shapeX,
                               shapeX + width,
                               shapeX + width,
                               shapeX + width - (x - (shapeX + width))*10,
                               shapeX - (x - shapeX)*10};
          
          yPoints = new int[] {shapeY, 
                               shapeY, 
                               shapeY + height,
                               shapeY + height - (y - (shapeY + height))*10,
                               shapeY - (y - shapeY)*10};
        }
        
        //bottom
        else if (y < shapeY) {
            xPoints = new int[] {shapeX + width,
                                 shapeX + width,
                                 shapeX,
                                 shapeX - (x - (shapeX))*10,
                                 shapeX + width - (x - (shapeX + width))*10};

            yPoints = new int[] {shapeY, 
                                 shapeY + height, 
                                 shapeY + height,
                                 shapeY + height - (y - (shapeY + height))*10,
                                 shapeY - (y - shapeY)*10};
        }
        
        //Right
        else {
          xPoints = new int[]{shapeX, 
                              shapeX + width, 
                              shapeX + width, 
                              shapeX, 
                              shapeX - (x - shapeX)*10, 
                              shapeX - (x - shapeX)*10};
        
          yPoints = new int[]{shapeY, 
                              shapeY, 
                              shapeY + height, 
                              shapeY + height,
                              shapeY + height - (y - (shapeY + height))*10,
                              shapeY - (y - (shapeY))*10};
        
        }
      }
    
    //middle
    else{
      if(y > shapeY + height){
        xPoints = new int[]{shapeX, 
                            shapeX, 
                            shapeX + width, 
                            shapeX + width, 
                            shapeX + width - (x - (shapeX + width))*10, 
                            shapeX - (x - shapeX)*10};

        yPoints = new int[]{shapeY + height, 
                            shapeY, 
                            shapeY, 
                            shapeY + height,
                            shapeY + height - (y - (shapeY + height))*10,
                            shapeY + height- (y - (shapeY + height))*10};
      }
      else if(y < shapeY){
        xPoints = new int[]{shapeX, 
                            shapeX, 
                            shapeX + width,   
                            shapeX + width, 
                            shapeX + width - (x - (shapeX + width))*10, 
                            shapeX - (x - shapeX)*10};

        yPoints = new int[]{shapeY, 
                            shapeY + height, 
                            shapeY + height, 
                            shapeY,
                            shapeY - (y - shapeY)*10,
                            shapeY - (y - shapeY)*10};
      }
    }
    
      
    return new Polygon(xPoints, yPoints, xPoints.length);
  }

  private void circleShadow(RoundRectangle2D.Float shape) {

  }
}
