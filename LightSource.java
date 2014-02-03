import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

public class LightSource {
  private double intensity = 0;
  private Color color;
  private static List<Shape> shapes = new ArrayList<>();

  public LightSource(Color color) {
    this.color = color;
  }

  public void update(int cx, int cy, Graphics2D g) {
    generateShadows(cx, cy, intensity);
    g.fillRect(0, 0, 500, 500);
  }

  private Area generateShadows(int cx, int cy, double intensity) {
    List<Integer> shadowsX = new ArrayList<>();
    List<Integer> shadowsY = new ArrayList<>();
    
    for (Shape s : shapes) {
      int x = s.getBounds().x;
      int y = s.getBounds().y;
      
      // Left of light source
      if(cx > x + 25){
        
        // Above 
        if(cy > y + 25){
          shadowsX.add(x);
          shadowsX.add(x);
          shadowsX.add(x + 50);
        }
        
        // Left
        else if(cy == y+25){
          
        }
        
        // below
        else if(cy < y + 25){
          
        }
      }
      
      // Right of light source
      else if(cx < x + 25){
     // Above 
        if(cy > y + 25){
          
        }
        
        // Right
        else if(cy == y+25){
          
        }
        
        // below
        else if(cy < y + 25){
          
        }
      }
    }
    return null;
  }

  public boolean changeIntensity(double intensity) {
    if (intensity >= 0)
      this.intensity = intensity;
    return false;
  }

  public int addShapes(Shape shape) {
    return shapes.size();
  }

  public boolean clearShapes() {
    shapes.clear();
    return true;
  }
}
