package com.sweeper.hardware.util;

import com.sweeper.hardware.enumerations.SurfaceType;

import java.math.BigDecimal;

public final class Utility {

    private Utility() {}

    public static BigDecimal determinePowerForSurfaceType( SurfaceType surfaceType ) {
        if (SurfaceType.BARE_FLOOR.equals(surfaceType)) {
            return Constants.BARE_FLOOR_POWER;
        }

        if (SurfaceType.LOW_PILE_CARPET.equals(surfaceType)) {
            return Constants.LOW_PILE_CARPET_POWER;
        }

        if (SurfaceType.HIGH_PILE_CARPET.equals(surfaceType)) {
            return Constants.HIGH_PILE_CARPET_POWER;
        }

        throw new IllegalArgumentException("Invalid surface type");
    }

    public static BigDecimal determinePowerForMovement(SurfaceType oldSurfaceType, SurfaceType newSurfaceType) {
        return determinePowerForSurfaceType(oldSurfaceType).add(determinePowerForSurfaceType(newSurfaceType)).divide(new BigDecimal("2"));
    }

}
