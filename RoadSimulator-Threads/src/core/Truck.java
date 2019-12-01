package core;

public class Truck extends Car {

	/**
	 * current cell of middle part
	 */
	private RoadPart middleCell;
	
	public Truck(RoadPart front, RoadPart middle, RoadPart back) {
		super(front,back);
		this.middleCell = middle;
		setDirection(RoadPart.getDirectionBetweenTwoParts(super.getFrontCell(), super.getBackCell()));
		setIsFinished(false);
	}
		
	public RoadPart getMiddleCell() {
		return middleCell;
	}

	@Override
	protected void moveToFrontCell(RoadPart toMove) {
		Road.road[super.getBackCell().getRow()][super.getBackCell().getCol()].setCurrentEntity(null);
		super.setBackCell(middleCell);
		middleCell=super.getFrontCell();
		Road.road[toMove.getRow()][toMove.getCol()].setCurrentEntity(this);
		super.setFrontCell(toMove);
	}
	
	@Override
	protected void removeEntityFromRoad() {
		setIsFinished(true);
		setDirection(null);
		Road.road[super.getFrontCell().getRow()][super.getFrontCell().getCol()].setCurrentEntity(null);
		Road.road[middleCell.getRow()][middleCell.getCol()].setCurrentEntity(null);
		Road.road[super.getBackCell().getRow()][super.getBackCell().getCol()].setCurrentEntity(null);
		super.setFrontCell(null);
		middleCell=null;
		super.setBackCell(null);
	}

	@Override
	public String toString() {
		if (super.getFrontCell()!=null && super.getBackCell()!=null && middleCell!=null)
		return "Truck [ID=" + getEntityName() + " , FrontCell=" + "[[row=" + getFrontCell().getRow() + ", col=" + getFrontCell().getCol() + "]]" + ", " + "MiddleCell=" + "[[row=" + middleCell.getRow()  + ", col=" + middleCell.getCol() + "]]" + ", " +  "BackCell="
			+ "[[row=" + getBackCell().getRow() + ", col=" + getBackCell().getCol() + "]]" + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else if (super.getFrontCell()==null && super.getBackCell()!=null && middleCell!=null)
			return "Truck [ID=" + getEntityName() + " , FrontCell=" + getFrontCell() + ", " + "MiddleCell=" + "[[row=" + middleCell.getRow()  + ", col=" + middleCell.getCol() + "]]" + ", " +  "BackCell="
			+ "[[row=" + getBackCell().getRow() + ", col=" + getBackCell().getCol() + "]]" + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else if (super.getFrontCell()!=null && super.getBackCell()==null && middleCell!=null)
			return "Truck [ID=" + getEntityName() + " , FrontCell=" + "[[row=" + getFrontCell().getRow() + ", col=" + getFrontCell().getCol() + "]]" + ", " + "MiddleCell=" + "[[row=" + middleCell.getRow()  + ", col=" + middleCell.getCol() + "]]" + ", " +  "BackCell="
			+ getBackCell() + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else if (super.getFrontCell()!=null && super.getBackCell()!=null && middleCell==null)
			return "Truck [ID=" + getEntityName() + " , FrontCell=" + "[[row=" + getFrontCell().getRow() + ", col=" + getFrontCell().getCol() + "]]" + ", " + "MiddleCell=" + middleCell + ", " +  "BackCell="
			+ "[[row=" + getBackCell().getRow() + ", col=" + getBackCell().getCol() + "]]" + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else if (super.getFrontCell()==null && super.getBackCell()==null && middleCell!=null)
			return "Truck [ID=" + getEntityName() + " , FrontCell=" + getFrontCell() + ", " + "MiddleCell=" + "[[row=" + middleCell.getRow()  + ", col=" + middleCell.getCol() + "]]" + ", " +  "BackCell="
			+ getBackCell() + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else if (super.getFrontCell()==null && super.getBackCell()!=null && middleCell==null)
			return "Truck [ID=" + getEntityName() + " , FrontCell=" + getFrontCell() + ", " + "MiddleCell=" + middleCell + ", " +  "BackCell="
			+ "[[row=" + getBackCell().getRow() + ", col=" + getBackCell().getCol() + "]]" + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else if (super.getFrontCell()!=null && super.getBackCell()==null && middleCell==null)
			return "Truck [ID=" + getEntityName() + " , FrontCell=" + "[[row=" + getFrontCell().getRow() + ", col=" + getFrontCell().getCol() + "]]" + ", " + "MiddleCell=" + middleCell + ", " +  "BackCell="
			+ getBackCell() + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
		else // (super.getFrontCell()==null && super.getBackCell()==null && middleCell==null)
			return "Truck [ID=" + getEntityName() + " , FrontCell="  + getFrontCell() + ", " + "MiddleCell=" +  middleCell  + ", "  +  "BackCell="
			+ getBackCell() + ", " + ", direction=" + getDirection() + ", isFinished=" + isFinished() + "]";
	}	
}