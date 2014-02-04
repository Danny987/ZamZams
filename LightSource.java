import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.Polygon;
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
    g.setBackground(new Color(0,0,0,0));
    g.clearRect(0, 0 ,900, 900);

    Area shadows = generateShadows(cx, cy, intensity);
    Area light = new Area(new Rectangle2D.Float(0, 0, 900, 900));        
    //light.subtract(shadows);
    //g.setClip(light);

    // g.setColor(Color.BLACK);
    // g.fill(shadows);

    float[] foo = { 0, 1f };
    Color[] bar = { new Color(255, 0, 0, 0), Color.BLACK};
    g.setPaint(new RadialGradientPaint(cx, cy, 200, foo, bar)); 
    g.fillRect(0, 0, 900, 900);

    g.setColor(Color.BLACK);
    g.fill(shadows);
  }

  private Area generateShadows(int cx, int cy, double intensity) {
    List<Integer> shadowsX = new ArrayList<>();
    List<Integer> shadowsY = new ArrayList<>();
    Area shadows = new Area();

    for(Shape s : shapes){
      int xPos = s.getBounds().x;
      int yPos = s.getBounds().y;
      int width = 50;
      int height = 50;

      int[] xPoints = new int[5];
      int[] yPoints = new int[5];

     ///////////////////////////////////////////////////////////////////////
     // Left Side
     /////////////////////////////////////////////////////////////////////// 
      if(cx > xPos + width / 2){
       
        ////////////////////////////////////////////////////////////////////
        // Above
        ////////////////////////////////////////////////////////////////////

        if(cy > yPos + height / 2){
          xPoints[0] = xPos;
          xPoints[1] = xPos;
          xPoints[2] = xPos + width;

          yPoints[0] = yPos + height;
          yPoints[1] = yPos;
          yPoints[2] = yPos;

          xPoints[3] = xPoints[2] - (cx - xPoints[2]) * 100;
          yPoints[3] = yPoints[2] - (cy - yPoints[2]) * 100;

          xPoints[4] = xPoints[0] - (cx - xPoints[0]) * 100;
          yPoints[4] = yPoints[0] - (cy - yPoints[0]) * 100;
        }

        ////////////////////////////////////////////////////////////////////
        // Below
        ////////////////////////////////////////////////////////////////////
        else if(cy < yPos + height / 2){
          xPoints[0] = xPos;
          xPoints[1] = xPos;
          xPoints[2] = xPos + width;

          yPoints[0] = yPos;
          yPoints[1] = yPos + height;
          yPoints[2] = yPos + height;

          xPoints[3] = xPoints[2] - (cx - xPoints[2]) * 100;
          yPoints[3] = yPoints[2] - (cy - yPoints[2]) * 100;

          xPoints[4] = xPoints[0] - (cx - xPoints[0]) * 100;
          yPoints[4] = yPoints[0] - (cy - yPoints[0]) * 100;
        }

        /////////////////////////////////////////////////////////////////////
        // Left
        /////////////////////////////////////////////////////////////////////
        else{
          // xPoints = new int[6];
          // xPoints[0] = xPos + width;
          // xPoints[1] = xPos;
          // xPoints[2] = xPos;
          // xPoints[3] = xPos + width;
          //
          // yPoints = new int[6];
          // yPoints[0] = yPos;
          // yPoints[1] = yPos;
          // yPoints[2] = yPos + height;
          // yPoints[3] = yPos + height;
          //
          // xPoints[4] = xPoints[0] - size;
          // yPoints[4] = yPoints[0] - Math.round((yPoints[0] - cy)/(xPoints[0] - cx));
          //
          // xPoints[5] = xPoints[2] - size;
          // yPoints[5] = yPoints[2] + Math.round((yPoints[2] - cy)/(xPoints[2] - cx));
        }
      }

      /////////////////////////////////////////////////////////////////////////
      // Right Side
      /////////////////////////////////////////////////////////////////////////
      else if(cx < xPos + width / 2){

        ///////////////////////////////////////////////////////////////////////
        // Above
        ///////////////////////////////////////////////////////////////////////
        if(cy > yPos + height / 2){
          xPoints[0] = xPos;
          xPoints[1] = xPos + width;
          xPoints[2] = xPos + width;

          yPoints[0] = yPos;
          yPoints[1] = yPos;
          yPoints[2] = yPos + height;

          xPoints[3] = xPoints[2] + (xPoints[2] - cx) * 100;
          yPoints[3] = yPoints[2] - (cy - yPoints[2]) * 100;

          xPoints[4] = xPoints[0] + (xPoints[0] - cx) * 100;
          yPoints[4] = yPoints[0] - (cy - yPoints[0]) * 100;
        }

        ////////////////////////////////////////////////////////////////////////
        // Below
        ////////////////////////////////////////////////////////////////////////
        else if(cy < yPos + height / 2){
          xPoints[0] = xPos + width;
          xPoints[1] = xPos + width;
          xPoints[2] = xPos;

          yPoints[0] = yPos;
          yPoints[1] = yPos + height;
          yPoints[2] = yPos + height;

          xPoints[3] = xPoints[2] + (xPoints[2] - cx) * 100;
          yPoints[3] = yPoints[2] - (cy - yPoints[2]) * 100;

          xPoints[4] = xPoints[0] + (xPoints[0] - cx) * 100;
          yPoints[4] = yPoints[0] - (cy - yPoints[0]) * 100;
        }

        ///////////////////////////////////////////////////////////////////////////
        // Right
        ///////////////////////////////////////////////////////////////////////////
        else{
         // xPoints = new int[6];
         // xPoints[0] = xPos;
         // xPoints[1] = xPos + width;
         // xPoints[2] = xPos + width;
         // xPoints[3] = xPos;

         // yPoints = new int[6];
         // yPoints[0] = yPos;
         // yPoints[1] = yPos;
         // yPoints[2] = yPos + height;
         // yPoints[3] = yPos + height;

         // xPoints[4] = xPoints[0] + width + (xPoints[0] - cx) * 100;
         // xPoints[5] = xPoints[2] + width + yPoints[0] - (cy - yPoints[0]) * 100;

          // yPoints[4] = yPoints[0] + Math.round((yPoints[0] - cy) / (xPoints[0] - cx));
          // yPoints[5] = yPoints[2] + Math.round((yPoints[2] - cy) / (xPoints[2] - cx));
        }
      }

      //////////////////////////////////////////////////////////////////////////
      // Centered
      //////////////////////////////////////////////////////////////////////////
      else{
        // xPoints = new int[6];
        // yPoints = new int[6];

        // xPoints[0] = xPos;
        // xPoints[1] = xPos;
        // xPoints[2] = xPos + width;
        // xPoints[3] = xPos + width;

        // yPoints[0] = yPos + height;
        // yPoints[1] = yPos;
        // yPoints[2] = yPos;
        // yPoints[3] = yPos + height;

        // xPoints[4] = xPoints[0] + width;
        // xPoints[4] = xPoints[3] + width;

        // yPoints[5] = yPoints[0] + Math.round((yPoints[0] - cy) / (xPoints[0] - cx));
        // yPoints[5] = yPoints[3] + Math.round((yPoints[3] - cy) / (xPoints[3] - cx));
      }

      shadows.add(new Area(new Polygon(xPoints, yPoints, xPoints.length)));
    }
    return shadows;
  }

  public boolean changeIntensity(double intensity) {
    if (intensity >= 0)
      this.intensity = intensity;
    return false;
  }

  public int addShapes(Shape shape) {
    shapes.add(shape);
    return shapes.size();
  }

  private int[] listToArray(List<Integer> list){
    int[] array = new int[list.size()];
    for(int i = 0; i < list.size(); i++){
      array[i] = list.get(i);
    }
    return array;
  }

  public boolean clearShapes() {
    shapes.clear();
    return true;
  }
}
