import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class House {
	//HOUSE LAYOUT
	private Tile[][] tileLayout = new Tile[100][100];

	//HOUSE PROPERTIES
	private int houseNumber = 0;
	private int houseWidth = 0;
	private int houseLength = 0;
	private String exitWall = "";

	//player and exit location
	private int player_X = 0;
	private int player_Y = 0;
	private int exit_X = 0;
	private int exit_Y = 0;

	//minimum distance from the exit
	private int exitDistance = 0;

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
	
	//setter and getters
	public int getHouseWidth() {
		return houseWidth;
	}

	public int getHouseLength() {
		return houseLength;
	}

	//sets the basic properties of the house
	//houseNumber = nth house in config file
	//houseWidth = width of house in tiles
	//houseLength = height of house in tiles
	//exitWall = wall that has the exit
	public void setProperties(int houseNumber, int houseWidth, int houseLength, String exitWall)
	{
		//set globals
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
				//if the tile is not a floor, object can't be placed
				if(tileLayout[x][y].getChar() != '.')
				{
					return false;
				}
			}

			//place the object
			for(int x = leftX; x < rightX+1; x++)
			{
				if(x == leftX && y == topY)
				{
					//change object type depending on area
					if(area <= 1)
					{
						//add it the obj array
						tileLayout[x][y].setTile('S', width, height,x,y);
						//add it to the obj list
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
					//inside of the object is just X's
					tileLayout[x][y].setTile('X', 1, 1,x,y);
				}
			}
		}
		return true; //object created
	}

	//create a zombie object and add it to the zombie list
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
				//if the position is not empty
				if(tileLayout[c1][y].getChar() != '.') 
					return false; //can't place wall
			}
			for(int y = low; y < high+1; y++){
				if(y == low)
				{
					//add the wall to the obj array
					tileLayout[c1][y].setTile('V',1,length,c1,y);
					//add the wall to the obj list
					tileList.add(new Tile('V',1,length,c1,y));
				}
				else
				{
					//the rest of the wall is just X's
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
		this.exitDistance = sight*2;
		return true;
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
				//tileLayout[tileList.get(r).x][tileList.get(r).y].setTile('Z', 1, 1, tileList.get(r).x, tileList.get(r).y);

				//set position in zombie object
				zombieList.get(i).setPosition(tileList.get(r));
			}
			//if the zombie had an x and y
			//tileLayout[position.x][position.y].setTile('Z', 1, 1, position.x, position.y);
		}
		return true;
	}



	//This method finds a valid Exit and Player location
	public boolean setPlayerExit()
	{
		//map that will contain all possible combinations
		//of valid exit and player locations. Exit = key, Player = value.
		//Player locations are stored in a list since there can be many values.
		HashMap<Point, ArrayList<Point>> validExits = new HashMap<Point, ArrayList<Point>>();


		//convert obj array to a char array
		//its easier to path find through the char array instead of the obj array
		char[][] houseLayout = new char[houseWidth][houseLength];
		for(int y = 0; y < houseLength; y++)
		{
			for(int x  = 0; x < houseWidth; x++)
			{
				houseLayout[x][y] = tileLayout[x][y].getChar();
			}
		}


		//For each wall type, get valid exit and player locations

		//if exit is on south wall
		if(exitWall.equals("south"))
		{
			//go along south wall
			for(int x = 1; x < houseWidth-1; x++)
			{
				//create a copy of houseLayout to avoid tampering with the original
				char[][] floodedLayout = new char[houseWidth][houseLength];
				for(int i = 0; i < houseLength; i++)
					for(int j = 0; j < houseWidth; j++)
						floodedLayout[j][i] = houseLayout[j][i];


				//if tile in front of current possible exit location is empty
				if(houseLayout[x][houseLength-2] == '.')
				{
					//Flood fill the copied layout to find all connecting tiles
					floodedLayout = floodFill(x, houseLength-2, x, houseLength-1, floodedLayout);
					//add the exit location and possible player tiles to the map
					validExits.put(new Point(x, houseLength-1), getValidTiles(x, houseLength-1, floodedLayout));
				}
			}
		}
		else if(exitWall.equals("north"))
		{
			//go along north wall
			for(int x = 1; x < houseWidth-1; x++)
			{
				//create a copy of houseLayout to avoid tampering with the original
				char[][] floodedLayout = new char[houseWidth][houseLength];
				for(int i = 0; i < houseLength; i++)
					for(int j = 0; j < houseWidth; j++)
						floodedLayout[j][i] = houseLayout[j][i];


				//if tile in front of current possible exit location is empty
				if(houseLayout[x][houseLength-2] == '.')
				{
					//Flood fill the copied layout to find all connecting tiles
					floodedLayout = floodFill(x, 1, x, 0, floodedLayout);
					//add the exit location and possible player tiles to the map
					validExits.put(new Point(x, 0), getValidTiles(x, 0, floodedLayout));
				}
			}
		}
		else if(exitWall.equals("west"))
		{
			//go along west wall
			for(int y = 1; y < houseLength-1; y++)
			{
				//create a copy of houseLayout to avoid tampering with the original
				char[][] floodedLayout = new char[houseWidth][houseLength];
				for(int i = 0; i < houseLength; i++)
					for(int j = 0; j < houseWidth; j++)
						floodedLayout[j][i] = houseLayout[j][i];


				//if tile in front of current possible exit location is empty
				if(houseLayout[1][y] == '.')
				{
					//Flood fill the copied layout to find all connecting tiles
					floodedLayout = floodFill(1, y, 0, y, floodedLayout);
					//add the exit location and possible player tiles to the map
					validExits.put(new Point(0, y), getValidTiles(0, y, floodedLayout));
				}
			}
		}
		else if(exitWall.equals("east"))
		{
			//go along east wall
			for(int y = 1; y < houseLength-1; y++)
			{
				//create a copy of houseLayout to avoid tampering with the original
				char[][] floodedLayout = new char[houseWidth][houseLength];
				for(int i = 0; i < houseLength; i++)
					for(int j = 0; j < houseWidth; j++)
						floodedLayout[j][i] = houseLayout[j][i];


				//if tile in front of current possible exit location is empty
				if(houseLayout[houseWidth-2][y] == '.')
				{
					//Flood fill the copied layout to find all connecting tiles
					floodedLayout = floodFill(houseWidth-2, y, houseWidth-1, y, floodedLayout);
					//add the exit location and possible player tiles to the map
					validExits.put(new Point(houseWidth-1, y), getValidTiles(houseWidth-1, y, floodedLayout));
				}
			}
		}

		//if we didn't add at least 1 set of points to the map, then the room is invalid
		if(validExits.size() < 1)
		{
			return false;
		}
		//we have valid locations, pick one at random
		else
		{
			//get the keys from the map, aka exit locations
			List<Point> keys = new ArrayList<Point>(validExits.keySet());
			//pick one of the keys at random, that will be the exit location
			Point exitPoint = keys.get(rand.nextInt(keys.size()));

			//get all the player locations associated with that exit
			ArrayList<Point> playerPoints = validExits.get(exitPoint);
			
			//if there are no valid player points, get another exit location
			while (playerPoints.size() < 1)
			{
				exitPoint = keys.get(rand.nextInt(keys.size()));
				playerPoints = validExits.get(exitPoint);
			}
			//pick one at random, that will be the player location
			Point playerPoint = playerPoints.get(rand.nextInt(playerPoints.size()));

			//set globals
			player_X = playerPoint.x;
			player_Y = playerPoint.y;
			exit_X = exitPoint.x;
			exit_Y = exitPoint.y;

			//update the tile array with the exit and player
			tileLayout[exitPoint.x][exitPoint.y].setTile('E', 1, 1, exitPoint.x, exitPoint.y);
			//tileLayout[playerPoint.x][playerPoint.y].setTile('P', 1, 1, playerPoint.x, playerPoint.y);

			//add exit to tile obj list
			tileList.add(new Tile('E',1 ,1, exit_X, exit_Y));

			return true; //all went well!
		}
	}

	//flood fills a 2d char array to find connecting tiles
	public char[][] floodFill(int x, int y, int exitX, int exitY, char[][] floodedLayout)
	{
		if(floodedLayout[x][y] == '.' || floodedLayout[x][y] == 'F')
		{
			floodedLayout[x][y] = '-';
			floodFill(x+1, y, exitX, exitY, floodedLayout);
			floodFill(x-1, y, exitX, exitY, floodedLayout);
			floodFill(x, y+1, exitX, exitY, floodedLayout);
			floodFill(x, y-1, exitX, exitY, floodedLayout);
		}
		return floodedLayout;
	}

	//gets the valid player spawns for an exit
	public ArrayList<Point> getValidTiles(int exitX, int exitY, char[][] floodedLayout)
	{
		//stores valid player spawns
		ArrayList<Point> validTiles = new ArrayList<>();

		//go through char array
		for(int y = 0; y < houseLength; y++)
		{
			for(int x  = 0; x < houseLength; x++)
			{
				//if a tile is reachable, and is far enough away
				if((floodedLayout[x][y] == '-') && (distanceCheck(x,y,exitX,exitY)))
				{
					//add the point to the array list
					Point p = new Point(x,y);
					validTiles.add(p);
				}
			}
		}

		return validTiles;

	}

	//given two points, player and exit, check if they have a distance
	//of at least sight*2. If so, return true.
	public boolean distanceCheck(int x1, int y1, int x2, int y2)
	{
		int d = (int)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
		if(d > exitDistance)
			return true;
		else
			return false;
	}


	//***************************************************************************//
	//THE FOLLOWING METHODS ARE FOR DEBUGGING PURPOSES

	public void printTileList()
	{
		for(int i = 0; i < tileList.size(); i++)
		{
			System.out.print(tileList.get(i).getChar());
		}
	}

	public void printProperties()
	{
		System.out.println("House Number: " + houseNumber);
		System.out.println("  Width: " + houseWidth);
		System.out.println("  Length: " + houseLength);
		System.out.println("  Exit Wall: " + exitWall);
		System.out.println("  Exit X: " + exit_X + " Exit Y: " + exit_Y);
		System.out.println("  Player X: " + player_X + " Player Y: " + player_Y);
	}

	//convert the tile array to chars and print it to the console
	public void drawHouse(boolean showPlayer, boolean showZombies)
	{
		char[][] houseLayout = new char[100][100];
		for(int y = 0; y < houseLength; y++)
			for(int x = 0; x < houseWidth; x++)
				houseLayout[x][y] = tileLayout[x][y].getChar();
		
		houseLayout[player_X][player_Y] = 'P';
		
		for(int i = 0; i < zombieList.size(); i++)
		{
			houseLayout[zombieList.get(i).getPosition().x][zombieList.get(i).getPosition().y] = 'Z';
		}
		
		for(int y = 0; y < houseLength; y++)
		{
			for(int x = 0; x < houseWidth; x++)
				System.out.print(houseLayout[x][y]);
			System.out.print("\n");
		}
	}
}
