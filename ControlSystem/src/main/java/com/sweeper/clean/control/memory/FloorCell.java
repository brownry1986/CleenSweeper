package com.sweeper.clean.control.memory;

import com.sweeper.hardware.enumerations.Direction;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;

import java.math.BigDecimal;

//This class expects clean inputs
public class FloorCell {

	private static final BigDecimal MAX_COST = new BigDecimal(1000);

	// Indicates the x position of this cell in the floor plan
	private int x = 0;

	// Indicates the y position of this cell in the floor plan
	private int y = 0;

	// Determines if this cell contains a charging station
	private boolean chargingStation = false;

	// up is always considered a positive movement in the y direction
	private ObstacleType forwardObstacle = ObstacleType.UNKNOWN;

	// down is always considered a negative movement in the y direction.
	private ObstacleType backwardObstacle = ObstacleType.UNKNOWN;

	// right is always considered a positive movement in the x direction
	private ObstacleType rightObstacle = ObstacleType.UNKNOWN;

	// left is always considered a negative movement in the x direction
	private ObstacleType leftObstacle = ObstacleType.UNKNOWN;

	// Surface at this location
	private SurfaceType surfaceType = SurfaceType.BARE_FLOOR;

	// State of this cell
	private FloorCellState state = FloorCellState.DIRTY;

	// This indicates the type of floor cell
	private FloorCellType type = FloorCellType.UNVISITED;

	private BigDecimal powerCost = MAX_COST;

	private BigDecimal value = BigDecimal.ZERO;

	/**
	 * Creates a floor cell that is not visited
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public FloorCell(double x, double y) {

		super();

		this.x = (int) x;
		this.y = (int) y;
	}

	/**
	 * Creates the initial floor cell with the charging station
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param chargingStation - true if this cell is the charging station
	 * @param forwardObstacle - the sensor reading of y + 1
	 * @param backwardObstacle - the sensor reading of y - 1
	 * @param rightObstacle - the sensor reading of x + 1
	 * @param leftObstacle - the sensor reading of x - 1
	 * @param surfaceType - the surface 
	 * @param state - if the robot has visited the cell or not.
	 */
	public FloorCell(double x, double y, boolean chargingStation, ObstacleType forwardObstacle,
			ObstacleType backwardObstacle, ObstacleType rightObstacle, ObstacleType leftObstacle,
			SurfaceType surfaceType, FloorCellState state) {

		super();

		this.x = (int) x;
		this.y = (int) y;
		this.chargingStation = chargingStation;
		this.type = FloorCellType.VISITED;

		if (forwardObstacle != null) {
			this.forwardObstacle = forwardObstacle;
		}

		if (backwardObstacle != null) {
			this.backwardObstacle = backwardObstacle;
		}

		if (rightObstacle != null) {
			this.rightObstacle = rightObstacle;
		}

		if (leftObstacle != null) {
			this.leftObstacle = leftObstacle;
		}

		if (surfaceType != null) {
			this.surfaceType = surfaceType;
		}

		if (state != null) {
			this.state = state;
		}
	}

	/**
	 * Creates the a floor cell that is fully populated
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param forwardObstacle - the sensor reading of y + 1
	 * @param backwardObstacle - the sensor reading of y - 1
	 * @param rightObstacle - the sensor reading of x + 1
	 * @param leftObstacle - the sensor reading of x - 1
	 * @param surfaceType - the surface 
	 * @param state - if the robot has visited the cell or not.
	 */
	public FloorCell(double x, double y, ObstacleType forwardObstacle,
			ObstacleType backwardObstacle, ObstacleType rightObstacle, ObstacleType leftObstacle,
			SurfaceType surfaceType, FloorCellState state) {

		super();

		this.x = (int) x;
		this.y = (int) y;
		this.chargingStation = false;
		this.type = FloorCellType.VISITED;

		if (forwardObstacle != null) {
			this.forwardObstacle = forwardObstacle;
		}

		if (backwardObstacle != null) {
			this.backwardObstacle = backwardObstacle;
		}

		if (rightObstacle != null) {
			this.rightObstacle = rightObstacle;
		}

		if (leftObstacle != null) {
			this.leftObstacle = leftObstacle;
		}

		if (surfaceType != null) {
			this.surfaceType = surfaceType;
		}

		if (state != null) {
			this.state = state;
		}
	}

