package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.AbsDecision;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

import java.math.BigDecimal;

public class IsReturnToCharger extends AbsDecision {

    private static final BigDecimal MIN_ACTION_POWER = new BigDecimal("8");

	@Override
	protected boolean decideImpl(HardwareInterfacePack sensors, State sweeperState)
			throws ControllerException {
        return sweeperState.getCurrentFloorCell().getPowerCost().add(MIN_ACTION_POWER).compareTo(sensors.getBatteryPack().getNumberOfBatteryUnits()) >= 0;
	}

	@Override
	protected String getActivityLogMsg() {
		return "Check if there is enough power to perform another action before returning to the charging station";
	}

}
