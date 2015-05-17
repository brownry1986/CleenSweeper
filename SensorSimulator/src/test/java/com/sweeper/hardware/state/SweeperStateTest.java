package com.sweeper.hardware.state;

import com.sweeper.hardware.exceptions.BatteryOutOfPowerException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SweeperStateTest {

    @Test
    public void testDrainBattery() {
        SweeperState.setBatteryPowerUnits(new BigDecimal("5.0"));
        SweeperState.drainBattery(new BigDecimal("3.0"));
        assertEquals(new BigDecimal("2.0"), SweeperState.getBatteryPowerUnits());

        try {
            SweeperState.drainBattery(new BigDecimal("3.0"));
            fail();
        } catch (BatteryOutOfPowerException be) {
            // pass
        }
    }
}
