import java.awt.Rectangle;

public class Tile
{
	//tile chars
	public final char S_OBJECT = 'S';
	public final char M_OBJECT = 'M';
	public final char L_OBJECT = 'L';
	public final char V_WALL = 'V';
	public final char H_WALL = 'H';
	public final char FIRETRAP = 'F';
	public final char ZOMBIE = 'Z';
	public final char PLAYER = 'P';
	public final char FLOOR = '.';
	public final char BLOCK = 'X';
	
	
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
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
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
