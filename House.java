import java.awt.Point;

public class House {
	//HOUSE LAYOUT
	private char houseLayout[][] = new char[100][100];
	
	//HOUSE PROPERTIES
	private int houseNumber = 0;
	private int width = 0;
	private int length = 0;
	private String exitWall = "";
	
	//PLAYER PROPERTIES
	private Point playerLoc = new Point(0, 0);
	private int playerSight = 0;
	private int playerHear  = 0;
	private float playerSpeed = 0;
	private int playerStamina = 0;
	private float playerRegen = 0;
	
	public void setProperties(int houseNumber, int width, int length, String exitWall)
	{
		this.houseNumber = houseNumber;
		this.width = width;
		this.length = length;
		this.exitWall = exitWall;
		
		//fill the floor and walls
		for (int y = 0; y < length; y++)
			for (int x = 0; x < width; x++)
				if((x == 0) || (x == width-1) || (y == 0) || (y == length-1))
					houseLayout[x][y] = 'W';
				else
					houseLayout[x][y] = '.';
	}
	
	//create an object based on 4 coordinates
	public boolean createObj(int x1, int x2, int y1, int y2) {
		int area = (Math.abs(x2 - x1)+1 * Math.abs(y2 - y1)+1); //get area of the obj
		int leftX = x1; int rightX = x2; int topY = y1; int botY = y2;
		
		//set the coordinates appropriately so that we work
		//with the top left corner of the object.
		if(x1 > x2)
		{
			leftX = x2;
			rightX = x1;
		}
		if(y1 > y2)
		{
			topY = y2;
			botY = y1;
		}
		
		//insert obj in the house layout
		for(int y = topY; y < botY+1; y++)
		{
			for(int x = leftX; x < rightX+1; x++)
			{
				if(houseLayout[x][y] != '.')
					return false;
			}
			for(int x = leftX; x < rightX+1; x++)
			{
				if(area <= 1) houseLayout[x][y] = 'S';
				else if(area > 9) houseLayout[x][y] = 'L';
				else houseLayout[x][y] = 'M';
			}
		}
		return true;
	}
	
	public boolean createZombie(int x, int y, int smell, float speed, int walkType, int probA, int probB)
	{
		//create zombie object
		//set zombie properties
		//check for valid x/y
		//add zombie to zombieList
		return false;
	}
	
	//creates an inner wall
	public boolean createWall(int wallType, int c1, int c2, int c3) {
		//wallType 1 = vertical, 2 = horizontal
		
		int low = c2;
		int high = c3;
		
		//set the coordinates appropriately so that we work
		//with the top left corner of the wall.
		if(c2 > c3)
		{
			low = c3;
			high = c2;
		}
		
		if(wallType == 1) //insert vertical wall
		{
			for(int y = low; y < high+1; y++)
			{
				if(houseLayout[c1][y] != '.') 
					return false;
			}
			for(int y = low; y < high+1; y++)
				houseLayout[c1][y] = 'V';
			
		}
		else if (wallType == 2) //insert horizontal wall
		{
			for(int x = low; x < high+1; x++)
			{
				if(houseLayout[x][c1] != '.') return false;
			}
			for(int x = low; x < high+1; x++)
				houseLayout[x][c1] = 'H';
		}
		return true;
	}
	
	public void printProperties()
	{
		System.out.println("House Number: " + houseNumber);
		System.out.println("  Width: " + width);
		System.out.println("  Length: " + length);
		System.out.println("  Exit Wall: " + exitWall + "\n");
	}
	
	//draw the house layout array
	public void drawHouse()
	{
		for(int y = 0; y < length; y++)
		{
			for(int x = 0; x < width; x++)
				System.out.print(houseLayout[x][y]);

			System.out.print("\n");
		}
	}

	public boolean createFirettrap(int x, int y) {
		// TODO Auto-generated method stub
		if(houseLayout[x][y] == '.')
			houseLayout[x][y] = 'F';
		else
			return false;
		return true;
	}

	public boolean createPlayer(int sight, int hear, float speed, int stamina,
			float regen) {
		// TODO Auto-generated method stub
		return false;
	}
}
