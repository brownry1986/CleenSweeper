package com.sweeper.hardware.mechanics;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.hardware.floorplan.FloorCellBuilder;
import com.sweeper.hardware.interfaces.CleaningApparatus;
import com.sweeper.hardware.state.SweeperState;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CleaningApparatusTest {

    CleaningApparatus cleaningApparatus;

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
        SweeperState.setBatteryPowerUnits(new BigDecimal("50"));
        cleaningApparatus = new CleaningApparatusImpl();
    }

    @Test
    public void testVacuum() {
        SweeperState.setXLocation(0);
        SweeperState.setYLocation(0);

        cleaningApparatus.vacuum();

        assertEquals(new BigDecimal("49"), SweeperState.getBatteryPowerUnits());
        assertFalse(SweeperState.isDirty());

        SweeperState.setXLocation(0);
        SweeperState.setYLocation(1);

        cleaningApparatus.vacuum();

        assertEquals(new BigDecimal("47"), SweeperState.getBatteryPowerUnits());
        assertFalse(SweeperState.isDirty());

        SweeperState.setXLocation(1);
        SweeperState.setYLocation(0);

        cleaningApparatus.vacuum();

        assertEquals(new BigDecimal("44"), SweeperState.getBatteryPowerUnits());
        assertFalse(SweeperState.isDirty());
    }


}