	/**
	 * @return x - coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y - coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return true if this cell is the charging station
	 */
	public boolean isChargingStation() {
		return chargingStation;
	}

	/**
	 * @param direction - the direction that you want the reading from
	 * @return what is the obstacle reading in the direction provided
	 */
	public ObstacleType getObstacle(Direction direction) {
		switch (direction) {
		case LEFT:
			return getLeftObstacle();
		case FORWARD:
			return getForwardObstacle();
		case RIGHT:
			return getRightObstacle();
		case BACKWARD:
			return getBackwardObstacle();

		}
		return forwardObstacle;
	}

	/**
	 * @return the obstacle reading in front of this cell
	 */
	public ObstacleType getForwardObstacle() {
		return forwardObstacle;
	}

	/**
	 * @return the obstacle reading in behind of this cell
	 */
	public ObstacleType getBackwardObstacle() {
		return backwardObstacle;
	}

	/**
	 * @return the obstacle reading to the right of this cell
	 */
	public ObstacleType getRightObstacle() {
		return rightObstacle;
	}

	/**
	 * @return the obstacle reading to the left of this cell
	 */
	public ObstacleType getLeftObstacle() {
		return leftObstacle;
	}

	/**
	 * @return the surface type of this cell
	 */
	public SurfaceType getSurfaceType() {
		return surfaceType;
	}

	/**
	 * @return the power cost estimate for moving across this cell
	 */
	public BigDecimal getPowerCost() {
		return powerCost;
	}

	/**
	 * @param set the power cost estimate for moving across this cell
	 */
	public void setPowerCost(BigDecimal powerCost) {
		this.powerCost = powerCost;
	}

	/**
	 * @return the weighted value of choosing this cell in the return path
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param the weighted value of choosing this cell in the return path
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * @return the value of if the floor cell is dirty or clean
	 */
	public FloorCellState getState() {
		return state;
	}

	/**
	 * set the state of the floor cell if dirty or clean
	 * @param state
	 */
	public void setState(FloorCellState state) {
		this.state = state;
	}

	/**
	 * @return - if this is a visited cell or not
	 */
	public FloorCellType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((backwardObstacle == null) ? 0 : backwardObstacle.hashCode());
		result = prime * result + (chargingStation ? 1231 : 1237);
		result = prime * result + ((forwardObstacle == null) ? 0 : forwardObstacle.hashCode());
		result = prime * result + ((leftObstacle == null) ? 0 : leftObstacle.hashCode());
		result = prime * result + ((rightObstacle == null) ? 0 : rightObstacle.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((surfaceType == null) ? 0 : surfaceType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		FloorCell other = (FloorCell) obj;
		if (backwardObstacle != other.backwardObstacle) {
			return false;
		}
		if (chargingStation != other.chargingStation) {
			return false;
		}
		if (forwardObstacle != other.forwardObstacle) {
			return false;
		}
		if (leftObstacle != other.leftObstacle) {
			return false;
		}
		if (rightObstacle != other.rightObstacle) {
			return false;
		}
		if (state != other.state) {
			return false;
		}
		if (surfaceType != other.surfaceType) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
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
        str.append(", ChargingStation => ");
        str.append(chargingStation);
        str.append(", ForwardObstacle => ");
        str.append(forwardObstacle);
        str.append(", BackwardObstacle => ");
        str.append(backwardObstacle);
        str.append(", RightObstacle => ");
        str.append(rightObstacle);
        str.append(", LeftObstacle => ");
        str.append(leftObstacle);
        return str.toString();
    }

}
