import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * @author Mario LoPrinzi ``
 */
public class Zombie extends Character
{
  private int walkType = 0; // 0 is random walk and 1 is line walk
  private float probabiltyA, probablityB, smell;
  // 1 = north, 2 = east, 3 = south, 4 = west
  // 5 = northeast, 6 = southeast, 7 = southwest, 8 = northwest`
  private int chosenDirection = 1;
  // for line walk zombies
  private int previousDirection = 0;

  public Zombie(Point position, float smell, float speed, int walkType,
      float probabilityA, float probabilityB)
  {
    this.position = position;
    this.position.x *= 50;
    this.position.y *= 50;
    this.smell = smell;
    this.speed = speed;
    this.walkType = walkType;
    this.probabiltyA = probabilityA;
    this.probablityB = probabilityB;
    // multiply by 50 because the x and y are in tiles not pixels
    // add 5 to center the box a bit because it isn't 50 by 50
    this.hitbox = new Rectangle((position.x) + 5, (position.y) + 5, 40, 40);
  }

  public void setSmell(float adjustedSmell)
  {

    this.smell = adjustedSmell;
  }

  private void move(Zombie curZombie)
  {

    curZombie.mover();

  }

  public void chooseDirection()
  {
    if (this.walkType == 0)
    {
      if (probabiltyA <= (Math.random() ))
      {
        // chooses a random direction to move in
        chosenDirection = (int) (Math.random() * 8);
      }

      else
      {
        // no movement
        chosenDirection = 0;
      }
    }
    else
    {
      // line walk type movement
      if (probabiltyA <= (Math.random() ))
      {
        // chooses a random direction to move in
        chosenDirection = (int) (Math.random() * 7) + 1;
      }

      else
      {
        // same movement
        chosenDirection = previousDirection;
      }
    }
  }

  public static void zombieWalk(ArrayList<Zombie> zombieList)
  {
    for (Zombie curZombie : zombieList)
    {
      System.out.println("*******" + curZombie.getHitbox().toString());
      curZombie.move(curZombie);
    }
  }

  private void mover()
  {
    int leftRight = 0, upDown = 0;

    switch (chosenDirection)
    {
    case 0:
    {
      leftRight = 0;
      upDown = 0;
      break;
    }
    case 1:
    {
      leftRight = 0;
      upDown = 1;
      break;
    }
    case 2:
    {
      leftRight = 2;
      upDown = 0;
      break;
    }
    case 3:
    {
      leftRight = 0;
      upDown = 2;
      break;
    }
    case 4:
    {
      leftRight = 1;
      upDown = 0;
      break;
    }
    case 5:
    {
      leftRight = 2;
      upDown = 1;
      break;
    }
    case 6:
    {
      leftRight = 2;
      upDown = 2;
      break;
    }
    case 7:
    {
      leftRight = 1;
      upDown = 2;
      break;
    }
    case 8:
    {
      leftRight = 1;
      upDown = 1;
      break;
    }
    }
    int collide = collision(cMap, leftRight, upDown);

    if (collide == 1)
    {
      /**
       * zombie hit zombie
       * */
    }
    if (this.trapCollision == true)
    {
      /**
       * player has triggered a trap
       */
    }
    else if (this.chosenDirection == 0)
    {
      previousDirection = direction;
    }

    else if (this.chosenDirection == 4)
    {

      // free movement
      this.position.x -= (TILE * speed / FRAMERATE);
      this.hitbox = new Rectangle(position.x + 5, position.y + 5, 40, 40);

      lastMovedDirection = direction;
      previousDirection = direction;
      this.direction = 4;

    }

    else if (this.chosenDirection == 2)
    {

      // free movement
      this.position.x += (TILE * speed / FRAMERATE);
      this.hitbox = new Rectangle(position.x + 5, position.y + 5, 40, 40);
      previousDirection = direction;
      lastMovedDirection = direction;
      this.direction = 2;

    }
    else if (this.chosenDirection == 1)
    {

      // free movement
      this.position.y -= (TILE * speed / FRAMERATE);
      this.hitbox = new Rectangle(position.x + 5, position.y + 5, 40, 40);
      lastMovedDirection = direction;
      previousDirection = direction;
      this.direction = 1;
    }

    else if (this.chosenDirection == 3)
    {

      // free movement
      this.position.y += (TILE * speed / FRAMERATE);
      this.hitbox = new Rectangle(position.x + 5, position.y + 5, 40, 40);
      lastMovedDirection = direction;
      previousDirection = direction;
      this.direction = 3;
    }

    else if (this.chosenDirection == 8)
    {
      // free movement
      this.position.x -= ((TILE * speed / FRAMERATE) / Math.sqrt(2));
      this.position.y -= ((TILE * speed / FRAMERATE) / Math.sqrt(2));
      this.hitbox = new Rectangle(position.x + 5, position.y + 5, 40, 40);
      lastMovedDirection = direction;
      previousDirection = direction;
      this.direction = 8;
    }
    else if (this.chosenDirection == 7)
    {

      // free movement
      this.position.x -= ((TILE * speed / FRAMERATE) / Math.sqrt(2));
      this.position.y += ((TILE * speed / FRAMERATE) / Math.sqrt(2));
      this.hitbox = new Rectangle(position.x + 5, position.y + 5, 40, 40);
      lastMovedDirection = direction;
      previousDirection = direction;
      this.direction = 7;
    }

    else if (this.chosenDirection == 5)
    {

      // free movement
      this.position.x += ((TILE * speed / FRAMERATE) / Math.sqrt(2));
      this.position.y -= ((TILE * speed / FRAMERATE) / Math.sqrt(2));
      this.hitbox = new Rectangle(position.x + 5, position.y + 5, 40, 40);
      lastMovedDirection = direction;
      previousDirection = direction;
      this.direction = 5;
    }

    else if (this.chosenDirection == 6)
    {

      // free movement
      this.position.x += ((TILE * speed / FRAMERATE) / Math.sqrt(2));
      this.position.y += ((TILE * speed / FRAMERATE) / Math.sqrt(2));
      this.hitbox = new Rectangle(position.x + 5, position.y + 5, 40, 40);
      lastMovedDirection = direction;
      previousDirection = direction;
      this.direction = 6;
    }
    return;
  }
}
