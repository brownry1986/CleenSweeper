package com.sweeper.hardware.floorplan;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;

//This class expects clean inputs
public class FloorCell {
	
	//Indicates the x position of this cell in the floor plan
	private int x;
	
	//Indicates the y position of this cell in the floor plan
	private int y;
	
	//Enumerated type for what type of flooring is in this cell (BARE_FLOOR, LOW_PILE_CARPET, HIGH_PILE_CARPET)
	private SurfaceType surfaceType;
	
	//The amount of dirt in the cell
	private int dirtUnits;
	
	//Determines if this cell contains a charging station
	private boolean chargingStation;
	
	//up is always considered a positive movement in the y direction
	private ObstacleType upObstacle;
	
	//down is always considered a negative movement in the y direction.
	private ObstacleType downObstacle;
	
	//right is always considered a positive movement in the x direction
	private ObstacleType rightObstacle;
	
	//left is always considered a negative movement in the x direction
	private ObstacleType leftObstacle;

	FloorCell(int x, int y, SurfaceType surfaceType, int dirtUnits,
			boolean chargingStation, ObstacleType upObstacle, ObstacleType downObstacle,
			ObstacleType rightObstacle, ObstacleType leftObstacle) {
		super();
		this.x = x;
		this.y = y;
		this.surfaceType = surfaceType;
		this.dirtUnits = dirtUnits;
		this.chargingStation = chargingStation;
		this.upObstacle = upObstacle;
		this.downObstacle = downObstacle;
		this.rightObstacle = rightObstacle;
		this.leftObstacle = leftObstacle;
	}

	//Simulates the action of the sweeper cleaning a unit of dirt off the floor.
	public void cleanCell() {
		if (dirtUnits > 0) {
			dirtUnits--;
		}
	}
	
	//determines if the cell has dirt or not.
	public boolean isDirty() {
        return dirtUnits > 0;
    }
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public SurfaceType getSurfaceType() {
		return surfaceType;
	}

	public int getDirtUnits() {
		return dirtUnits;
	}

	public boolean isChargingStation() {
		return chargingStation;
	}

	public ObstacleType getForwardObstacle() {
		return upObstacle;
	}

	public ObstacleType getBackwardObstacle() {
		return downObstacle;
	}

	public ObstacleType getRightObstacle() {
		return rightObstacle;
	}

	public ObstacleType getLeftObstacle() {
		return leftObstacle;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("X => ");
		str.append(x);
		str.append(", Y => ");
		str.append(y);
		str.append(", Surface => ");
		str.append(surfaceType);
		str.append(", DirtUnits => ");
		str.append(dirtUnits);
		str.append(", ChargingStation => ");
		str.append(chargingStation);
		str.append(", UpObstacle => ");
		str.append(upObstacle);
		str.append(", DownObstacle => ");
		str.append(downObstacle);
		str.append(", RightObstacle => ");
		str.append(rightObstacle);
		str.append(", LeftObstacle => ");
		str.append(leftObstacle);
		return str.toString();
	}

}
