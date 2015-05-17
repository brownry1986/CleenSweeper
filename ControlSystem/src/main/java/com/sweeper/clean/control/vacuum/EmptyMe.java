package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.AbsAction;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

public class EmptyMe extends AbsAction {

	@Override
	protected void executeImpl(HardwareInterfacePack sensors, State sweeperState) throws ControllerException {
		
		if(sensors == null){
			throw new ControllerException("SensorPack Not Provided");
		}
		
		if(sensors.getChargingStation() == null){
			throw new ControllerException("Charging Station Not Provided");
		}

        sensors.getChargingStation().emptyMe();
	}

	@Override
	protected double getPowerRequired(State sweeperState) throws ControllerException {
		return 0;
	}

	@Override
	protected String getActivityInitLogMsg() {
		return "Emptying and recharging vacuum";
	}

}
