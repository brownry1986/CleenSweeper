package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.AbsAction;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.CleaningHead;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.SurfaceSensor;

public class SetCleaningHead extends AbsAction {

	@Override
	protected void executeImpl(HardwareInterfacePack sensors, State sweeperState)
			throws ControllerException {

		if (sensors == null) {
			throw new ControllerException("SensorPack not provided");
		}

		if (sweeperState == null) {
			throw new ControllerException("State not provided");
		}

		if (sensors.getSurfaceSensor() == null) {
			throw new ControllerException("Surface Sensor is not available");
		}

		SurfaceSensor sensor = sensors.getSurfaceSensor();

		SurfaceType type = sensor.getSurfaceType();
		if (SurfaceType.HIGH_PILE_CARPET.equals(type)) {

			if (!CleaningHead.HIGH_PILE_CARPET.equals(sweeperState.getCleaningHead())) {
				sweeperState.setCleaningHead(CleaningHead.HIGH_PILE_CARPET);
			}

		} else if (SurfaceType.LOW_PILE_CARPET.equals(type)) {

			if (!CleaningHead.LOW_PILE_CARPET.equals(sweeperState.getCleaningHead())) {
				sweeperState.setCleaningHead(CleaningHead.LOW_PILE_CARPET);
			}

		} else {
			if (!CleaningHead.BARE_FLOOR.equals(sweeperState.getCleaningHead())) {
				sweeperState.setCleaningHead(CleaningHead.BARE_FLOOR);
			}
		}

		sweeperState.log("Surface Detected: " + type.toString() + " Head Set To: "
				+ sweeperState.getCleaningHead().toString());

	}

	@Override
	protected double getPowerRequired(State sweeperState) {
		return 0;
	}

	@Override
	protected String getActivityInitLogMsg() {
		return "Setting the Cleaning Head";
	}

}
