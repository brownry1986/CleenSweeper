package com.sweeper.clean;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

/**
 * Abstract parent of all decisions
 * 
 * Provide functions that will shared by all actions. 
 * Like determining if the bin is full or the battery is low
 * @author Dave
 *
 */
public abstract class AbsDecision implements IDecision {

	/**
	 * Executes the general edit checks on power and full bin before the action is taken
	 */
	public boolean decide(HardwareInterfacePack sensors, State sweeperState) throws ControllerException {
		
		if(sensors == null){
			throw new ControllerException("SensorPack not provided");
		}
		
		if(sweeperState == null){
			throw new ControllerException("State is not provided");
		}
		
		boolean retVal = decideImpl(sensors, sweeperState);
		
		sweeperState.log(getActivityLogMsg());
		
		return retVal;
	}
	
	/**
	 * Execute the logic specific to the decision being made
	 * 
	 * @param sensors - hardware interface
	 * @param sweeperState - the current memory of the robot
	 * @return true if the decision is true
	 * @throws ControllerException - if there is an error
	 */
	protected abstract boolean decideImpl(HardwareInterfacePack sensors, State sweeperState) throws ControllerException;
	
	/**
	 * @return A static message to be put into the log marking this was executed.
	 */
	protected abstract String getActivityLogMsg();

}
