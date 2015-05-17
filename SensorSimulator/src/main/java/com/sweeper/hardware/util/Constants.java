package com.sweeper.hardware.util;

import java.math.BigDecimal;

public final class Constants {

    private Constants() {}

    public static final BigDecimal BARE_FLOOR_POWER = BigDecimal.ONE;

    public static final BigDecimal LOW_PILE_CARPET_POWER = BARE_FLOOR_POWER.add(BigDecimal.ONE);

    public static final BigDecimal HIGH_PILE_CARPET_POWER = LOW_PILE_CARPET_POWER.add(BigDecimal.ONE);

}
