package com.sweeper.hardware.state;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.exceptions.BatteryOutOfPowerException;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.simulator.ui.SimulatorUI;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Map;

public final class SweeperState {

    private static Map<Point, FloorCell> floorPlan;

    private static int xLocation = 0;

    private static int yLocation = 0;

    protected static final BigDecimal MAX_BATTERY_UNITS = new BigDecimal("1000");

    private static BigDecimal batteryPowerUnits = MAX_BATTERY_UNITS;

    private static final int MAX_DIRT_CAPACITY = 50;

    private static int dirtUnits = 0;

	private static SurfaceType cleaningHeadHeight = SurfaceType.BARE_FLOOR;
    
    private static long delay = 0;
    
    private static SimulatorUI ui;

    private SweeperState() {}


    public static void setFloorPlan( Map<Point, FloorCell> floorPlan ) {
        SweeperState.floorPlan = floorPlan;
    }

    public static int getXLocation() {
        return xLocation;
    }
    
    

    public static void setXLocation(int xLocation) {
        SweeperState.xLocation = xLocation;
        notify(SweeperStateActions.MOVEMENT);
        sleep(delay);
    }

    public static int getYLocation() {
        return yLocation;
    }

    public static void setYLocation(int yLocation) {
    	SweeperState.yLocation = yLocation;
    	notify(SweeperStateActions.MOVEMENT);
    	sleep(delay);
        
    }

    public static SurfaceType getSurfaceType() {
        return getCurrentFloorCell().getSurfaceType();
    }

    public static SurfaceType getCleaningHeadHeight() {
        return cleaningHeadHeight;
    }

    public static void setCleaningHeadHeight(SurfaceType cleaningHeadHeight) {
        SweeperState.cleaningHeadHeight = cleaningHeadHeight;
        notify(SweeperStateActions.CLEANING_HEAD);
        sleep(delay);
    }

    public static boolean isDirty() {
        return getCurrentFloorCell().isDirty();
    }

    public static void cleanCell() {
        getCurrentFloorCell().cleanCell();
        dirtUnits++;
        notify(SweeperStateActions.DIRT_BIN);
        notify(SweeperStateActions.CLEAN);
        sleep(delay);
    }

    public static int getRemainingDirtUnits() {
		return MAX_DIRT_CAPACITY - dirtUnits;
	}

    public static BigDecimal getBatteryPowerUnits() {
        return batteryPowerUnits;
    }

    public static void setBatteryPowerUnits(BigDecimal batteryPowerUnits) {
        SweeperState.batteryPowerUnits = batteryPowerUnits;
        notify(SweeperStateActions.BATTERY);
        sleep(delay);
    }

    public static void drainBattery( BigDecimal powerUnits ) {
        if (powerUnits.compareTo(batteryPowerUnits) >= 0) {
            batteryPowerUnits = BigDecimal.ZERO;
            throw new BatteryOutOfPowerException( "Battery ran out of power, sweeper cannot continue" );
        }
        batteryPowerUnits = batteryPowerUnits.subtract(powerUnits);
        notify(SweeperStateActions.BATTERY);
        sleep(delay);
    }

    public static void rechargeBattery() {
        batteryPowerUnits = MAX_BATTERY_UNITS;
        notify(SweeperStateActions.BATTERY);

        // Add a longer delay to simulate the time to recharge the battery
        sleep(delay * 3);
    }

    public static ObstacleType getLeftObstacleType() {
        return getCurrentFloorCell().getLeftObstacle();
    }

    public static ObstacleType getRightObstacleType() {
        return getCurrentFloorCell().getRightObstacle();
    }

    public static ObstacleType getBackwardObstacleType() {
        return getCurrentFloorCell().getBackwardObstacle();
    }

    public static ObstacleType getForwardObstacleType() {
        return getCurrentFloorCell().getForwardObstacle();
    }

    protected static FloorCell getCurrentFloorCell() {
        return floorPlan.get( new Point(xLocation, yLocation) );
    }

    public static boolean isCleaningBinFull() {
        return dirtUnits >= MAX_DIRT_CAPACITY;
    }

    public static void emptyCleaningBin() {
        notify(SweeperStateActions.EMPTY_ME);
        // Add longer delay to simulate the consumer emptying the dirt bin
        sleep(delay * 10);
        dirtUnits = 0;
        notify(SweeperStateActions.DIRT_BIN);
        sleep(delay);
    }

    public static boolean isChargingStation() {
        return getCurrentFloorCell().isChargingStation();
    }

    public static void setDelay(long milliseconds) {
    	delay = milliseconds;
    }
    
    public static void setUI(SimulatorUI simUI){
    	ui = simUI;
    }
    
    private static void sleep(long milliseconds) {
    	try {
    		Thread.sleep(milliseconds);
    	} catch (InterruptedException ie) {
            //There are no threads that can interrupt this.
            throw new RuntimeException(ie.getMessage(), ie);
        }
    }
    
    private static void notify(SweeperStateActions action){
    	if (ui != null) {
    		ui.notifyUpdate(action);
    	}
    }

}
