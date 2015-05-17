package com.sweeper.clean;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

/**
 * Definition of a decision that the robot needs to make
 * 
 * @author Dave
 *
 */
public interface IDecision {
	
	/**
	 * Executes a Decision for the robot to take
	 * 
	 * @param sensors - the hardware interfaces
	 * @param sweeperState - the current memory in the robot
	 * @return true if the decision is true
	 * @throws ControllerException - if invalid scenario found
	 * @throws ReturnToChargerException - if the action cannot be taken and the robot needs to return to station
	 */
	boolean decide(HardwareInterfacePack sensors, State sweeperState) throws ControllerException;

}
