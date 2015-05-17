package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.AbsDecision;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

public class IsBinFull extends AbsDecision{

	@Override
	protected boolean decideImpl(HardwareInterfacePack sensors, State sweeperState)
			throws ControllerException {
        return sensors.getCleaningApparatus().isFull();
	}

	@Override
	protected String getActivityLogMsg() {
		return "Check if the Bin is Full";
	}

}
