package core;

import util.Constants;
import util.E_Direction;

public class Human extends Entity {

	/**
	 * helper variable for naming the human objects
	 */
	private static Character humanCounter = 'A';
	
	/**
	 * human's name
	 */
	private Character name;
	
	/**
	 * the current cell
	 */
	private RoadPart currCell;
	
	public Human(RoadPart curr) {
		this.name = humanCounter++;
		this.currCell = curr;
		setDirection(getHumanDirection());
		setIsFinished(false);
	}
	
	/**
	 * @return human's direction according to the relevant road part
	 */
	private E_Direction getHumanDirection() {
		if (currCell.getRow()==Constants.HUMAN_TO_LEFT && currCell.getCol()==Constants.HUMAN_TO_DOWN)
			return E_Direction.DOWN;
		else if (currCell.getRow()==Constants.HUMAN_TO_RIGHT && currCell.getCol()==Constants.HUMAN_TO_UP)
			return E_Direction.UP;
		else if (currCell.getRow()==Constants.HUMAN_TO_RIGHT && currCell.getCol()==Constants.HUMAN_TO_DOWN)
			return E_Direction.RIGHT;
		else if (currCell.getRow()==Constants.HUMAN_TO_LEFT && currCell.getCol()==Constants.HUMAN_TO_UP)
			return E_Direction.LEFT;
		else if ((currCell.getRow()>Constants.HUMAN_TO_LEFT && currCell.getRow()<Constants.HUMAN_TO_RIGHT) && currCell.getCol()==Constants.HUMAN_TO_DOWN)
			return E_Direction.DOWN;
		else if ((currCell.getRow()>Constants.HUMAN_TO_LEFT && currCell.getRow()<Constants.HUMAN_TO_RIGHT) && currCell.getCol()==Constants.HUMAN_TO_UP)
			return E_Direction.UP;
		else if ((currCell.getCol()>Constants.HUMAN_TO_DOWN && currCell.getCol()<Constants.HUMAN_TO_UP) && currCell.getRow()==Constants.HUMAN_TO_LEFT)
			return E_Direction.LEFT;
		else if ((currCell.getCol()>Constants.HUMAN_TO_DOWN && currCell.getCol()<Constants.HUMAN_TO_UP) && currCell.getRow()==Constants.HUMAN_TO_RIGHT)
			return E_Direction.RIGHT;
		else return null;
	}

	@Override
	public String getEntityName() {
		return String.valueOf(name);
	}

	@Override
	protected RoadPart getFirstCell() {
		return currCell;
	}

	@Override
	protected void moveToFrontCell(RoadPart toMove) {
		Road.road[currCell.getRow()][currCell.getCol()].setCurrentEntity(null);
		Road.road[toMove.getRow()][toMove.getCol()].setCurrentEntity(this);
		currCell=toMove;
	}

	@Override
	protected void removeEntityFromRoad() {
		    setIsFinished(true);
		    setDirection(null);
	        Road.road[currCell.getRow()][currCell.getCol()].setCurrentEntity(null);
		    currCell=null;
	}

	@Override
	protected boolean isAtEndOfRoad() {
		if (this.getDirection().equals(E_Direction.DOWN))
	    	if (currCell.getRow()==Constants.HUMAN_TO_RIGHT && currCell.getCol()==Constants.HUMAN_TO_DOWN)
	    		return true;
	    if (this.getDirection().equals(E_Direction.RIGHT))
	    	if (currCell.getRow()==Constants.HUMAN_TO_RIGHT && currCell.getCol()==Constants.HUMAN_TO_UP)
	    		return true;
	    if (this.getDirection().equals(E_Direction.UP))
	    	if (currCell.getRow()==Constants.HUMAN_TO_LEFT && currCell.getCol()==Constants.HUMAN_TO_UP)
	    		return true;
	    if (this.getDirection().equals(E_Direction.LEFT))
	    	if (currCell.getRow()==Constants.HUMAN_TO_LEFT && currCell.getCol()==Constants.HUMAN_TO_DOWN)
	    		return true;
		return false;
	}

	@Override
	protected boolean isLastButOne() {
		if (this.getDirection().equals(E_Direction.DOWN))
	    	if (currCell.getRow()==Constants.HUMAN_TO_RIGHT-1 && currCell.getCol()==Constants.HUMAN_TO_DOWN)
	    		return true;
	    if (this.getDirection().equals(E_Direction.RIGHT))
	    	if (currCell.getRow()==Constants.HUMAN_TO_RIGHT && currCell.getCol()==Constants.HUMAN_TO_UP-1)
	    		return true;
	    if (this.getDirection().equals(E_Direction.UP))
	    	if (currCell.getRow()==Constants.HUMAN_TO_LEFT+1 && currCell.getCol()==Constants.HUMAN_TO_UP)
	    		return true;
	    if (this.getDirection().equals(E_Direction.LEFT))
	    	if (currCell.getRow()==Constants.HUMAN_TO_LEFT && currCell.getCol()==Constants.HUMAN_TO_DOWN+1)
	    		return true;
		return false;
	}
	
	@Override
	public String toString() {
		if (currCell!=null)
		return "Human [name=" + name + ", currCell=" + "[[row=" + currCell.getRow() + ", col=" + currCell.getCol() +  "]]" + ", direction=" + getDirection() +  ", isFinished=" + isFinished() + "]";
		else
			return "Human [name=" + name + ", currCell=" + currCell + ", direction=" + getDirection() +  ", isFinished=" + isFinished() + "]";
			
	} 
	
}
