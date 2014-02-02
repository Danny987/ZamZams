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
 * totalHouses variable is stored here.
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Level{

	private boolean debug = false;
	int error = 0;
	int totalHouses = 0;
	
	String numReg = "\\d+";
	String strReg = "[a-zA-Z]+";

	ArrayList<House> houseList = new ArrayList<>();

	public static void main(String[] args)
	{

	}

	public Level(String fileName)
	{	
		//parse xml, get NodeList
		NodeList list = parseXml(fileName);

		//count number of houses
		totalHouses = getNumOfHouses(list);

		for(int i = 0; i < totalHouses; i++)
		{
			//create house object
			House house = new House();
			houseList.add(house);

			//set house properties
			setProperties(list, i);

			//set house walls
			setWalls(list, i);

			//set house objects
			setObjects(list, i);

			//set house fire traps
			//house.setFireTraps(list, i);

			//set zombies
			//house.setZombies(list, i);

			//set player
			//house.setPlayer(list, i);
			
			if(error == 1) System.out.println("ERROR");
		}

	}

	private void setProperties(NodeList list, int houseNumber) 
	{

		int houseCount = 0; //current house in the node list

		for( int i = 0; i < list.getLength(); i++) //go through node list
		{
			Element element = (Element)list.item(i);
			String nodeName = element.getNodeName();

			//if the current node is a house and is the correct house number
			if((nodeName.equals("house")) && (houseCount == houseNumber))
			{	
				//Error Checking!!!
				//check for non-specified attributes
				NamedNodeMap houseAttr = element.getAttributes();
				for(int j = 0; j < houseAttr.getLength(); j++)
				{
					Node attr = houseAttr.item(j);
					if(!attr.getNodeName().equals("width") && !attr.getNodeName().equals("length") && !attr.getNodeName().equals("exit"))
					{
						error = 1;
						System.out.println("error");
						return;
					}
				}


				int width = -1; int length = -1; String exitWall = "";

				//get house attributes 
				if(element.getAttribute("width").matches(numReg)) //make sure attribute is a number
					width = Integer.parseInt(element.getAttribute("width"));
				if(element.getAttribute("length").matches(numReg))
					length = Integer.parseInt(element.getAttribute("length"));
				if(element.getAttribute("exit").matches(strReg))
					exitWall = element.getAttribute("exit");



				//Error Checking!!!
				//check for required attributes
				if(width == -1 || length == -1 || exitWall == "") error = 1;
				if(error ==1) return;

				//set the properties
				houseList.get(houseNumber).setProperties(houseNumber, width, length, exitWall);

				if(debug)System.out.println("House[" + houseNumber + "] attributes set.");

				houseCount++; //go to next house
			}
			else if(nodeName.equals("house"))//if its a house, but wrong house number
				houseCount++;//go to next house
		}
	}

	private void setWalls(NodeList list, int houseNumber) {
		int houseCount = 0;

		for( int i = 0; i < list.getLength(); i++) //go through node list
		{
			Element element = (Element)list.item(i);
			String nodeName = element.getNodeName();

			if(nodeName.equals("house"))
			{
				houseCount++;
			}
			else if((nodeName.equals("wall")) && (houseCount-1 == houseNumber))
			{
				//Error Checking!!!
				//check for non-specified attributes
				NamedNodeMap wallAttr = element.getAttributes();
				for(int j = 0; j < wallAttr.getLength(); j++)
				{
					Node attr = wallAttr.item(j);
					if(!attr.getNodeName().equals("x") && !attr.getNodeName().equals("x1") && !attr.getNodeName().equals("x2") &&
							!attr.getNodeName().equals("y") && !attr.getNodeName().equals("y1") && !attr.getNodeName().equals("y2"))
					{
						error = 1;
						System.out.println("error");
						return;
					}
				}



				int wallType = -1; //1 = horizontal, 2 = vertical
				int c1 = -1; //wall coordinate 1 (x/y)
				int c2 = -1; //wall coordinate 2 (x1/y1)
				int c3 = -1; //wall coordinate 3 (x2/y2)

				//check if wall is vertical or horizontal
				if(element.getAttribute("y") != "") //if there is a 'y' tag
				{
					wallType = 2;
					if(element.getAttribute("y").matches(numReg)) //make sure attribute is a number
						c1 = Integer.parseInt(element.getAttribute("y"));
					if(element.getAttribute("x1").matches(numReg))
						c2 = Integer.parseInt(element.getAttribute("x1"));
					if(element.getAttribute("x2").matches(numReg))
						c3 = Integer.parseInt(element.getAttribute("x2"));
				}
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
				else
				{
					error = 1;
				}

				//Error Checking!!!
				//check for required attributes
				if(c1 == -1 || c2 == -1 || c3 == -1) error = 1;
				if(error == 1) return;


				//create the wall
				houseList.get(houseNumber).createWall(wallType, c1, c2, c3);
				if(debug)System.out.println("wall created in house["+houseNumber+"]");
			}
		}
	}

	private void setObjects(NodeList list, int houseNumber) {
		int houseCount = 0;

		for( int i = 0; i < list.getLength(); i++) //go through node list
		{
			Element element = (Element)list.item(i);
			String nodeName = element.getNodeName();

			if(nodeName.equals("house"))
			{
				houseCount++;
			}
			else if((nodeName.equals("obj")) && (houseCount-1 == houseNumber))
			{
				//Error Checking!!!
				//check for non-specified attributes
				NamedNodeMap objAttr = element.getAttributes();
				for(int j = 0; j < objAttr.getLength(); j++)
				{
					Node attr = objAttr.item(j);
					if(!attr.getNodeName().equals("x1") && !attr.getNodeName().equals("x2") &&
							!attr.getNodeName().equals("y1") && !attr.getNodeName().equals("y2"))
					{
						error = 1;
						System.out.println("error");
						return;
					}
				}



				int x1 = -1; int x2 = -1; int y1 = -1; int y2 = -1;

				if(element.getAttribute("x1").matches(numReg)) //make sure attribute is a number
					x1 = Integer.parseInt(element.getAttribute("x1"));
				if(element.getAttribute("x2").matches(numReg))
					x2 = Integer.parseInt(element.getAttribute("x2"));
				if(element.getAttribute("y1").matches(numReg))
					y1 = Integer.parseInt(element.getAttribute("y1"));
				if(element.getAttribute("y2").matches(numReg))
					y2 = Integer.parseInt(element.getAttribute("y2"));


				//Error Checking!!!
				//check for required attributes
				if(x1 == -1 || x2 == -1 || y1 == -1 || y2 == -1) error = 1;
				if(error == 1) return;

				//create the object
				houseList.get(houseNumber).createObj(x1, x2, y1, y2);
				if(debug)System.out.println("object created in house["+houseNumber+"]");
			} 
		}
	}


	//reads XML file
	//generate a NodeList from the XML file provided.
	//return NULL on failure.
	private NodeList parseXml(String fileName)
	{
		//check if xml file exists
		try{
			// Open the xml file
			FileInputStream fstream = new FileInputStream(fileName);
			if(debug)System.out.println(fileName + " succesfuly loaded.");
		}catch (Exception e){//Catch exception if any
			if(debug)System.err.println("Error: " + fileName + " could not be loaded.");
			//System.err.println("Error: " + e.getMessage());
			return null;
		}

		File xmlFile = new File(fileName);

		try {
			// Create a new factory to create parsers
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			// Use the factory to create a parser (builder) and use it to parse the document.
			DocumentBuilder builder = dBF.newDocumentBuilder();

			Document doc = builder.parse(xmlFile);
			if(debug)System.out.println(xmlFile + " is well-formed!");
			NodeList list = doc.getElementsByTagName("*");
			return list;
		}
		catch (Exception e) {
			if(debug)System.out.println(xmlFile + " isn’t well-formed!");
			return null;
		}
	}

	//get the total number of houses in the config file
	//should return total number of houses
	private int getNumOfHouses(NodeList list)
	{
		int numOfHouses = 0; //total number of houses in config

		for( int i = 0; i < list.getLength(); i++) //go through each node
		{
			Element element = (Element)list.item(i);
			String nodeName  = element.getNodeName();

			if(nodeName.equals("house")) //if node is house, increment total
				numOfHouses++;
		}
		return numOfHouses;
	}

}
