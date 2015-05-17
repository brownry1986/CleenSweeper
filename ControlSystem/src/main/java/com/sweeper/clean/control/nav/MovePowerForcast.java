package com.sweeper.clean.control.nav;

import java.awt.Point;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.SurfaceType;

public class MovePowerForcast {

	private static final double HIGH_PILE_UNITS = 3;
	private static final double LOW_PILE_UNITS = 2;
	private static final double BARE_FLOOR_UNITS = 1;

	public double getPowerRequired(State sweeperState, Point nextLocation) {

		if (sweeperState == null) {
			return HIGH_PILE_UNITS;
		}

		if (nextLocation == null) {
			return HIGH_PILE_UNITS;
		}

		SurfaceType toSurface = null;
		SurfaceType fromSurface = null;
		try {
			Point currentLoc = sweeperState.getCurrentLocation();
			if(currentLoc == null){
				return HIGH_PILE_UNITS;
			}
			
			FloorCell currentCell = sweeperState.getFloorCell(currentLoc);
			if(currentCell == null){
				return HIGH_PILE_UNITS;
			}

			fromSurface = currentCell.getSurfaceType();
			toSurface = null;

			FloorCell nextCell = sweeperState.getFloorCell(nextLocation);
			if (nextCell == null) {
				return HIGH_PILE_UNITS;
			} else {
				toSurface = nextCell.getSurfaceType();
			}
			
		} catch (ControllerException e) {
			return HIGH_PILE_UNITS;
		}

		return checkSurfaces(fromSurface, toSurface);

	}

	protected double checkSurfaces(SurfaceType fromSurface, SurfaceType toSurface) {

		if (SurfaceType.BARE_FLOOR.equals(fromSurface)) {
			if (SurfaceType.BARE_FLOOR.equals(toSurface)) {
				return BARE_FLOOR_UNITS;
			} else if (SurfaceType.LOW_PILE_CARPET.equals(toSurface)) {
				return (BARE_FLOOR_UNITS + LOW_PILE_UNITS) / 2;
			} else {
				return (BARE_FLOOR_UNITS + HIGH_PILE_UNITS) / 2;
			}
		} else if (SurfaceType.LOW_PILE_CARPET.equals(fromSurface)) {
			if (SurfaceType.BARE_FLOOR.equals(toSurface)) {
				return (LOW_PILE_UNITS + BARE_FLOOR_UNITS) / 2;
			} else if (SurfaceType.LOW_PILE_CARPET.equals(toSurface)) {
				return LOW_PILE_UNITS;
			} else {
				return (LOW_PILE_UNITS + HIGH_PILE_UNITS) / 2;
			}
		} else {
			if (SurfaceType.BARE_FLOOR.equals(toSurface)) {
				return (HIGH_PILE_UNITS + BARE_FLOOR_UNITS) / 2;
			} else if (SurfaceType.LOW_PILE_CARPET.equals(toSurface)) {
				return (HIGH_PILE_UNITS + LOW_PILE_UNITS) / 2;
			} else {
				return HIGH_PILE_UNITS;
			}
		}
	}

}
