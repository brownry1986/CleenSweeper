package com.sweeper.clean.control.nav.scan;

import com.sweeper.clean.AbsAction;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.Direction;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.ChargingStation;
import com.sweeper.hardware.interfaces.DirtSensor;
import com.sweeper.hardware.interfaces.ObstacleSensor;
import com.sweeper.hardware.interfaces.SurfaceSensor;

import java.awt.*;
import java.math.BigDecimal;

public class ScanLocation extends AbsAction {

    @Override
	public void executeImpl(HardwareInterfacePack hardware, State state) throws ControllerException {

		if (hardware == null) {
			throw new ControllerException(
					"Hardware Interface was not provided unable to ScanLocation");
		}

		if (state == null) {
			throw new ControllerException("State was not provided unable to ScanLocation");
		}

		ObstacleSensor sensor = hardware.getObstacleSensor();

		if (sensor == null) {
			throw new ControllerException(
					"Location senor is not available unable to scan location.");
		}

		ObstacleType back = sensor.getBackwardSensorQuery();
		ObstacleType forward = sensor.getForwardSensorQuery();
		ObstacleType left = sensor.getLeftSensorQuery();
		ObstacleType right = sensor.getRightSensorQuery();

		SurfaceSensor surfaceSensor = hardware.getSurfaceSensor();
		if (surfaceSensor == null) {
			throw new ControllerException(
					"Surface senor is not available unable to initializing scan.");
		}

		SurfaceType surfaceType = surfaceSensor.getSurfaceType();

		Point point = state.getCurrentLocation();
		if (point == null) {
			throw new ControllerException("Current Location is unknown unable to scan");
		}

		DirtSensor dirtSensor = hardware.getDirtSensor();
		if (dirtSensor == null) {
			throw new ControllerException(
					"Dirt senor is not available unable to initializing scan.");
		}

        ChargingStation chargingStation = hardware.getChargingStation();

        boolean isDirty = dirtSensor.isDirty();
		FloorCellState cellState = FloorCellState.DIRTY;
		if (!isDirty) {
			cellState = FloorCellState.CLEAN;
		}

        if ( state.getFloorCell(point) == null ) {
            FloorCell floorCell = new FloorCell(point.getX(), point.getY(), chargingStation.isChargingStation(), forward, back, right, left, surfaceType, cellState);
            state.addFloorCell(point, floorCell);
        }

        FloorCell currentFloorCell = state.getCurrentFloorCell();
        recalculatePowerCost(state, currentFloorCell.getX(), currentFloorCell.getY());
        recalculateCellValue(state, currentFloorCell.getX(), currentFloorCell.getY());
        state.log("Calculated power cost to return from Cell(" + point.getX() + "," + point.getY() + ") to Charging Station as " + currentFloorCell.getPowerCost());
        state.log("Calculated value of current Cell(" + point.getX() + "," + point.getY() + ") as " + currentFloorCell.getValue());

		state.log("Scanned Cell(" + point.getX() + ", " + point.getY() + ")");
	}

