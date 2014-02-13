
public class Tile {
	//initialize
	private char objType = '.';
	private int width = 1;
	private int height = 1;
	int x = 0;
	int y = 0;

	public Tile(char objType, int width, int height, int x, int y){
		this.objType = objType;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	public void setTile(char objType, int width, int height, int x, int y){
		this.objType = objType;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	public char getChar(){
		return this.objType;
	}

	public Tile cloneTile(){
		Tile clonedTile = new Tile(this.objType, this.width, this.height, this.x, this.y);
		return clonedTile;
	}
	
	/*public Tile cloneTile(){
		Tile clonedTile = new Tile();
		clonedTile.setTile(this.objType, this.width, this.height, this.x, this.y);
		return clonedTile;
	}*/

}
