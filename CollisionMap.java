import java.util.ArrayList;

/**
 * @author Mario LoPrinzi
 * 
 */
public class CollisionMap
{
  private ArrayList<Tile> collisionMap;
  private ArrayList<Zombie> zombieMap;

  public void BuildcollisionMap(ArrayList<Tile> houseLayout,
      ArrayList<Zombie> zombieList)
  {
    collisionMap = new ArrayList<Tile>(houseLayout);
    zombieMap = new ArrayList<Zombie>(zombieList);

  }

  public ArrayList<Tile> getCollisionMap()
  {
    return collisionMap;
  }

  public ArrayList<Zombie> getZombieMap()
  {
    return zombieMap;
  }
}