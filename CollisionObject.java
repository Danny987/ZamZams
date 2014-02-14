import java.awt.Rectangle;

/**
 * @author Mario LoPrinzi
 * 
 */
public class CollisionObject
{
  private Rectangle hitbox = new Rectangle();
  private char tileType;

  public CollisionObject(Rectangle newBox, char tile)
  {
    hitbox = newBox;
    tileType = tile;
  }

  public Rectangle getHitbox()
  {
    return hitbox;
  }

  public char getTileType()
  {
    return tileType;
  }
}
