package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.AbsAction;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

public class Recharge extends AbsAction {

	@Override
	protected void executeImpl(HardwareInterfacePack sensors, State sweeperState) throws ControllerException {
		
		if(sensors == null){
			throw new ControllerException("SensorPack Not Provided");
		}
		
		if(sensors.getBatteryPack() == null){
			throw new ControllerException("Battery Pack Not Provided");
		}

        sensors.getBatteryPack().recharge();
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
