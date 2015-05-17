package com.sweeper.hardware.mechanics;

import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.CleaningApparatus;
import com.sweeper.hardware.state.SweeperState;
import com.sweeper.hardware.util.Utility;

public class CleaningApparatusImpl implements CleaningApparatus {

    public void vacuum() {
        SweeperState.drainBattery(Utility.determinePowerForSurfaceType(SweeperState.getSurfaceType()));
        SweeperState.cleanCell();
    }

    public SurfaceType getCleaningApparatusPosition() {
        return SweeperState.getCleaningHeadHeight();
    }

    public void setCleaningApparatusPosition(SurfaceType surfaceType) {
        SweeperState.setCleaningHeadHeight(surfaceType);
    }

    public boolean isFull() {
        return SweeperState.isCleaningBinFull();
    }

    public void empty() {
        SweeperState.emptyCleaningBin();
    }
    public String toString() {
   	 	return "The bin is " + (isFull() ? "full" : "not full");
    }
}
