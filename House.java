import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class House {
	//HOUSE LAYOUT
	private Tile[][] tileLayout = new Tile[100][100];

	//HOUSE PROPERTIES
	private int houseNumber = 0;
	private int houseWidth = 0;
	private int houseLength = 0;
	private String exitWall = "";
	
	//minimum distance from the exit
	private int exitDistance = 10;

	//list of zombie objects
	ArrayList<Zombie> zombieList = new ArrayList<>();
	
	//list of tile objects
	ArrayList<Tile> tileList = new ArrayList<>();
	
	Random rand = new Random();
	
	public House(){
		for (int y=0;y<100;y++)
		{
			for (int x=0;x<100;x++)
		    tileLayout[x][y] = new Tile('.',1,1,x,y);  // create each actual tile
		}
	}

	public void setProperties(int houseNumber, int houseWidth, int houseLength, String exitWall)
	{
		this.houseNumber = houseNumber;
		this.houseWidth = houseWidth;
		this.houseLength = houseLength;
		this.exitWall = exitWall;

		//fill the floor and walls
		for (int y = 0; y < houseLength; y++)
			for (int x = 0; x < houseWidth; x++)
				if((x == 0) || (x == houseWidth-1) || (y == 0) || (y == houseLength-1)){
					tileLayout[x][y].setTile('X', 1, 1,x,y);
				} 
				else{
					tileLayout[x][y].setTile('.', 1, 1,x,y);
				}
	}

	//create an object based on 4 coordinates
	public boolean createObj(int x1, int x2, int y1, int y2) {
		int area = (Math.abs(x2 - x1)+1 * Math.abs(y2 - y1)+1); //get area of the obj
		int leftX = x1; int rightX = x2; int topY = y1; int botY = y2;
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);
		
		//check that the object is inside the house
		if(x1 > houseWidth || x2 > houseWidth || y1 > houseLength || y2 > houseLength)
			return false;
		
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
				if(tileLayout[x][y].getChar() != '.')
				{
					return false;
				}
			}
			
			for(int x = leftX; x < rightX+1; x++)
			{
				if(x == leftX && y == topY)
				{
					if(area <= 1)
					{
						tileLayout[x][y].setTile('S', width, height,x,y);
						tileList.add(new Tile('S', width, height,x,y));
					}
					else if(area > 9){
						tileLayout[x][y].setTile('L', width, height,x,y);
						tileList.add(new Tile('L', width, height,x,y));
					}
					else{
						tileLayout[x][y].setTile('M', width, height,x,y);
						tileList.add(new Tile('M', width, height,x,y));
					}
				}
				else{
					tileLayout[x][y].setTile('X', 1, 1,x,y);
				}
			}
		}
		return true;
	}

	public void createZombie(int x, int y, int smell, float speed, int walkType, int probA, int probB)
	{
		Point position = new Point(x,y);
		//create zombie object
		Zombie zombie = new Zombie(position, smell, speed, walkType, probA, probB);
		//add zombie to list
		zombieList.add(zombie);
	}

	//creates an inner wall
	public boolean createWall(int wallType, int c1, int c2, int c3) {
		//wallType 1 = vertical, 2 = horizontal
		
		int length = Math.abs(c3-c2);
		
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
			//make sure the wall is in the house
			if(c1 > houseWidth || c2 > houseLength || c3 > houseLength)
				return false;
			
			for(int y = low; y < high+1; y++)
			{
				if(tileLayout[c1][y].getChar() != '.') 
					return false;
			}
			for(int y = low; y < high+1; y++){
				if(y == low)
				{
					tileLayout[c1][y].setTile('V',1,length,c1,y);
					tileList.add(new Tile('V',1,length,c1,y));
				}
				else
				{
					tileLayout[c1][y].setTile('X',1,1,c1,y);
				}
			}

		}
		else if (wallType == 2) //insert horizontal wall
		{
			//make sure the wall is in the house
			if(c1 > houseLength || c2 > houseWidth || c3 > houseWidth)
				return false;
			
			for(int x = low; x < high+1; x++)
			{
				if(tileLayout[x][c1].getChar() != '.') 
					return false;
			}
			for(int x = low; x < high+1; x++)
			{
				if(x == low)
				{
					tileLayout[x][c1].setTile('H',1,length, x, c1);
					tileList.add(new Tile('H',1,length, x, c1));
				}
				else
				{
					tileLayout[x][c1].setTile('X',1,1,x,c1);
				}
			}
			
		}
		return true;
	}

	public void printProperties()
	{
		System.out.println("House Number: " + houseNumber);
		System.out.println("  Width: " + houseWidth);
		System.out.println("  Length: " + houseLength);
		System.out.println("  Exit Wall: " + exitWall + "\n");
	}

	//draw the house layout array
	public void drawHouse()
	{
		for(int y = 0; y < houseLength; y++)
		{
			for(int x = 0; x < houseWidth; x++)
				System.out.print(tileLayout[x][y].getChar());

			System.out.print("\n");
		}
	}

	public boolean createFirettrap(int x, int y) {
		// TODO Auto-generated method stub
		if(tileLayout[x][y].getChar() == '.')
		{
			tileLayout[x][y].setTile('F',1 ,1, x, y);
			tileList.add(new Tile('F',1 ,1, x, y));
		}
		else
		{
			return false;
		}
		return true;
	}

	public boolean createPlayer(int sight, int hear, float speed, int stamina,
			float regen) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setZombiePositions() {
		// TODO Auto-generated method stub
		for(int i = 0; i < zombieList.size(); i++)
		{
			//get posiiton point from zombie
			Point position = zombieList.get(i).getPosition();

			//if the zombie tag didn't have an x and y
			if(position.x == -1 && position.y == -1)
			{
				//get all the possible tiles that the zombie can be placed in
				//DO NOT GENERATE EACH TIME, JUST REMOVE ZOMBIE
				ArrayList<Point> tileList = new ArrayList<>();
				for (int y = 0; y < houseLength; y++)
				{
					for (int x = 0; x < houseWidth; x++)
					{
						if(tileLayout[x][y].getChar() == '.')
						{
							Point validTile = new Point(x,y); //put valid tile in list
							tileList.add(validTile);
						}
					}
				}		
				//if no valid tiles
				if(tileList.size() == 0)
					return false;
				
				//pick a random tile
				int r = rand.nextInt(tileList.size()+1);

				//set zombie in houselayout
				tileLayout[tileList.get(r).x][tileList.get(r).y].setTile('Z', 1, 1, tileList.get(r).x, tileList.get(r).y);
				
				//set position in zombie object
				zombieList.get(i).setPosition(tileList.get(r));
			}
			//if the zombie had an x and y
			tileLayout[position.x][position.y].setTile('Z', 1, 1, position.x, position.y);
		}
		return true;
	}
	
	public boolean setPlayerExit()
	{
		//convert to a char array
		char[][] houseLayout = new char[houseWidth][houseLength];
		for(int y = 0; y < houseLength; y++)
		{
			for(int x  = 0; x < houseLength; x++)
			{
				houseLayout[x][y] = tileLayout[x][y].getChar();
			}
		}
		
		if(exitWall.equals("north"))
		{
			for(int x = 1; x < houseWidth-1; x++)
			{
				if(houseLayout[x][1] == '.')
					houseLayout = floodFill(x, 1, x, 0, houseLayout);
			}
		}
		else if(exitWall.equals("south"))
		{
			for(int x = 1; x < houseWidth-1; x++)
			{
				if(houseLayout[x][houseLength-2] == '.')
					houseLayout = floodFill(x, houseLength-2, x, houseLength-1, houseLayout);
			}
		}
		else if(exitWall.equals("east"))
		{
			for(int y = 1; y < houseLength-1; y++)
			{
				if(houseLayout[1][y] == '.')
					houseLayout = floodFill(1, y, 0, y, houseLayout);
			}
		}
		else if(exitWall.equals("west"))
		{
			for(int y = 1; y < houseLength-1; y++)
			{
				if(houseLayout[houseWidth-2][y] == '.')
					houseLayout = floodFill(houseWidth-2, y, houseWidth-1, y, houseLayout);
			}
		}
		
		//print char array
		/*for(int y = 0; y < houseLength; y++)
		{
			for(int x  = 0; x < houseLength; x++)
			{
				System.out.print(houseLayout[x][y]);
			}
			System.out.print("\n");
		}*/
		
		return true;
	}
	
	public char[][] floodFill(int x, int y, int exitX, int exitY, char[][] houseLayout)
	{
		houseLayout[exitX][exitY] = 'E';
		if(houseLayout[x][y] == '.' || houseLayout[x][y] == 'F')
		{
			houseLayout[x][y] = ' ';
			floodFill(x+1, y, exitX, exitY, houseLayout);
			floodFill(x-1, y, exitX, exitY, houseLayout);
			floodFill(x, y+1, exitX, exitY, houseLayout);
			floodFill(x, y-1, exitX, exitY, houseLayout);
		}
		return houseLayout;
	}
	
	public boolean distanceCheck(int x1, int y1, int x2, int y2)
	{
		int d = (int)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
		if(d > exitDistance)
			return true;
		else
			return false;
	}
	
	public void printTileList()
	{
		for(int i = 0; i < tileList.size(); i++)
		{
			System.out.print(tileList.get(i).getChar());
		}
	}

  public int getHouseWidth()
  {
    return houseWidth;
  }

  public void setHouseWidth(int houseWidth)
  {
    this.houseWidth = houseWidth;
  }

  public int getHouseLength()
  {
    return houseLength;
  }

  public void setHouseLength(int houseLength)
  {
    this.houseLength = houseLength;
  }
}
