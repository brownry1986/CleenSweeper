package com.sweeper.hardware.controller;

import com.sweeper.clean.control.Controller;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.hardware.state.SweeperState;
import com.sweeper.simulator.ui.SimulatorUI;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulatorControllerImpl implements SimulatorController {

	private Controller sweeperController;
	private Thread t;
	private boolean isRunning = false;
	
	public SimulatorControllerImpl(Controller sweeperController, Map<Point, FloorCell>floorPlan) {
		this.sweeperController = sweeperController;
		this.t = new Thread(sweeperController);
		SweeperState.setFloorPlan(floorPlan);

        // TODO: Should we remove the following initialization?
        // Sweeper State is already initialized to position 0,0 and batter power of 1000
		//It is assumed that the sweeper will always start at 0,0
		SweeperState.setXLocation(0);
		SweeperState.setYLocation(0);
		SweeperState.setBatteryPowerUnits(new BigDecimal("1000"));
	}
	
	public SimulatorControllerImpl(){}
	
	public void start() {
		t.start();
		isRunning = true;
	}

	public void stop() {
		sweeperController.stop();
		isRunning = false;
		this.t = new Thread(sweeperController);
	}

	public void setFloorPlan(Map<Point, FloorCell> floorPlan) {
		if (isRunning) {
			stop();
		}
		SweeperState.setFloorPlan(floorPlan);
		//It is assumed that the sweeper will always start at 0,0
		SweeperState.setXLocation(0);
		SweeperState.setYLocation(0);
        SweeperState.setBatteryPowerUnits(new BigDecimal("1000"));
	}

	public void setSweeper(Controller sweeperController) {
		if (isRunning) {
			stop();
		}
			this.sweeperController = sweeperController;
			this.t = new Thread(sweeperController);
	}
	
	public void setDelay(double seconds){
		// need to multiply by 1000 because Thread.sleep wants milliseconds.
		double milliseconds = seconds * 1000;
		SweeperState.setDelay((long) milliseconds);
	}
	
	public void setUIUpdate(SimulatorUI simUI){
		SweeperState.setUI(simUI);
	}
	
	public List<String> getSweeperLog() {
		return sweeperController.getLog();
	}
	
	public List<String> getSweeperFloorPlan() {
		List<String> floorList = new ArrayList<String>();
		try {
			Map<Point, com.sweeper.clean.control.memory.FloorCell> sweeperFloorCells = sweeperController.getFloorPlan();
			for (Map.Entry<Point, com.sweeper.clean.control.memory.FloorCell> entry : sweeperFloorCells.entrySet()) {
				if (entry != null) {
					floorList.add(entry.getValue().toString());
                }
			}
		} catch (ControllerException ce){
			//this will swallow the exception and return an empty list
		}
		return floorList;
	}


}
