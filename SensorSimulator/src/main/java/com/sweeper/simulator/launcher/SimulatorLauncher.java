package com.sweeper.simulator.launcher;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.Controller;
import com.sweeper.hardware.controller.SimulatorController;
import com.sweeper.hardware.controller.SimulatorControllerImpl;
import com.sweeper.hardware.mechanics.CleaningApparatusImpl;
import com.sweeper.hardware.mechanics.MovementApparatusImpl;
import com.sweeper.hardware.sensors.*;
import com.sweeper.simulator.ui.SimulatorGUI;
import com.sweeper.simulator.ui.SimulatorUI;

public final class SimulatorLauncher {

    private SimulatorLauncher() {}

	public static void main(String[] args) {
		SimulatorUI simulatorUI = new SimulatorGUI();
		
		//wire up the simulator controller and sweeper controller
		HardwareInterfacePack hip = new HardwareInterfacePack();
		Controller sweeperController = new Controller();
		
		//load up the hardware interface pack.
		hip.setBatteryPack(new BatteryPackImpl());
		hip.setDirtSensor(new DirtSensorImpl());
		hip.setSurfaceSensor(new SurfaceSensorImpl());
		hip.setObstacleSensor(new ObstacleSensorImpl());
		hip.setCleaningApparatus(new CleaningApparatusImpl());
		hip.setMovementApparatus(new MovementApparatusImpl());
		hip.setChargingStation(new ChargingStationImpl());
		sweeperController.setHardwarePack(hip);
		

		//create the Sim Controller now
		SimulatorController simulatorControl = new SimulatorControllerImpl();
		simulatorControl.setSweeper(sweeperController);
		simulatorUI.setSimulatorController(simulatorControl);
		
		//connect the model and UI for notifying of updates
		simulatorControl.setUIUpdate(simulatorUI); 
		
		//start the gui
		simulatorUI.runUI();
	}
	
}
