package com.sweeper.hardware.interfaces;

import java.math.BigDecimal;

public interface BatteryPack {

	BigDecimal getNumberOfBatteryUnits();

    void recharge();
}
