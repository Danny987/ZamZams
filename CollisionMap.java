import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * @author Mario LoPrinzi
 * 
 */
public class CollisionMap
{
  private ArrayList<CollisionObject> collisionMap = new ArrayList<CollisionObject>();
  private ArrayList<Zombie> zombieMap = new ArrayList<Zombie>();

  public void BuildcollisionMap(char[][] houseLayout,
      ArrayList<Zombie> zombieList)
  {
    zombieMap = zombieList;
    int i = 0;
    int j = 0;
    for (char[] c : houseLayout)
    {

      for (char x : c)
      {

        if (x == '.')
        {
          j++;
        }

        else
        {
          collisionMap.add(new CollisionObject(new Rectangle(i * 50, j * 50,
              50, 50), x));
          j++;
        }

      }
      j = 0;
      i++;
    }
    for (Zombie zom : zombieMap)
    {
      collisionMap.add(new CollisionObject(new Rectangle(
          zom.getPosition().x * 50, zom.getPosition().y * 50, 50, 50), 'Z'));
    }
  }

  public ArrayList<CollisionObject> getCollisionMap()
  {
    return collisionMap;
  }

  public ArrayList<Zombie> getZombieMap()
  {
    return zombieMap;
  }
}