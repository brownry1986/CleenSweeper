package com.sweeper.hardware.floorplan;

import com.sweeper.exceptions.InvalidXmlParameterException;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;

public final class FloorCellBuilder {

	private FloorCellBuilder() {}
	
	public static FloorCell floorCellCreate(int x, int y, SurfaceType surfaceType, int dirtUnits,
											boolean chargingStation, ObstacleType upObstacle, ObstacleType downObstacle,
											ObstacleType rightObstacle, ObstacleType leftObstacle) {
		return new FloorCell(x, y, surfaceType, dirtUnits, chargingStation, upObstacle, downObstacle, rightObstacle, leftObstacle);
	}

	public static FloorCell floorCellCreateFromXml(int xs, int ys, int ss, String ps, int ds, boolean cs) throws InvalidXmlParameterException {
		SurfaceType surfaceType;
		ObstacleType upObstacle;
		ObstacleType downObstacle;
		ObstacleType leftObstacle;
		ObstacleType rightObstacle;
		
		//Determine the floor type from the ss integer
		surfaceType = getSurfaceTypeFromXml(ss);
			
		//Parse the ps string to determine the obstructions in the different directions
		//ps should be a string of length 4 with the below 
		//0 = unknown, 1 = open, 2 = obstacle, 4 = stairs
		String pattern = "(0|1|2|4)(0|1|2|4)(0|1|2|4)(0|1|2|4)";
		if (ps.matches(pattern)) {
			rightObstacle = getObstacleTypeFromXml(ps.charAt(0));
			leftObstacle = getObstacleTypeFromXml(ps.charAt(1));
			upObstacle = getObstacleTypeFromXml(ps.charAt(2));
			downObstacle = getObstacleTypeFromXml(ps.charAt(3));
		} else {
			throw new InvalidXmlParameterException("Obstacle String (ps) did not have appropriate values. " +
													"Expected a string of length 4 with individual values of " +
													"0, 1, 2, or 4 for the individual characters");
		}
			return floorCellCreate(xs, ys, surfaceType, ds, cs, upObstacle, downObstacle, rightObstacle, leftObstacle);
		
	}
	
	private static ObstacleType getObstacleTypeFromXml(char input) {
		final char open = '1';
		final char obstacle = '2';
		final char stairs = '4';
		
		switch (input) {
			case open: return ObstacleType.OPEN;
			case obstacle: return ObstacleType.OBSTACLE;
			case stairs: return ObstacleType.STAIRS;
			default: return ObstacleType.UNKNOWN;
		}
	}
	
	private static SurfaceType getSurfaceTypeFromXml(int input) throws InvalidXmlParameterException{
		final int bareFloor = 1;
		final int lowPileCarpet = 2;
		final int highPileCarpet = 4;
		
		switch (input) {
		case bareFloor: return SurfaceType.BARE_FLOOR;
		case lowPileCarpet: return SurfaceType.LOW_PILE_CARPET;
		case highPileCarpet: return SurfaceType.HIGH_PILE_CARPET;
		default: throw new InvalidXmlParameterException("Floor type (ss) not recognized");
		}
	}
	
	
}
