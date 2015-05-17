package com.sweeper.hardware.controller;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.Controller;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.hardware.floorplan.FloorCellBuilder;
import com.sweeper.hardware.mechanics.CleaningApparatusImpl;
import com.sweeper.hardware.mechanics.MovementApparatusImpl;
import com.sweeper.hardware.sensors.BatteryPackImpl;
import com.sweeper.hardware.sensors.DirtSensorImpl;
import com.sweeper.hardware.sensors.ObstacleSensorImpl;
import com.sweeper.hardware.sensors.SurfaceSensorImpl;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

public class SimulatorControllerIntegrationTest {

	@Test
	public void InitializeControllerTest() {
		//The simcontroller will need to be run in a Thread.
		SimulatorController simController;
		Map<Point, FloorCell> floorPlan = new HashMap<Point, FloorCell>();
		try {
			  floorPlan.put(new Point(0,0), FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.BARE_FLOOR, 2, true, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE));
		} catch (Exception e) {
			fail("Exception caught when trying to create floorplan\n");
		}
		//create a hardware interface pack and a sweeper controller
		HardwareInterfacePack hip = new HardwareInterfacePack();
		Controller sweeperController = new Controller();
		
		//load up the hardware interface pack.
		hip.setBatteryPack(new BatteryPackImpl());
		hip.setDirtSensor(new DirtSensorImpl());
		hip.setSurfaceSensor(new SurfaceSensorImpl());
		hip.setObstacleSensor(new ObstacleSensorImpl());
		hip.setCleaningApparatus(new CleaningApparatusImpl());
		hip.setMovementApparatus(new MovementApparatusImpl());
		try {
			sweeperController.setHardwarePack(hip);
		} catch (Exception e) {
			fail("Exception caught when trying to set the hardware pack");
		}
		
		
		//create the Sim Controller now
		simController = new SimulatorControllerImpl(sweeperController, floorPlan);
		simController.start();
		try {
			Thread.sleep(10);
		} catch (Exception e) {fail(e.getMessage());}
		simController.stop();
		
	}

}
