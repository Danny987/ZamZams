/*
 * @author Daniel Gomez
 * Lab 1
 * 1/29/14
 * 
 * Group Members: Mario, Marcos, James, Ramon
 * 
 * Story: This class loads an xml file, counts how many 
 * houses are in that file, and creates house objects 
 * based on their parameters.
 * 
 * House objects are stored here.
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Level{
	//error variables
	int errorFlag = 0;
	String errorString = "";

	//list of house objects
	ArrayList<House> houseList = new ArrayList<>();

	//regex
	String numReg = "\\d+";
	String floatReg = "^([+-]?\\d*\\.?\\d*)$";
	String strReg = "[a-zA-Z]+";
	String zomReg = "(\\d+)(%)(:)(\\d+)(%)";

	public Level(String fileName)
	{
		//parse XML 
		//return NodeList on valid 
		//null on in-valid
		NodeList xmlData = parseXml(fileName);

		if(xmlData != null) //if xml was valid
		{
			createHouses(xmlData);
			validateHouses();
		}

		//report errors if any
		if(errorFlag == 1)
			System.out.println("Error: " + errorString);

	}

	//set the player, zombie, and exit positions in each house
	private void validateHouses() {
		
		//go through each house
		for(int i = 0; i < houseList.size(); i++)
		{
			//set zombie positions
			if(!houseList.get(i).setZombiePositions())
			{
				errorFlag = 1;
				errorString = "Couoldn't place 'zombie' in house " + i + ".";
				return;
			}
		}

	}

	//Goes through the xml data
	//creates house objects and adds them to houseList
	//sets the house properties based on tags read
	private void createHouses(NodeList xmlData)
	{
		int houseCount = 0; //number of houses read
		int playerCount = 0;//how many player tags have been read
		//should be equal to houseCount

		//go through XML data
		for (int i = 0; i < xmlData.getLength(); i++)
		{
			Element element = (Element)xmlData.item(i); //get xml element
			String nodeName = element.getNodeName(); //get element name

			if(nodeName.equals("house")) //if a house tag is read
			{

				House house = new House(); //create a house
				houseList.add(house); //add to house list
				houseCount++; //increment counter

				//set house attributes
				readHouse(element, houseCount-1); //houseCount-1 = house number
			}

			if(nodeName.equals("wall"))
				if(!readWall(element, houseCount-1))
					return; //error getting wall

			if(nodeName.equals("obj"))
				if(!readObj(element, houseCount-1))
					return; //error getting obj

			if(nodeName.equals("firetrap"))
				if(!readFiretrap(element, houseCount-1))
					return; //error getting firetrap

			if(nodeName.equals("zombie"))
				if(!readZombie(element, houseCount-1))
					return; //error getting zombie

			if(nodeName.equals("player"))
			{
				playerCount++;
				if(playerCount != houseCount)
				{
					errorFlag = 1;
					errorString = "Invalid number of 'player' tags in house " + (houseCount-1) + ".";
					return;
				}
				if(!readPlayer(element, houseCount-1))
					return; //error getting player
			}

		}
	}

	//Reads player tag and sets values in house object
	private boolean readPlayer(Element element, int houseNumber) {
		//declare variables
		int sight = -1;
		int hear = -1;
		float speed = -1;
		int stamina = -1;
		float regen = -1;
		
		//check for non-specified attributes
		NamedNodeMap playerAttr = element.getAttributes();
		for(int i = 0; i < playerAttr.getLength(); i++)
		{
			Node attr = playerAttr.item(i); //get attribute

			//check if attribute is part of specified attributes
			if(!attr.getNodeName().equals("sight") && !attr.getNodeName().equals("hear") &&
					!attr.getNodeName().equals("speed") && !attr.getNodeName().equals("stamina") &&
					!attr.getNodeName().equals("regen"))
			{
				errorFlag = 1;
				errorString = "Unspecified attribute for 'player' in house " + houseNumber + ".";
				return false;
			}
		}

		//set values if attributes are valid
		if(element.getAttribute("sight").matches(numReg)) 
			sight = Integer.parseInt(element.getAttribute("sight"));
		if(element.getAttribute("hear").matches(numReg))
			hear = Integer.parseInt(element.getAttribute("hear"));
		if(element.getAttribute("speed").matches(floatReg))
			speed = Float.parseFloat(element.getAttribute("speed"));
		if(element.getAttribute("stamina").matches(numReg))
			stamina = Integer.parseInt(element.getAttribute("stamina"));
		if(element.getAttribute("regen").matches(floatReg))
			regen = Float.parseFloat(element.getAttribute("regen"));

		//check for missing required attributes
		if(sight < 0 || hear < 0 || speed < 0 || regen < 0)
		{
			errorFlag = 1;
			errorString = "Invalid or missing 'player' attribute in house " + houseNumber + ".";
			return false;
		}
		

		//if we got here, create player
		/*if(!houseList.get(houseNumber).createPlayer(sight, hear, speed, stamina, regen)
		{
			errorFlag = 1;
			errorString = "Could not place 'player' in house " + houseNumber + ".";
			return false;
		}*/
		return true;
	}

	//Reads zombie tag and sets values in house object
	private boolean readZombie(Element element, int houseNumber) {
		//declare variables
		int x = -1;
		int y = -1;
		int smell = -1;
		float speed = -1;
		int walkType = -1;
		int probA = -1;
		int probB = -1;
		String probStr = "";

		//check for non-specified attributes
		NamedNodeMap zombieAttr = element.getAttributes();
		for(int i = 0; i < zombieAttr.getLength(); i++)
		{
			Node attr = zombieAttr.item(i); //get attribute

			//check if attribute is part of specified attributes
			if(!attr.getNodeName().equals("x") && !attr.getNodeName().equals("y") &&
					!attr.getNodeName().equals("smell") && !attr.getNodeName().equals("speed") &&
					!attr.getNodeName().equals("randomwalk") && !attr.getNodeName().equals("linewalk"))
			{
				errorFlag = 1;
				errorString = "Unspecified attribute for 'zombie' in house " + houseNumber + ".";
				return false;
			}
		}

		//set values if attributes are valid
		//make sure there isn't both an 'randomwalk' and 'linewalk' attribute
		if(element.getAttribute("randomwalk") != "" && element.getAttribute("linewalk") != "")
		{
			errorFlag = 1;
			errorString = "'randomwalk' and 'linewalk' attributes found in 'zombie' in house " + houseNumber + ".";
			return false;
		}
		//check if wall is vertical
		else if(element.getAttribute("randomwalk") != "") //if there is a 'y' tag
		{
			walkType = 1;
			if(Pattern.matches(zomReg, element.getAttribute("randomwalk")))
				probStr = element.getAttribute("randomwalk");
		}
		//check if wall is horizontal
		else if((element.getAttribute("linewalk") != "")) //if there is an 'x' tag
		{
			walkType = 2;
			if(Pattern.matches(zomReg, element.getAttribute("linewalk")))
				probStr = element.getAttribute("linewalk");
		}

		//set values if attributes are valid
		if(element.getAttribute("x").matches(numReg)) 
			x = Integer.parseInt(element.getAttribute("x"));
		if(element.getAttribute("y").matches(numReg))
			y = Integer.parseInt(element.getAttribute("y"));
		if(element.getAttribute("smell").matches(numReg))
			smell = Integer.parseInt(element.getAttribute("smell"));
		if(element.getAttribute("speed").matches(floatReg))
			speed = Float.parseFloat(element.getAttribute("speed"));

		//seperate walk probabilities at ':'
		String[] probArr = probStr.split(":");
		if(probArr.length != 2) //if it doesn't result in 2 strings, error
		{
			errorFlag = 1;
			errorString = "Invalid 'randomwalk' or 'linewalk' attribute for 'zombie' in house " + houseNumber + ".";
			return false;
		}
		//remove all chars but digits
		probArr[0] = probArr[0].replaceAll("\\D+", "");
		probArr[1] = probArr[1].replaceAll("\\D+", "");
		
		//set probabilities if they are valid
		if((Integer.parseInt(probArr[0]) >= 0) && (Integer.parseInt(probArr[0]) <= 100))
			probA = Integer.parseInt(probArr[0]);
		if((Integer.parseInt(probArr[1]) >= 0) && (Integer.parseInt(probArr[1]) <= 100))
			probB = Integer.parseInt(probArr[1]);
		
		//if the 'x' and 'y' were set, make sure none or both were set
		if((x == -1 && y != -1) || (x != -1 && y == 1))
		{
			errorFlag = 1;
			errorString = "Invalid 'x' or 'y' attribute 'zombie' in house " + houseNumber + ".";
			return false;
		}

		//check for missing required attributes
		if(smell < 0 || speed < 0 || walkType < 0 || probA < 0 || probB < 0)
		{
			errorFlag = 1;
			errorString = "Invalid or missing 'zombie' attribute in house " + houseNumber + ".";
			return false;
		}

		//if we got here, create zombie
		houseList.get(houseNumber).createZombie(x, y, smell, speed, walkType, probA, probB);
		
		return true;
	}

	//Reads firetrap tag and sets values in house object
	private boolean readFiretrap(Element element, int houseNumber) {
		//declare variables
		int x = -1;
		int y = -1;

		//check for non-specified attributes
		NamedNodeMap trapAttr = element.getAttributes();
		for(int i = 0; i < trapAttr.getLength(); i++)
		{
			Node attr = trapAttr.item(i); //get attribute

			//check if attribute is part of specified attributes
			if(!attr.getNodeName().equals("x") && !attr.getNodeName().equals("y"))
			{
				errorFlag = 1;
				errorString = "Unspecified attribute for 'firetrap' in house " + houseNumber + ".";
				return false;
			}
		}

		//set values if attributes are valid
		if(element.getAttribute("x").matches(numReg)) 
			x = Integer.parseInt(element.getAttribute("x"));
		if(element.getAttribute("y").matches(numReg))
			y = Integer.parseInt(element.getAttribute("y"));

		//check for missing required attributes
		if(x < 1 || y < 1)
		{
			errorFlag = 1;
			errorString = "Invalid or missing 'firetrap' attribute in house " + houseNumber + ".";
			return false;
		}

		//if we got here, create firetrap
		if(!houseList.get(houseNumber).createFirettrap(x, y))
		{
			errorFlag = 1;
			errorString = "Could not place 'firetrap' in house " + houseNumber + ".";
			return false;
		}
		return true;
	}

	//Reads obj tag and sets values in house object
	private boolean readObj(Element element, int houseNumber) {
		//declare variables
		int x1 = -1; //default = -1
		int x2 = -1; //used to check which
		int y1 = -1; //variables don't get set
		int y2 = -1;

		//check for non-specified attributes
		NamedNodeMap objAttr = element.getAttributes();
		for(int i = 0; i < objAttr.getLength(); i++)
		{
			Node attr = objAttr.item(i); //get attribute

			//check if attribute is part of specified attributes
			if(!attr.getNodeName().equals("x1") && !attr.getNodeName().equals("x2") &&
					!attr.getNodeName().equals("y1") && !attr.getNodeName().equals("y2"))
			{
				errorFlag = 1;
				errorString = "Unspecified attribute for 'obj' in house " + houseNumber + ".";
				return false;
			}
		}

		//set values if attributes are valid
		if(element.getAttribute("x1").matches(numReg)) 
			x1 = Integer.parseInt(element.getAttribute("x1"));
		if(element.getAttribute("x2").matches(numReg))
			x2 = Integer.parseInt(element.getAttribute("x2"));
		if(element.getAttribute("y1").matches(numReg))
			y1 = Integer.parseInt(element.getAttribute("y1"));
		if(element.getAttribute("y2").matches(numReg))
			y2 = Integer.parseInt(element.getAttribute("y2"));

		//check for missing required attributes
		if(x1 < 1 || x2 < 1 || y1 < 1 || y2 < 1)
		{
			errorFlag = 1;
			errorString = "Invalid or missing 'obj' attribute in house " + houseNumber + ".";
			return false;
		}
		

		//if we got here, create the object
		if(!houseList.get(houseNumber).createObj(x1, x2, y1, y2))
		{
			errorFlag = 1;
			errorString = "Could not place 'obj' in house " + houseNumber + ".";
			return false;
		}
		return true;
	}

	//Reads wall tag and sets values in house object
	private boolean readWall(Element element, int houseNumber) {
		//declare variables
		int wallType = -1; //1 = horizontal, 2 = vertical
		int c1 = -1; //wall coordinate 1 (x/y)
		int c2 = -1; //wall coordinate 2 (x1/y1)
		int c3 = -1; //wall coordinate 3 (x2/y2)

		//check for non-specified attributes
		NamedNodeMap wallAttr = element.getAttributes();
		for(int j = 0; j < wallAttr.getLength(); j++)
		{
			Node attr = wallAttr.item(j);
			if(!attr.getNodeName().equals("x") && !attr.getNodeName().equals("x1") && !attr.getNodeName().equals("x2") &&
					!attr.getNodeName().equals("y") && !attr.getNodeName().equals("y1") && !attr.getNodeName().equals("y2"))
			{
				errorFlag = 1;
				errorString = "Unspecified attribute for 'wall' in house " + houseNumber + ".";
				return false;
			}
		}

		//set values if attributes are valid
		//make sure there isn't both an 'x' and 'y' attribute
		if(element.getAttribute("y") != "" && element.getAttribute("x") != "")
		{
			errorFlag = 1;
			errorString = "'x' and 'y' found in 'wall' attribute in house " + houseNumber + ".";
			return false;
		}
		//check if wall is vertical
		else if(element.getAttribute("y") != "") //if there is a 'y' tag
		{
			wallType = 2;
			if(element.getAttribute("y").matches(numReg)) //make sure attribute is a number
				c1 = Integer.parseInt(element.getAttribute("y"));
			if(element.getAttribute("x1").matches(numReg))
				c2 = Integer.parseInt(element.getAttribute("x1"));
			if(element.getAttribute("x2").matches(numReg))
				c3 = Integer.parseInt(element.getAttribute("x2"));
		}
		//check if wall is horizontal
		else if((element.getAttribute("x") != "")) //if there is an 'x' tag
		{
			wallType = 1;
			if(element.getAttribute("x").matches(numReg))
				c1 = Integer.parseInt(element.getAttribute("x"));
			if(element.getAttribute("y1").matches(numReg))
				c2 = Integer.parseInt(element.getAttribute("y1"));
			if(element.getAttribute("y2").matches(numReg))
				c3 = Integer.parseInt(element.getAttribute("y2"));
		}
			
		//check for missing required attributes
		if(c1 < 1 || c2 < 1 || c3 < 1)
		{
			errorFlag = 1;
			errorString = "Invalid or missing 'wall' attribute in house " + houseNumber + ".";
			return false;
		}

		//if we got here, create wall
		if(!houseList.get(houseNumber).createWall(wallType, c1, c2, c3))
		{
			errorFlag = 1;
			errorString = "Could not place 'wall' in house " + houseNumber + ".";
			return false;
		}
		return true;
	}

	//Reads house tag and sets values in house object
	private boolean readHouse(Element element, int houseNumber)
	{
		//declare variables
		int width = -1;
		int length = -1;
		String exitWall = "";

		//check for non-specified attributes
		NamedNodeMap houseAttr = element.getAttributes();
		for(int j = 0; j < houseAttr.getLength(); j++)
		{
			Node attr = houseAttr.item(j);
			if(!attr.getNodeName().equals("width") 
					&& !attr.getNodeName().equals("length") 
					&& !attr.getNodeName().equals("exit"))
			{
				errorFlag = 1;
				errorString = "Unspecified attribute for 'house' in house " + houseNumber + ".";
				return false;
			}
		}

		//set values if attributes are valid
		if(element.getAttribute("width").matches(numReg))
			width = Integer.parseInt(element.getAttribute("width"));
		if(element.getAttribute("length").matches(numReg))
			length = Integer.parseInt(element.getAttribute("length"));
		if(element.getAttribute("exit").matches(strReg))
			exitWall = element.getAttribute("exit");

		//check for missing required attributes
		if(width < 1 || length < 1 || exitWall =="")
		{
			errorFlag = 1;
			errorString = "Invalid or missing 'house' attribute in house " + houseNumber + ".";
			return false;
		}

		//if we got here, set house properties
		houseList.get(houseNumber).setProperties(houseNumber, width, length, exitWall);
		return true;
	}

	//reads XML file
	//generates a NodeList from the XML file provided.
	//return NULL on failure.
	private NodeList parseXml(String fileName)
	{
		//check if xml file exists
		FileInputStream fstream = null;
		try{
			// Open the xml file
			fstream = new FileInputStream(fileName);
		}catch (Exception e){//Catch exception if any
			errorFlag = 1;
			errorString = "Config file could not be loaded.";
			return null;
		}finally
		{
			try
			{
				if(fstream != null)
					fstream.close();
			}
			catch (IOException e)
			{
			}
		}

		File xmlFile = new File(fileName);

		try {
			// Create a new factory to create parsers
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			// Use the factory to create a parser (builder) and use it to parse the document.
			DocumentBuilder builder = dBF.newDocumentBuilder();

			Document doc = builder.parse(xmlFile);
			NodeList list = doc.getElementsByTagName("*");
			return list;
		}
		catch (Exception e) {
			errorFlag = 1;
			errorString = "Config file is not well-formed.";
			return null;
		}
	}
}
