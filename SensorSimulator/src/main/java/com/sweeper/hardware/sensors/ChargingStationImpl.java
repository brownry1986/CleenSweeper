package com.sweeper.hardware.sensors;

import com.sweeper.hardware.interfaces.ChargingStation;
import com.sweeper.hardware.state.SweeperState;

public class ChargingStationImpl implements ChargingStation {

    public boolean isChargingStation() {
        return SweeperState.isChargingStation();
    }

    public void emptyMe() {
        SweeperState.emptyCleaningBin();
    }
}
