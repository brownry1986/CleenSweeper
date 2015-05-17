package com.sweeper.hardware.sensors;

import com.sweeper.hardware.interfaces.DirtSensor;
import com.sweeper.hardware.state.SweeperState;

public class DirtSensorImpl implements DirtSensor {

	public boolean isDirty() {
		return SweeperState.isDirty();
	}
	
	public String toString(){
		return "The dirt sensor detects that the cell is " + (isDirty()? "dirty" : "clean");
	}

}
