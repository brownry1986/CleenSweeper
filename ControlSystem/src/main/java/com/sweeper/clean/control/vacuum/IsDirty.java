package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.AbsDecision;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.interfaces.DirtSensor;

public class IsDirty extends AbsDecision {

	@Override
	protected boolean decideImpl(HardwareInterfacePack sensors, State sweeperState)
			throws ControllerException {

		if (sensors == null) {
			throw new ControllerException("SensorPack not provided");
		}

		if (sensors.getDirtSensor() == null) {
			throw new ControllerException("Dirt Sensor is not available");
		}

		if (sweeperState == null) {
			throw new ControllerException("State is not available");
		}

		DirtSensor dirtSensor = sensors.getDirtSensor();

		boolean isDirty = dirtSensor.isDirty();
		if (!isDirty) {
			FloorCell currentCell = sweeperState.getCurrentFloorCell();
			if (currentCell != null) {
				currentCell.setState(FloorCellState.CLEAN);
			}
		}

		return isDirty;
	}

	@Override
	protected String getActivityLogMsg() {
		return "Check if the current cell is Dirty";
	}

}
