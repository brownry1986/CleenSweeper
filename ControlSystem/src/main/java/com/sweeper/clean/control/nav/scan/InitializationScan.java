package com.sweeper.clean.control.nav.scan;

import com.sweeper.clean.AbsAction;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.DirtSensor;
import com.sweeper.hardware.interfaces.ObstacleSensor;
import com.sweeper.hardware.interfaces.SurfaceSensor;

import java.awt.*;
import java.math.BigDecimal;

public class InitializationScan extends AbsAction {

	@Override
	protected void executeImpl(HardwareInterfacePack sensors, State state)
			throws ControllerException {

		if (sensors == null) {
			throw new ControllerException(
					"Hardware Interface was not provided unable to initializing scan.");
		}

		if (state == null) {
			throw new ControllerException("State was not provided unable to initializing scan.");
		}

		ObstacleSensor obstacleSensor = sensors.getObstacleSensor();
		if (obstacleSensor == null) {
			throw new ControllerException(
					"Obstacle sensor is not available unable to initializing scan.");
		}
		
		ObstacleType back = obstacleSensor.getBackwardSensorQuery();
		ObstacleType forward = obstacleSensor.getForwardSensorQuery();
		ObstacleType left = obstacleSensor.getLeftSensorQuery();
		ObstacleType right = obstacleSensor.getRightSensorQuery();
		
		SurfaceSensor surfaceSensor = sensors.getSurfaceSensor();
		if (surfaceSensor == null) {
			throw new ControllerException(
					"Surface sensor is not available unable to initializing scan.");
		}
		
		SurfaceType surfaceType = surfaceSensor.getSurfaceType();
		
		DirtSensor dirtSensor = sensors.getDirtSensor();
		if (dirtSensor == null) {
			throw new ControllerException(
					"Dirt sensor is not available unable to initializing scan.");
		}
		
		boolean isDirty = dirtSensor.isDirty();
		FloorCellState cellState = FloorCellState.DIRTY;
		if(!isDirty){
			cellState = FloorCellState.CLEAN;
		}
		
		Point point = new Point(0, 0);

		FloorCell floorCell = new FloorCell(0, 0, true, forward, back, right, left, surfaceType, cellState);
        floorCell.setPowerCost(BigDecimal.ZERO);
		
		state.addFloorCell(point, floorCell);
		state.setCurrentLocation(point);
	}

	@Override
	protected double getPowerRequired(State sweeperState) throws ControllerException {
		return 0;
	}

	@Override
	protected String getActivityInitLogMsg() {
		return "Scanning Charging Station Location";
	}

}
