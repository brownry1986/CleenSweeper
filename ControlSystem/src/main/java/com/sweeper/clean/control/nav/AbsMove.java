package com.sweeper.clean.control.nav;

import com.sweeper.clean.AbsAction;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

import java.awt.*;
import java.math.BigDecimal;

public abstract class AbsMove extends AbsAction {

    @Override
	protected void executeImpl(HardwareInterfacePack sensors, State sweeperState)
			throws ControllerException {

		if (sensors == null) {
			throw new ControllerException("Sensors not provided unable to move.");
		}

		if (sweeperState == null) {
			throw new ControllerException("State of the sweeper not provided unable to move.");
		}

		if (sensors.getBatteryPack() == null) {
			throw new ControllerException("Battery Sensor is not provided unable to move.");
		}

        BigDecimal beforeBattery = sensors.getBatteryPack().getNumberOfBatteryUnits();
		if (beforeBattery.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ControllerException("Out Of Power");
		}

		Point currentLoc = sweeperState.getCurrentLocation();
		if(currentLoc == null){
			throw new ControllerException("The current location Is null unable to move.");
		}
		
		Point targetPoint = getFuturePoint(currentLoc);
		if(targetPoint == null){
			throw new ControllerException("getFuturePoint returned null, unable to process move.");
		}

		move(sensors);
        sweeperState.log("Moved from Cell(" + currentLoc.getX() + ", " + currentLoc.getY() + ") to Cell(" + targetPoint.getX() + "," + targetPoint.getY() + ")");
		sweeperState.setCurrentLocation(targetPoint);
    }

    @Override
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

		return power.getPowerRequired(sweeperState, nextLocation);
	}
    
    protected MovePowerForcast getMovePowerForcast(){
    	return new MovePowerForcast();
    }

	protected abstract Point getFuturePoint(Point currentLocation) throws ControllerException;

	protected abstract void move(HardwareInterfacePack sensors) throws ControllerException;

}
