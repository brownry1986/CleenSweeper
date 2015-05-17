package com.sweeper.clean;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

/**
 * Definition of an action command for the controller to execute
 * 
 */
public interface IAction {
	
	/**
	 * Executes an Action for the robot to take
	 * 
	 * @param sensors - the hardware interfaces
	 * @param state - the current memory in the robot
	 * @throws ControllerException - if invalid scenario found
	 */
	void execute(HardwareInterfacePack sensors, State state) throws ControllerException;
	
}
