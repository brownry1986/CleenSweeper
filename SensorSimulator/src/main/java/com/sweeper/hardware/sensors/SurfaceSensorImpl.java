package com.sweeper.hardware.sensors;

import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.SurfaceSensor;
import com.sweeper.hardware.state.SweeperState;

public class SurfaceSensorImpl implements SurfaceSensor {

    public SurfaceType getSurfaceType() {
        return SweeperState.getSurfaceType();
    }
    
    public String toString(){
    	return "The surface type is " + getSurfaceType();
    }

}
