package com.sweeper.hardware.sensors;

import com.sweeper.hardware.interfaces.BatteryPack;
import com.sweeper.hardware.state.SweeperState;

import java.math.BigDecimal;

public class BatteryPackImpl implements BatteryPack {

    public BigDecimal getNumberOfBatteryUnits() {
        return SweeperState.getBatteryPowerUnits();
    }

    public void recharge() {
        SweeperState.rechargeBattery();
    }
    
    public String toString(){
    	return "Number of power units: " + getNumberOfBatteryUnits();
    }

}