    private void recalculateCellValue(State state, int x, int y) throws ControllerException {
        FloorCell currentFloorCell = state.getFloorCell(new Point(x, y));

        BigDecimal leftValue = BigDecimal.ZERO;
        if ( currentFloorCell.getLeftObstacle() == ObstacleType.OPEN ) {
            FloorCell leftFloorCell = state.getSurroundingFloorCell(x, y, Direction.LEFT);
            if ( leftFloorCell == null || leftFloorCell.getState() == FloorCellState.DIRTY) {
                leftValue = BigDecimal.ONE;
            } else {
                leftValue = leftFloorCell.getValue().divide(BigDecimal.TEN);
            }
        }

        BigDecimal forwardValue = BigDecimal.ZERO;
        if ( currentFloorCell.getForwardObstacle() == ObstacleType.OPEN ) {
            FloorCell forwardFloorCell = state.getSurroundingFloorCell(x, y, Direction.FORWARD);
            if ( forwardFloorCell == null || forwardFloorCell.getState() == FloorCellState.DIRTY) {
                forwardValue = BigDecimal.ONE;
            } else {
                forwardValue = forwardFloorCell.getValue().divide(BigDecimal.TEN);
            }
        }

        BigDecimal rightValue = BigDecimal.ZERO;
        if ( currentFloorCell.getRightObstacle() == ObstacleType.OPEN ) {
            FloorCell rightFloorCell = state.getSurroundingFloorCell(x, y, Direction.RIGHT);
            if ( rightFloorCell == null || rightFloorCell.getState() == FloorCellState.DIRTY) {
                rightValue = BigDecimal.ONE;
            } else {
                rightValue = rightFloorCell.getValue().divide(BigDecimal.TEN);
            }
        }

        BigDecimal backwardValue = BigDecimal.ZERO;
        if ( currentFloorCell.getBackwardObstacle() == ObstacleType.OPEN ) {
            FloorCell backwardFloorCell = state.getSurroundingFloorCell(x, y, Direction.BACKWARD);
            if ( backwardFloorCell == null || backwardFloorCell.getState() == FloorCellState.DIRTY) {
                backwardValue = BigDecimal.ONE;
            } else {
                backwardValue = backwardFloorCell.getValue().divide(BigDecimal.TEN);
            }
        }

        currentFloorCell.setValue(leftValue.add(forwardValue).add(rightValue).add(backwardValue));
    }

    private void recalculatePowerCost(State state, int x, int y) throws ControllerException {
        FloorCell currentFloorCell = state.getFloorCell(new Point(x, y));
        BigDecimal currentFloorCellCost = currentFloorCell.getPowerCost();
        BigDecimal minimumCost = calculateMinimumCost(state, x, y, currentFloorCell.getSurfaceType());

        state.log("Calculated power cost to return from Cell(" + currentFloorCell.getX() + "," + currentFloorCell.getY() + ") to Charging Station as " + currentFloorCell.getPowerCost());
        if ( minimumCost.compareTo(currentFloorCellCost) < 0 ) {
            currentFloorCell.setPowerCost(minimumCost);
            recalculateSurroundCellPowerCost(state, x, y);
        }
    }

    private void recalculateSurroundCellPowerCost(State state, int x, int y) throws ControllerException {
        for (Direction direction : Direction.values() ) {
            FloorCell floorCell = state.getSurroundingFloorCell(x, y, direction);
            if ( floorCell != null ) {
                recalculatePowerCost(state, floorCell.getX(), floorCell.getY());
            }
        }
    }

    private BigDecimal calculateMinimumCost(State sweeperState, int x, int y, SurfaceType currentSurfaceType) throws ControllerException {
        if (sweeperState.getFloorCell(new Point(x, y)).isChargingStation()) {
            return BigDecimal.ZERO;
        }

        BigDecimal minimumCost = new BigDecimal(1000);
        for (Direction direction : Direction.values() ) {
            FloorCell floorCell = sweeperState.getSurroundingFloorCell(x, y, direction);
            if ( floorCell != null ) {
                BigDecimal cost = calculateCost(currentSurfaceType, floorCell.getSurfaceType(), floorCell.getPowerCost());
                minimumCost = minimumCost.min(cost);
            }
        }

        return minimumCost;
    }

    private BigDecimal calculateCost(SurfaceType currentSurfaceType, SurfaceType surfaceType, BigDecimal cellCost) {
        return determinePowerForSurfaceType(surfaceType).add(determinePowerForSurfaceType(currentSurfaceType)).divide(new BigDecimal(2)).add(cellCost);
    }

    public BigDecimal determinePowerForSurfaceType( SurfaceType surfaceType ) {
        if (SurfaceType.BARE_FLOOR.equals(surfaceType)) {
            return BigDecimal.ONE;
        }

        if (SurfaceType.LOW_PILE_CARPET.equals(surfaceType)) {
            return new BigDecimal(2);
        }

        if (SurfaceType.HIGH_PILE_CARPET.equals(surfaceType)) {
            return new BigDecimal(3);
        }

        throw new IllegalArgumentException("Invalid surface type");
    }

    @Override
	protected double getPowerRequired(State sweeperState) {
		return 0;
	}

	@Override
	protected String getActivityInitLogMsg() {
		return "Scanning Current Location";
	}

}
