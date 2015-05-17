package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.AbsDecision;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

public class IsReturnedToCharger extends AbsDecision {

	@Override
	protected boolean decideImpl(HardwareInterfacePack sensors, State sweeperState) throws ControllerException {
        return sweeperState.getCurrentFloorCell().isChargingStation();
	}

	@Override
	protected String getActivityLogMsg() {
		return "Check if we are at the charging station";
	}

}
