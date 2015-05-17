package com.sweeper.hardware.controller;

import com.sweeper.clean.control.Controller;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.simulator.ui.SimulatorUI;

import java.awt.Point;
import java.util.List;
import java.util.Map;

public interface SimulatorController {

	void start();
	
	void stop();
	
	void setFloorPlan(Map<Point, FloorCell> floorPlan);
	
	void setSweeper(Controller sweeperController);
	
	/**
	 * This method sets the delay in the model (SweeperState). The delay is called anytime the model is updated.
	 * By default the delay should be 0 when the SweeperState is created.
	 * @param seconds
	 */
	void setDelay(double seconds);
	
	/**
	 * This method is to allow the model (SweeperState) to be able to notify the UI that it has changed.
	 * via its notifyUpdate method.
	 * @param simUI
	 */
	void setUIUpdate(SimulatorUI simUI);
	
	List<String> getSweeperLog();
	
	List<String> getSweeperFloorPlan();
	
}
