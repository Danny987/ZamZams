import java.util.ArrayList;


/**
 * @author Mario LoPrinzi
 * 
 */
public class CollisionMap
{
  private ArrayList<Tile> collisionMap;
  private ArrayList<Zombie> zombieMap;

  public void BuildcollisionMap(House curHouse)
  {
    collisionMap = new ArrayList<Tile>(curHouse.tileList);
    zombieMap = new ArrayList<Zombie>(curHouse.zombieList);
    collisionMap.add(new Tile('W', curHouse.getHouseWidth(), 1, 0, 0));
    collisionMap.add(new Tile('W', 1, curHouse.getHouseLength(), 0, 0));
    collisionMap.add(new Tile('W', curHouse.getHouseWidth(), 1, 0,
        curHouse.getHouseLength()-1));
    collisionMap.add(new Tile('W', 1, curHouse.getHouseLength(), curHouse
        .getHouseWidth()-1, 0));

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
