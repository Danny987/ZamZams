import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

abstract class Character
{
  protected final int TILE = 50;
  protected final int FRAMERATE = 30;
  protected float speed;
  protected float hear;
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

  public void buildMap(char[][] houseLayout, ArrayList<Zombie> zombieList)
  {
    cMap.BuildcollisionMap(houseLayout, zombieList);
  }

  int collision(CollisionMap map, int leftRight, int upDown)
  {
    // 0 = free movement
    // 1 = wall or object in way
    // 2 = zombie collision, player dead
    // 3 = door collision, player win
    // 4 = fire trap
    moveBox.setBounds(hitbox);
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

    for (CollisionObject cObj : map.getCollisionMap())
    {
      if (cObj.getHitbox().intersects(this.moveBox))
      {
        if (cObj.getTileType() == 'W')
        {
          return 1;
        }
        else if (cObj.getTileType() == 'D')
        {
          return 3;
        }
        else if (cObj.getTileType() == 'F')
        {
          return 4;
        }
      }

    }

    for (Zombie cObj : map.getZombieMap())
    {
      if (cObj.getHitbox().intersects(moveBox))
      {
        return 2;
      }
    }

    return 0;
  }
}
