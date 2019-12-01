package core;

import util.Constants;
import util.E_Direction;

public abstract class Entity extends Thread {
	private static Object lock = new Object();
	private static Object circle = new Object();
	private static Object circleTwo = new Object();
	/**
	 * The direction in which the entity will move
	 * Hint: in some places try to use - RoadPart.getDirectionBetweenTwoParts
	 */
	private E_Direction direction;
	
	/**
	 * Indicates whether the entity moved out of road
	 */
	private boolean isFinished;
	
	public E_Direction getDirection() {
		return direction;
	}
	
	protected void setDirection(E_Direction direction) {
		this.direction = direction;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	
	protected void setIsFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public abstract String getEntityName();
	
	protected abstract RoadPart getFirstCell();
	
	/**
	 * Moves the entity to given RoadPart (next cell in entity's direction) 
	 * @param toMove - the RoadPart to move in
	 */
	protected abstract void moveToFrontCell(RoadPart toMove);
	
	/**
	 * When the entity arrives to the end of the road - remove it from road
	 */
	protected abstract void removeEntityFromRoad();
	
	/**
	 * @return if front cell of entity arrived to the last cell of its road
	 */
	protected abstract boolean isAtEndOfRoad();
	
	/**
	 * @return if front cell of entity arrived to the penultimate cell of its road
	 */
	protected abstract boolean isLastButOne();
	
	/*
	#################################
	### HELPER METHODS - OPTIONAL ###
	#################################
	*/
	private void moveDown() {
		RoadPart nextCell = new RoadPart(this.getFirstCell().getRow()+1 , this.getFirstCell().getCol());
		synchronized (lock) { // prevents collision between car and human - different entities that inherit from this object
		if (Road.road[nextCell.getRow()][nextCell.getCol()].getCurrentEntity()==null && (nextCell.getRow()+1>19 || Road.road[nextCell.getRow()+1][nextCell.getCol()]==null || Road.road[nextCell.getRow()+1][nextCell.getCol()].getCurrentEntity()==null))
		moveToFrontCell(nextCell);
		}
	}
	
	private void moveLeft() {
		RoadPart nextCell = new RoadPart(this.getFirstCell().getRow() , this.getFirstCell().getCol()-1);
		synchronized (lock) { // same as above
		if (Road.road[nextCell.getRow()][nextCell.getCol()].getCurrentEntity()==null && (nextCell.getCol()-1<0 || Road.road[nextCell.getRow()][nextCell.getCol()-1]==null  || Road.road[nextCell.getRow()][nextCell.getCol()-1].getCurrentEntity()==null))
		moveToFrontCell(nextCell);
		}
	}
	
	private void moveRight() {
		RoadPart nextCell = new RoadPart(this.getFirstCell().getRow() , this.getFirstCell().getCol()+1);
		synchronized (lock) { // same as above
		if (Road.road[nextCell.getRow()][nextCell.getCol()].getCurrentEntity()==null && (nextCell.getCol()+1>19 || Road.road[nextCell.getRow()][nextCell.getCol()+1]==null || Road.road[nextCell.getRow()][nextCell.getCol()+1].getCurrentEntity()==null))
		moveToFrontCell(nextCell);
		}
	}
	
	private void moveUp() {
		RoadPart nextCell = new RoadPart(this.getFirstCell().getRow()-1, this.getFirstCell().getCol());
		synchronized (lock) { // same as above
		if (Road.road[nextCell.getRow()][nextCell.getCol()].getCurrentEntity()==null && (nextCell.getRow()-1<0 || Road.road[nextCell.getRow()-1][nextCell.getCol()]==null || Road.road[nextCell.getRow()-1][nextCell.getCol()].getCurrentEntity()==null))
		moveToFrontCell(nextCell);
		}
	}
	
	
	/**
	 * moves this entity one cell according to its direction
	 * @throws InterruptedException 
	 */
	private void moveEntity() throws InterruptedException {
		synchronized (circle) { // takes care of deadlock as in traffic jam at the junction that may occur with this particular JSON input (and other inputs too)
			while (this.equals(Road.road[Constants.RIGHT_TO_LEFT-1][Constants.UP_TO_DOWN].getCurrentEntity()) && Road.road[Constants.LEFT_TO_RIGHT][Constants.UP_TO_DOWN-1].getCurrentEntity()!=null){
				circle.wait();	
			}
		}
		synchronized (circleTwo) { // this + the one above take care of all possible traffic jams - no matter the JSON input
			while (this.equals(Road.road[Constants.LEFT_TO_RIGHT+1][Constants.DOWN_TO_UP].getCurrentEntity()) && Road.road[Constants.RIGHT_TO_LEFT][Constants.DOWN_TO_UP+1].getCurrentEntity()!=null){
				circleTwo.wait();	
			}
		}
		if (direction.equals(E_Direction.DOWN))
			moveDown();
		else if (direction.equals(E_Direction.UP))
			moveUp();
		else if (direction.equals(E_Direction.LEFT))
			moveLeft();
		else if (direction.equals(E_Direction.RIGHT))
			moveRight();
		synchronized (circle){
			if (Road.road[Constants.RIGHT_TO_LEFT-1][Constants.UP_TO_DOWN].getCurrentEntity()!=null && Road.road[Constants.LEFT_TO_RIGHT][Constants.UP_TO_DOWN-1].getCurrentEntity()==null && Road.road[Constants.LEFT_TO_RIGHT][Constants.UP_TO_DOWN-2].getCurrentEntity()==null && Road.road[Constants.LEFT_TO_RIGHT][Constants.UP_TO_DOWN-3].getCurrentEntity()==null)
			circle.notify();
		}
		synchronized (circleTwo){
			if (Road.road[Constants.LEFT_TO_RIGHT+1][Constants.DOWN_TO_UP].getCurrentEntity()!=null && Road.road[Constants.RIGHT_TO_LEFT][Constants.DOWN_TO_UP+1].getCurrentEntity()==null && Road.road[Constants.RIGHT_TO_LEFT][Constants.DOWN_TO_UP+2].getCurrentEntity()==null && Road.road[Constants.RIGHT_TO_LEFT][Constants.DOWN_TO_UP+3].getCurrentEntity()==null)
			circleTwo.notify();
		}
		if (this.isAtEndOfRoad()==true)
			this.removeEntityFromRoad();
		
		
		
	}
	
	public void run() {
		while (isFinished==false)
		{
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			moveEntity();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
}
