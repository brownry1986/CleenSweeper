package com.sweeper.hardware.exceptions;

public class BatteryOutOfPowerException extends RuntimeException {

    public BatteryOutOfPowerException(String message) {
        super(message);
    }
}
