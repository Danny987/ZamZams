import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

abstract class Character
{
  protected final int TILE = 50;
  protected final int FRAMERATE = 30;
  protected float speed;
  protected float hear;
  protected boolean objectCollision = false, trapCollision = false,
      exitCollision = false;
  protected Rectangle hitbox = new Rectangle();
  protected Rectangle moveBox = new Rectangle();
  protected Point position = new Point();
  protected static CollisionMap cMap = new CollisionMap();

  /**
   * A getter method for the point ( in pixels ) of the player.
   * */
  public Point getPosition()
  {
    return this.position;
  }

  public static CollisionMap getCollisionMap()
  {
    return cMap;
  }

  /**
   * A setter method for the point ( in pixels ) of the player.
   * */
  public void setPosition(Point p)
  {
    p.x *= 50;
    p.y *= 50;
    
    this.position = new Point(p);
    this.getHitbox().setLocation(p);
  }

  public void buildMap(House curHouse)
  {
    cMap.BuildcollisionMap(curHouse);
  }

  public Rectangle getHitbox()
  {
    return this.hitbox;
  }

  int collision(CollisionMap map, int leftRight, int upDown)
  {
    // 1 = zombie collision, player dead
    exitCollision = false;
    trapCollision = false;
    objectCollision = false;

    moveBox.setBounds(this.hitbox);
    if (leftRight == 1 && upDown == 0)
    {
      moveBox.setLocation((int) (moveBox.x - speed * TILE / FRAMERATE),
          moveBox.y);
    }

    else if (leftRight == 2 && upDown == 0)
    {
      moveBox.setLocation((int) (moveBox.x + speed * TILE / FRAMERATE),
          moveBox.y);
    }

    else if (leftRight == 0 && upDown == 1)
    {
      moveBox.setLocation(moveBox.x, (int) (moveBox.y - speed * TILE
          / FRAMERATE));

    }

    else if (leftRight == 0 && upDown == 2)
    {
      moveBox.setLocation(moveBox.x, (int) (moveBox.y + speed * TILE
          / FRAMERATE));
    }
    else if (leftRight == 1 && upDown == 1)
    {
      moveBox.setLocation((int) (moveBox.x - speed * TILE / FRAMERATE),
          (int) (moveBox.y - speed * TILE / FRAMERATE));
    }
    else if (leftRight == 1 && upDown == 2)
    {
      moveBox.setLocation((int) (moveBox.x - speed * TILE / FRAMERATE),
          (int) (moveBox.y + speed * TILE / FRAMERATE));
    }

    else if (leftRight == 2 && upDown == 1)
    {
      moveBox.setLocation((int) (moveBox.x + speed * TILE / FRAMERATE),
          (int) (moveBox.y - speed * TILE / FRAMERATE));
    }

    else if (leftRight == 2 && upDown == 2)
    {
      moveBox.setLocation((int) (moveBox.x + speed * TILE / FRAMERATE),
          (int) (moveBox.y + speed * TILE / FRAMERATE));
    }

    for (Zombie zombie : map.getZombieMap())
    {
      System.out.println(zombie.getHitbox().toString());
      if (zombie.getHitbox().intersects(moveBox))
      {

        return 1;
      }
    }

    for (Tile cObj : map.getCollisionMap())
    {
      if (cObj.getHitbox().intersects(this.moveBox))
      {
        if (cObj.getChar() == 'E')
        {
          exitCollision = true;
        }
        if (cObj.getChar() == 'F')
        {
          trapCollision = true;
        }
        if (cObj.getChar() != 'E' && cObj.getChar() != 'F')
        {
          objectCollision = true;
        }

      }

    }

    return 0;
  }
}
