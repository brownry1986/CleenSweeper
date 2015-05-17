package com.sweeper.simulator.ui;

import com.sweeper.hardware.controller.SimulatorController;
import com.sweeper.hardware.state.SweeperStateActions;

public interface SimulatorUI {

	void runUI();
	
	void setSimulatorController(SimulatorController simulatorController);
	
	void notifyUpdate(SweeperStateActions action);

}
