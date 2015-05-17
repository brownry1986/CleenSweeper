package com.sweeper.hardware.sensors;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.hardware.floorplan.FloorCellBuilder;
import com.sweeper.hardware.interfaces.DirtSensor;
import com.sweeper.hardware.state.SweeperState;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DirtSensorTest {

	@Test
	public void vacuumTest() {
		//Create a floor cell and clean it.
		FloorCell floorCell = null;
		try {
			floorCell = FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.BARE_FLOOR, 1, true, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN);
		} catch (Exception e) {
			fail();
		}
		Map<Point, FloorCell> tempFloorPlan = new HashMap<Point, FloorCell>();
		tempFloorPlan.put(new Point(floorCell.getX(),floorCell.getY()), floorCell);
		SweeperState.setFloorPlan(tempFloorPlan);
		SweeperState.setXLocation(floorCell.getX());
		SweeperState.setYLocation(floorCell.getY());
		DirtSensor dirtSensor = new DirtSensorImpl();
		assertTrue("Floor Cell was not dirty", dirtSensor.isDirty());
	}

}
