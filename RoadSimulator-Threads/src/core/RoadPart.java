package core;
import java.util.ArrayList;

import util.Constants;
import util.E_Direction;

public class RoadPart {

	/**
	 * the cell's row number
	 */
	private int row;
	
	/**
	 * the cell's column number
	 */
	private int col;
	
	/**
	 * the current entity in this cell
	 */
	private Entity currentEntity;
	
	/**
	 * list of all possible directions
	 */
	private ArrayList<E_Direction> possibleDirections;
	
	public RoadPart(int row, int col) {
		this.row = row;
		this.col = col;
		initDirections();
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Entity getCurrentEntity() {
		return currentEntity;
	}

	protected synchronized void setCurrentEntity(Entity currentEntity) {
		this.currentEntity = currentEntity;
	}

	public ArrayList<E_Direction> getPossibleDirections() {
		return possibleDirections;
	}

	/**
	 * initialise list adequate directions
	 */
	private void initDirections() {
		ArrayList<E_Direction> possibleDirections=new ArrayList<E_Direction>();
		
		if (col==Constants.UP_TO_DOWN)
			possibleDirections.add(E_Direction.DOWN);
		if (col==Constants.DOWN_TO_UP)
			possibleDirections.add(E_Direction.UP);
		if (row==Constants.LEFT_TO_RIGHT)
			possibleDirections.add(E_Direction.RIGHT);
		if (row==Constants.RIGHT_TO_LEFT)
			possibleDirections.add(E_Direction.LEFT);
		
		
		if (col==Constants.HUMAN_TO_DOWN && (row>=Constants.HUMAN_TO_LEFT && row<=Constants.HUMAN_TO_RIGHT))
			possibleDirections.add(E_Direction.DOWN);
		if (col==Constants.HUMAN_TO_UP && (row>=Constants.HUMAN_TO_LEFT && row<=Constants.HUMAN_TO_RIGHT))
			possibleDirections.add(E_Direction.UP);
		if (row==Constants.HUMAN_TO_RIGHT && (col>=Constants.HUMAN_TO_DOWN && col<=Constants.HUMAN_TO_UP))
			possibleDirections.add(E_Direction.RIGHT);
		if (row==Constants.HUMAN_TO_LEFT && (col>=Constants.HUMAN_TO_DOWN && col<=Constants.HUMAN_TO_UP))
			possibleDirections.add(E_Direction.LEFT);
		
		this.possibleDirections=possibleDirections; 
	}
	
	/**
	 * calculate the right direction between two cell
	 * @param front
	 * @param back
	 * @return the entity's direction
	 */
	public static E_Direction getDirectionBetweenTwoParts(RoadPart front, RoadPart back) {
		if (front.col==back.col && front.row>back.row)
			return E_Direction.DOWN;
		else if (front.col==back.col && front.row<back.row)
			return E_Direction.UP;
		else if (front.row==back.row && front.col>back.col)
			return E_Direction.RIGHT;
		else if (front.row==back.row && front.col<back.col)
			return E_Direction.LEFT;
		else return null;
	}

	@Override
	public String toString() {
		return "RoadPart [row=" + row + ", col=" + col + ", currentEntity=" + currentEntity + ", possibleDirections="
				+ possibleDirections + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoadPart other = (RoadPart) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
}
