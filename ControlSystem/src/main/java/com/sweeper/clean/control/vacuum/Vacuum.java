package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.AbsAction;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.CleaningHead;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.interfaces.CleaningApparatus;
import com.sweeper.hardware.interfaces.DirtSensor;

public class Vacuum extends AbsAction {

	@Override
	protected void executeImpl(HardwareInterfacePack sensors, State sweeperState) throws ControllerException {
		
		if(sensors == null){
			throw new ControllerException("SensorPack Not Provided");
		}
		
		if(sweeperState == null){
			throw new ControllerException("State Not Provided");
		}
		
		if(sensors.getCleaningApparatus() == null){
			throw new ControllerException("Cleaning Apparatus Not Provided");
		}
		
		if(sensors.getDirtSensor() == null){
			throw new ControllerException("Dirt Sensor Not Provided");
		}
		
		CleaningApparatus cleaningApparatus = sensors.getCleaningApparatus();
        cleaningApparatus.vacuum();
        
        DirtSensor dirtSensor = sensors.getDirtSensor();
		if(!dirtSensor.isDirty()){
			FloorCell currentCell = sweeperState.getCurrentFloorCell();
			if(currentCell != null){
				currentCell.setState(FloorCellState.CLEAN);
			}
		}
	}

	@Override
	protected double getPowerRequired(State sweeperState) throws ControllerException {

		if(sweeperState == null){
			throw new ControllerException("State Not Provided");
		}
		
		if(sweeperState.getCleaningHead() == null){
			throw new ControllerException("Cleaning Head not set");
		}
		
		CleaningHead head = sweeperState.getCleaningHead();
		
		int powerNeeded = 1;
		if(CleaningHead.HIGH_PILE_CARPET.equals(head)){
			powerNeeded = 3;
		} else if(CleaningHead.LOW_PILE_CARPET.equals(head)){
			powerNeeded = 2;
		} 
		
		return powerNeeded;
	}

	@Override
	protected String getActivityInitLogMsg() {
		return "Vacuuming";
	}

}
