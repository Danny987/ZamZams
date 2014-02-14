import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author Mario LoPrinzi
 * 
 */
public class Zombie extends Character
{
  private int walkType = 0; // 0 is random walk and 1 is line walk
  private float probabiltyA, probablityB, smell;

  public Zombie(Point position, float smell, float speed, int walkType,
      float probabilityA, float probabilityB)
  {
    this.position = position;
    this.smell = smell;
    this.speed = speed;
    this.walkType = walkType;
    this.probabiltyA = probabilityA;
    this.probablityB = probabilityB;
    this.hitbox = new Rectangle((position.x)*50, (position.y)*50, 50, 50);
  }

  public void setSmell(float adjustedSmell)
  {
    this.smell = adjustedSmell;
  }

 
}
