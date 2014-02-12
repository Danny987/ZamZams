
public class Tile {
	//initialize
	private char objType = '.';
	private int width = 1;
	private int height = 1;
	
	public Tile(){

	}
	
	public void setTile(char objType, int width, int height){
		this.objType = objType;
		this.width = width;
		this.height = height;
	}
	
	public char getChar(){
		return this.objType;
	}
	
	public Tile cloneTile(){
		Tile clonedTile = new Tile();
		clonedTile.setTile(this.objType, this.width, this.height);
		return clonedTile;
	}

}
