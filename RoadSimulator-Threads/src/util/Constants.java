package util;

public class Constants {

	public static final int ROAD_DIMENSION = 20;
	// Car area
	public static final int UP_TO_DOWN = ROAD_DIMENSION/2 - 1;
	public static final int DOWN_TO_UP = ROAD_DIMENSION/2;
	public static final int LEFT_TO_RIGHT = ROAD_DIMENSION/2;
	public static final int RIGHT_TO_LEFT = ROAD_DIMENSION/2 - 1;
	// Human area
	public static final int HUMAN_TO_LEFT = RIGHT_TO_LEFT - 2;
	public static final int HUMAN_TO_DOWN = UP_TO_DOWN - 2;
	public static final int HUMAN_TO_RIGHT = LEFT_TO_RIGHT + 2;
	public static final int HUMAN_TO_UP = DOWN_TO_UP + 2;
	
}
