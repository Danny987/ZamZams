import java.awt.Rectangle;

public class Tile
{
  // initialize
  private char objType = '.';
  private int width = 1;
  private int height = 1;
  private int x = 0;
  private int y = 0;
  Rectangle hitBox = new Rectangle();

  public Tile(char objType, int width, int height, int x, int y)
  {
    this.objType = objType;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;

    hitBox.setBounds(x * 50, y * 50, width * 50, height * 50);
  }

  public void setTile(char objType, int width, int height, int x, int y)
  {
    this.objType = objType;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    hitBox.setBounds(x * 50, y * 50, width * 50, height * 50);

  }

  public char getChar()
  {
    return this.objType;
  }

  public Rectangle getHitbox()
  {
    return hitBox;
  }

  public Tile cloneTile()
  {
    Tile clonedTile = new Tile(this.objType, this.width, this.height, this.x,
        this.y);
    return clonedTile;
  }

  /*
   * public Tile cloneTile(){ Tile clonedTile = new Tile();
   * clonedTile.setTile(this.objType, this.width, this.height, this.x, this.y);
   * return clonedTile; }
   */

}
