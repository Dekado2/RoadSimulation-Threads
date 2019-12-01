package core;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.Constants;
public class Road {

	/**
	 * 2d road, see Constants.ROAD_DIMENSION
	 */
	static RoadPart[][] road;
	
	/**
	 * list of all participated entities
	 */
	private static ArrayList<Entity> entities;

	public static void main(String[] args) {
		initRoad();
		initEntities();
		printRoad();
		System.out.println();
		Thread t = null;
		List<Thread> threads = new ArrayList<Thread>();
		for (Entity ent: entities)
		{
			 t = new Thread(ent);
			 threads.add(t);
		}
		for (int i=0;i<threads.size();i++)
			threads.get(i).start();
		while (isEntityOnRoad()==true)
		{
			printRoad();
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		printRoad();
	}

	/**
	 * initialise only usable cells, all the rest - null
	 */
	private static void initRoad() {
		road = new RoadPart[Constants.ROAD_DIMENSION][Constants.ROAD_DIMENSION];
		for (int row = 0; row < Constants.ROAD_DIMENSION; row++)
			for (int col = 0; col < Constants.ROAD_DIMENSION; col++)
				if (isPartOfRoad(row, col) || isHumanPart(row, col))
					road[row][col] = new RoadPart(row, col);
	}
	
	/**
	 * print the road accordingly to each cell status - must be printed every second
	 */
	private static void printRoad() {
		for (RoadPart[] row : road) {
			for (RoadPart cell : row) {
				if (cell == null)
					System.out.print(" ");
				else if (cell.getCurrentEntity() == null) {
					if (isPartOfRoad(cell.getRow(), cell.getCol()))
						System.out.print("0");
					else if (isHumanPart(cell.getRow(), cell.getCol()))
						System.out.print("O");
				}	else
					System.out.print(cell.getCurrentEntity().getEntityName());
			}
			System.out.println();
		}
	}
	
	/**
	 * @param row
	 * @param col
	 * @return if given cell belongs to humans road
	 */
	private static boolean isHumanPart(int row, int col) {
		if (((row>=Constants.HUMAN_TO_DOWN && row<=Constants.HUMAN_TO_UP) && (col==Constants.HUMAN_TO_DOWN || col==Constants.HUMAN_TO_UP)) || ((row == Constants.HUMAN_TO_DOWN || row==Constants.HUMAN_TO_UP) && (col>=Constants.HUMAN_TO_LEFT && col<=Constants.HUMAN_TO_RIGHT)))
			return true;
		return false;
	}

	/**
	 * @param row
	 * @param col
	 * @return if given cell belongs to vehicles road
	 */
	private static boolean isPartOfRoad(int row, int col) {
		if ((row>=Constants.RIGHT_TO_LEFT && row<=Constants.LEFT_TO_RIGHT && col>=0 && col<Constants.ROAD_DIMENSION) || (col>=Constants.UP_TO_DOWN && col<=Constants.DOWN_TO_UP && row>=0 && row<Constants.ROAD_DIMENSION))
			return true;
		return false;
	}

	/**
	 * initializes entities. reads data from entities json file.
	 */
//	@SuppressWarnings("unchecked")
	public static void initEntities() {
		entities = new ArrayList<>();
		JSONParser parser = new JSONParser();
		int i=0;
		  try
		  {
			  Object obj = parser.parse(new FileReader("resources/entities.json"));
			  JSONArray jsonArray = (JSONArray) obj;
			  JSONObject jsonObject=null;
			  while (i<jsonArray.size())
			  {
			     jsonObject = (JSONObject) jsonArray.get(i++);
			     String type = (String) jsonObject.get("type");
			     JSONArray frontCoords = (JSONArray) jsonObject.get("front");
			     Entity ent=null;
			     if (type.equals("Human"))
			    	 ent = new Human(new RoadPart(Integer.parseInt(frontCoords.get(0).toString()), Integer.parseInt(frontCoords.get(1).toString())));
			     else if (type.equals("Car") || type.equals("Truck"))
			     {
			     JSONArray backCoords=null;
			     backCoords = (JSONArray) jsonObject.get("back");
			     if (type.equals("Car")){
			    	 ent = new Car(new RoadPart(Integer.parseInt(frontCoords.get(0).toString()), Integer.parseInt(frontCoords.get(1).toString())), new RoadPart(Integer.parseInt(backCoords.get(0).toString()), Integer.parseInt(backCoords.get(1).toString())));
			    	 road[((Car)ent).getBackCell().getRow()][((Car)ent).getBackCell().getCol()] = ((Car)ent).getBackCell();
				     road[((Car)ent).getBackCell().getRow()][((Car)ent).getBackCell().getCol()].setCurrentEntity(ent);
			     }
			     else if (type.equals("Truck"))
			       {
			          JSONArray middleCoords=null;
			          middleCoords = (JSONArray) jsonObject.get("middle");
			          ent = new Truck(new RoadPart(Integer.parseInt(frontCoords.get(0).toString()), Integer.parseInt(frontCoords.get(1).toString())), new RoadPart(Integer.parseInt(middleCoords.get(0).toString()), Integer.parseInt(middleCoords.get(1).toString())), new RoadPart(Integer.parseInt(backCoords.get(0).toString()), Integer.parseInt(backCoords.get(1).toString())));
			          road[((Truck)ent).getBackCell().getRow()][((Truck)ent).getBackCell().getCol()] = ((Truck)ent).getBackCell();
					  road[((Truck)ent).getBackCell().getRow()][((Truck)ent).getBackCell().getCol()].setCurrentEntity(ent);
					  road[((Truck)ent).getMiddleCell().getRow()][((Truck)ent).getMiddleCell().getCol()] = ((Truck)ent).getMiddleCell();
					  road[((Truck)ent).getMiddleCell().getRow()][((Truck)ent).getMiddleCell().getCol()].setCurrentEntity(ent);
			       }
			     }		      
			     road[ent.getFirstCell().getRow()][ent.getFirstCell().getCol()] = ent.getFirstCell();
			     road[ent.getFirstCell().getRow()][ent.getFirstCell().getCol()].setCurrentEntity(ent);
			     entities.add(ent);
			  }
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		System.out.println(LocalTime.now() + " all data fetched from file:\n\n");
	}

	/**
	 * @return if there is at least one entity somewhere on the road
	 */
	private static boolean isEntityOnRoad() {
		for (RoadPart[] row : road)
			for (RoadPart cell : row)
				if (cell!=null && cell.getCurrentEntity()!=null)
					return true;
		return false;
	}
	
}
