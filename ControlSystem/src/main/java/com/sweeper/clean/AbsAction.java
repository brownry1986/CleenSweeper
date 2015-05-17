package com.sweeper.clean;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

/**
 * Abstract parent of all actions.
 * 
 * Provide functions that will shared by all actions. 
 * Like determining if the bin is full or the battery is low
 * @author Dave
 *
 */
public abstract class AbsAction implements IAction {

	/**
	 * Executes the general edit checks on power and full bin before the action is taken
	 */
	public void execute(HardwareInterfacePack sensors, State state) throws ControllerException{

		if(sensors == null){
			throw new ControllerException("SensorPack not provided");
		}
		
		if(state == null){
			throw new ControllerException("State is not provided");
		}
		
		if(sensors.getBatteryPack() == null){
			throw new ControllerException("Battery Sensor is not provided.");
		}

		state.log(getActivityInitLogMsg());
		executeImpl(sensors, state);
	}
	
	/**
	 * Execute the logic specific to the action being taken
	 * 
	 * @param sensors - hardware interface
	 * @param sweeperState - the current memory of the robot
	 * @throws ControllerException - if there is an error
	 */
	protected abstract void executeImpl(HardwareInterfacePack sensors, State sweeperState) throws ControllerException;
	
	/**
	 * Returns the power that is going to be required to perform this action
	 * 
	 * @param sweeperState - the current memory of the robot
	 * @return the power that this action will consume
	 * @throws ControllerException - if there is an error
	 */
	protected abstract double getPowerRequired(State sweeperState) throws ControllerException;
	
	/**
	 * @return A static message to be put into the log marking this was executed.
	 */
	protected abstract String getActivityInitLogMsg();

}
