package com.sweeper.hardware.mechanics;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.hardware.floorplan.FloorCellBuilder;
import com.sweeper.hardware.interfaces.MovementApparatus;
import com.sweeper.hardware.state.SweeperState;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class MovementApparatusTest {

    MovementApparatus movementApparatus;

    @Before
    public void setup() throws Exception {
        Map<Point, FloorCell> floorPlan = new HashMap<Point, FloorCell>();
        FloorCell floorCell1 = FloorCellBuilder.floorCellCreate(0, 1, SurfaceType.LOW_PILE_CARPET, 1, false, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN);
        FloorCell floorCell2 = FloorCellBuilder.floorCellCreate(1, 0, SurfaceType.HIGH_PILE_CARPET, 1, false, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN);
        FloorCell floorCell3 = FloorCellBuilder.floorCellCreate(1, 1, SurfaceType.BARE_FLOOR, 1, false, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN);

        floorPlan.put(new Point(0, 1), floorCell1);
        floorPlan.put(new Point(1, 0), floorCell2);
        floorPlan.put(new Point(1, 1), floorCell3);

        SweeperState.setFloorPlan(floorPlan);
        SweeperState.setBatteryPowerUnits(new BigDecimal("50"));
        movementApparatus = new MovementApparatusImpl();
    }

    @Test
    public void testVacuum() {
        SweeperState.setXLocation(1);
        SweeperState.setYLocation(1);

        movementApparatus.moveBackward();
        assertEquals(new BigDecimal("48"), SweeperState.getBatteryPowerUnits());
        assertEquals(1, SweeperState.getXLocation());
        assertEquals(0, SweeperState.getYLocation());

        movementApparatus.moveForward();
        assertEquals(new BigDecimal("46"), SweeperState.getBatteryPowerUnits());
        assertEquals(1, SweeperState.getXLocation());
        assertEquals(1, SweeperState.getYLocation());

        movementApparatus.moveLeft();
        assertEquals(new BigDecimal("44.5"), SweeperState.getBatteryPowerUnits());
        assertEquals(0, SweeperState.getXLocation());
        assertEquals(1, SweeperState.getYLocation());

        movementApparatus.moveRight();
        assertEquals(new BigDecimal("43.0"), SweeperState.getBatteryPowerUnits());
        assertEquals(1, SweeperState.getXLocation());
        assertEquals(1, SweeperState.getYLocation());
    }

}
