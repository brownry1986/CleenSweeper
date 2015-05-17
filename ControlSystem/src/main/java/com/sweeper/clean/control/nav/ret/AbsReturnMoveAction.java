package com.sweeper.clean.control.nav.ret;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.IAction;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;
import com.sweeper.clean.control.nav.MovePowerForcast;

import java.awt.*;

/**
 * This abstract class is special for returning the robot to the station
 * it's not allowed to throw the Return exception.
 * 
 * If the power is gone here we are stopped.
 * 
 * @author Dave
 *
 */
public abstract class AbsReturnMoveAction implements IAction {

	public void execute(HardwareInterfacePack sensors, State sweeperState) throws ControllerException {

		if(sensors == null){
			throw new ControllerException("SensorPack not provided");
		}
		
		if(sweeperState == null){
			throw new ControllerException("State is not provided");
		}
		
		Point currentLoc = sweeperState.getCurrentLocation();
		if(currentLoc == null){
			throw new ControllerException("The current location is unknown");
		}
		
		Point targetPoint = getFuturePoint(currentLoc);
		if(targetPoint == null){
			throw new ControllerException("The target point is unknown");
		}
		
		move(sensors);	
		sweeperState.log("Moved on Return to Charger from Cell(" + currentLoc.getX() + ", " + currentLoc.getY() + ") to Cell(" + targetPoint.getX() + "," + targetPoint.getY() + ")");
		sweeperState.setCurrentLocation(targetPoint);
	}
	

	/**
	 * Returns the power that is going to be required to perform this action
	 * 
	 * @param sweeperState - the current memory of the robot
	 * @return the power that this action will consume
	 * @throws ControllerException - if there is an error
	 */
	protected double getPowerRequired(State sweeperState) throws ControllerException {
		
		if (sweeperState == null) {
			throw new ControllerException(
					"State of the sweeper not provided unable to move backward.");
		}

		if (sweeperState.getCurrentLocation() == null) {
			throw new ControllerException("The current location of the robot is not known.");
		}
		
		Point nextLocation = getFuturePoint(sweeperState.getCurrentLocation());
		
		if(nextLocation == null){
			throw new ControllerException("getFuturePoint returned null");
		}
		
		MovePowerForcast power = getMovePowerForcast();
		
		double retVal = power.getPowerRequired(sweeperState, nextLocation);
		
		return retVal;
	}
	
	protected MovePowerForcast getMovePowerForcast(){
    	return new MovePowerForcast();
    }
	
	protected abstract Point getFuturePoint(Point currentLocation) throws ControllerException;
	
	protected abstract void move(HardwareInterfacePack sensors) throws ControllerException;
	
}
