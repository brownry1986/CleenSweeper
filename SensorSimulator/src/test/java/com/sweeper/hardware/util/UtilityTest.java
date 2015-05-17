package com.sweeper.hardware.util;

import com.sweeper.hardware.enumerations.SurfaceType;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UtilityTest {

    @Test
    public void testDeterminePowerForSurfaceType() {
        assertEquals(Constants.BARE_FLOOR_POWER, Utility.determinePowerForSurfaceType(SurfaceType.BARE_FLOOR));
        assertEquals(Constants.LOW_PILE_CARPET_POWER, Utility.determinePowerForSurfaceType(SurfaceType.LOW_PILE_CARPET));
        assertEquals(Constants.HIGH_PILE_CARPET_POWER, Utility.determinePowerForSurfaceType(SurfaceType.HIGH_PILE_CARPET));
        try {
            Utility.determinePowerForSurfaceType(null);
            fail();
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    @Test
    public void testDeterminePowerForMovement() {
        assertEquals(new BigDecimal("1"), Utility.determinePowerForMovement(SurfaceType.BARE_FLOOR, SurfaceType.BARE_FLOOR));
        assertEquals(new BigDecimal("1.5"), Utility.determinePowerForMovement(SurfaceType.BARE_FLOOR, SurfaceType.LOW_PILE_CARPET));
        assertEquals(new BigDecimal("2"), Utility.determinePowerForMovement(SurfaceType.BARE_FLOOR, SurfaceType.HIGH_PILE_CARPET));
        assertEquals(new BigDecimal("1.5"), Utility.determinePowerForMovement(SurfaceType.LOW_PILE_CARPET, SurfaceType.BARE_FLOOR));
        assertEquals(new BigDecimal("2"), Utility.determinePowerForMovement(SurfaceType.LOW_PILE_CARPET, SurfaceType.LOW_PILE_CARPET));
        assertEquals(new BigDecimal("2.5"), Utility.determinePowerForMovement(SurfaceType.LOW_PILE_CARPET, SurfaceType.HIGH_PILE_CARPET));
        assertEquals(new BigDecimal("2"), Utility.determinePowerForMovement(SurfaceType.HIGH_PILE_CARPET, SurfaceType.BARE_FLOOR));
        assertEquals(new BigDecimal("2.5"), Utility.determinePowerForMovement(SurfaceType.HIGH_PILE_CARPET, SurfaceType.LOW_PILE_CARPET));
        assertEquals(new BigDecimal("3"), Utility.determinePowerForMovement(SurfaceType.HIGH_PILE_CARPET, SurfaceType.HIGH_PILE_CARPET));
    }
}
