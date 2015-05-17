package com.sweeper.clean;

import com.sweeper.hardware.interfaces.*;
import com.sweeper.hardware.interfaces.BatteryPack;


/**
 * Container for all the hardware interface instances.
 * 
 * @author Dave
 *
 */
public class HardwareInterfacePack {

	private BatteryPack batteryPack;

	private CleaningApparatus cleaningApparatus;

	private DirtSensor dirtSensor;

	private MovementApparatus movementApparatus;

	private SurfaceSensor surfaceSensor;
	
	private ObstacleSensor obstacleSensor;

	private ChargingStation chargingStation;

	public DirtSensor getDirtSensor() {
		return dirtSensor;
	}

	public void setDirtSensor(DirtSensor dirtSensor) {
		this.dirtSensor = dirtSensor;
	}

	public BatteryPack getBatteryPack() {
		return batteryPack;
	}

	public void setBatteryPack(BatteryPack batteryPack) {
		this.batteryPack = batteryPack;
	}

	public CleaningApparatus getCleaningApparatus() {
		return cleaningApparatus;
	}

	public void setCleaningApparatus(CleaningApparatus cleaningApparatus) {
		this.cleaningApparatus = cleaningApparatus;
	}

	public MovementApparatus getMovementApparatus() {
		return movementApparatus;
	}

	public void setMovementApparatus(MovementApparatus movementApparatus) {
		this.movementApparatus = movementApparatus;
	}

	public SurfaceSensor getSurfaceSensor() {
		return surfaceSensor;
	}

	public void setSurfaceSensor(SurfaceSensor surfaceSensor) {
		this.surfaceSensor = surfaceSensor;
	}

	public ObstacleSensor getObstacleSensor() {
		return obstacleSensor;
	}

	public void setObstacleSensor(ObstacleSensor obstacleSensor) {
		this.obstacleSensor = obstacleSensor;
	}

    public ChargingStation getChargingStation() {
        return chargingStation;
    }

    public void setChargingStation(ChargingStation chargingStation) {
        this.chargingStation = chargingStation;
    }

}
