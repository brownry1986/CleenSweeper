package com.sweeper.clean.control.nav;

import java.awt.Point;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.hardware.interfaces.MovementApparatus;

public class MoveLeft extends AbsMove {

	@Override
	protected Point getFuturePoint(Point currentLocation) throws ControllerException {
		
		if(currentLocation == null){
			throw new ControllerException("Current Point not provided.");
		}
		
		int x = (int) currentLocation.getX();
		int y = (int) currentLocation.getY();

		x--;

		return new Point(x, y);
	}

	@Override
	protected void move(HardwareInterfacePack sensors) throws ControllerException {
		
		if(sensors == null){
			throw new ControllerException("HardwareInterfacePack not provided.");
		}
		
		if(sensors.getMovementApparatus() == null){
			throw new ControllerException("MovementApparatus not provided.");
		}
		
		MovementApparatus movehardware = sensors.getMovementApparatus();
		movehardware.moveLeft();
	}

	@Override
	protected String getActivityInitLogMsg() {
		return "Moving Left";
	}

}
