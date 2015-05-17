package com.sweeper.hardware.sensors;

import com.sweeper.hardware.interfaces.BatteryPack;
import com.sweeper.hardware.state.SweeperState;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BatteryPackTest {

    BatteryPack batteryPack;

    @Before
    public void setup() {
        batteryPack = new BatteryPackImpl();
    }

	@Test
	public void testGetNumberOfBatteryUnits() {
        SweeperState.setBatteryPowerUnits(new BigDecimal("50"));
        assertEquals(new BigDecimal("50"), batteryPack.getNumberOfBatteryUnits());
	}

}
