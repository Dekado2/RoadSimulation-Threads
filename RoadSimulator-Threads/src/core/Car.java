package core;

import util.Constants;
import util.E_Direction;

public class Car extends Entity {

	/**
	 * helper variable for naming the car objects
	 */
	private static int carsCounter = 1;
	
	/**
	 * car's ID
	 */
	private int ID;
	
	/**
	 * current cell of front part
	 */
	private RoadPart frontCell;

	/**
	 * current cell of back part
	 */
	private RoadPart backCell;
	
	public Car(RoadPart front, RoadPart back) {
		this.ID = carsCounter++;
		this.frontCell = front;
		this.backCell = back;
		setDirection(RoadPart.getDirectionBetweenTwoParts(frontCell, backCell));
		setIsFinished(false);
	}

	public RoadPart getFrontCell() {
		return frontCell;
	}

	protected void setFrontCell(RoadPart frontCell) {
		this.frontCell = frontCell;
	}

	public RoadPart getBackCell() {
		return backCell;
	}

	protected void setBackCell(RoadPart backCell) {
		this.backCell = backCell;
	}

	@Override
	public String getEntityName() {
		return String.valueOf(ID);
	}

	@Override
	protected RoadPart getFirstCell() {
		return frontCell;
	}

	@Override
	protected void moveToFrontCell(RoadPart toMove) {
		Road.road[backCell.getRow()][backCell.getCol()].setCurrentEntity(null);
		setBackCell(frontCell);
		Road.road[toMove.getRow()][toMove.getCol()].setCurrentEntity(this);
		setFrontCell(toMove);
	}

	@Override
	protected void removeEntityFromRoad() {
		setIsFinished(true);
		setDirection(null);
		Road.road[frontCell.getRow()][frontCell.getCol()].setCurrentEntity(null);
		Road.road[backCell.getRow()][backCell.getCol()].setCurrentEntity(null);
		frontCell=null;
	 	backCell=null;
	}

	@Override
	protected boolean isAtEndOfRoad() {
		if (this.getDirection().equals(E_Direction.DOWN))
	    	if (frontCell.getRow()==Constants.ROAD_DIMENSION-1 && frontCell.getCol()==Constants.UP_TO_DOWN)
	    		return true;
	    if (this.getDirection().equals(E_Direction.RIGHT))
	    	if (frontCell.getRow()==Constants.LEFT_TO_RIGHT && frontCell.getCol()==Constants.ROAD_DIMENSION-1)
	    		return true;
	    if (this.getDirection().equals(E_Direction.UP))
	    	if (frontCell.getRow()==0 && frontCell.getCol()==Constants.DOWN_TO_UP)
	    		return true;
	    if (this.getDirection().equals(E_Direction.LEFT))
	    	if (frontCell.getRow()==Constants.RIGHT_TO_LEFT && frontCell.getCol()==0)
	    		return true;
		return false;
	}

	@Override
	protected boolean isLastButOne() {
		if (this.getDirection().equals(E_Direction.DOWN))
	    	if (frontCell.getRow()==Constants.ROAD_DIMENSION-2 && frontCell.getCol()==Constants.UP_TO_DOWN)
	    		return true;
	    if (this.getDirection().equals(E_Direction.RIGHT))
	    	if (frontCell.getRow()==Constants.LEFT_TO_RIGHT && frontCell.getCol()==Constants.ROAD_DIMENSION-2)
	    		return true;
	    if (this.getDirection().equals(E_Direction.UP))
	    	if (frontCell.getRow()==1 && frontCell.getCol()==Constants.DOWN_TO_UP)
	    		return true;
	    if (this.getDirection().equals(E_Direction.LEFT))
	    	if (frontCell.getRow()==Constants.RIGHT_TO_LEFT && frontCell.getCol()==1)
	    		return true;
		return false;
	}

	@Override
	public String toString() {
		if (frontCell!=null && backCell!=null)
		return "Car [ID=" + ID + ", frontCell=" + "[[row=" + frontCell.getRow() + ", col=" + frontCell.getCol() + "]]" + ", " + "backCell=" + "[[row=" + backCell.getRow() + ", col=" + backCell.getCol() + "]]" + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else if (frontCell==null && backCell!=null)
			return "Car [ID=" + ID + ", frontCell=" + frontCell + ", " + "backCell=" + "[[row=" + backCell.getRow() + ", col=" + backCell.getCol() + "]]" + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else if (frontCell!=null && backCell==null)
			return "Car [ID=" + ID + ", frontCell=" + "[[row=" + frontCell.getRow() + ", col=" + frontCell.getCol() + "]]" + ", " + "backCell="  + backCell + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else // (frontCell==null && backCell==null)
			return "Car [ID=" + ID + ", frontCell=" + frontCell + ", backCell=" + backCell + ", direction=" + getDirection() + ", isFinished=" + isFinished() +"]";
		}	
}
