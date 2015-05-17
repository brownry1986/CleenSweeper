package com.sweeper.hardware.sensors;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.hardware.floorplan.FloorCellBuilder;
import com.sweeper.hardware.interfaces.SurfaceSensor;
import com.sweeper.hardware.state.SweeperState;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SurfaceSensorTest {

    SurfaceSensor sensor;

    @Before
    public void setup() throws Exception {
        Map<Point, FloorCell> floorPlan = new HashMap<Point, FloorCell>();
        FloorCell floorCell1 = FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.BARE_FLOOR, 1, true, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN);
        FloorCell floorCell2 = FloorCellBuilder.floorCellCreate(0, 1, SurfaceType.LOW_PILE_CARPET, 1, false, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN);
        FloorCell floorCell3 = FloorCellBuilder.floorCellCreate(1, 0, SurfaceType.HIGH_PILE_CARPET, 1, false, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN);
        floorPlan.put(new Point(0, 0), floorCell1);
        floorPlan.put(new Point(0, 1), floorCell2);
        floorPlan.put(new Point(1, 0), floorCell3);
        SweeperState.setFloorPlan(floorPlan);
        sensor = new SurfaceSensorImpl();
    }

    // TODO: Change this test to use mock objects
	@Test
	public void getSurfaceTypeTest() {
        SweeperState.setXLocation(0);
        SweeperState.setYLocation(0);
        assertEquals( SurfaceType.BARE_FLOOR, sensor.getSurfaceType() );

        SweeperState.setXLocation(0);
        SweeperState.setYLocation(1);
        assertEquals( SurfaceType.LOW_PILE_CARPET, sensor.getSurfaceType() );

        SweeperState.setXLocation(1);
        SweeperState.setYLocation(0);
        assertEquals( SurfaceType.HIGH_PILE_CARPET, sensor.getSurfaceType() );
   	}
	
}
