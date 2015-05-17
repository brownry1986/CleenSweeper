package com.sweeper.clean.control.memory;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.hardware.enumerations.Direction;
import com.sweeper.hardware.enumerations.ObstacleType;

import java.awt.*;
import java.util.*;
import java.util.List;

public class State {
	
    private Map<Point, FloorCell> floorPlan = new HashMap<Point, FloorCell>();

    private Point currentLocation = new Point(0,0);
	
	private CleaningHead cleaningHead = CleaningHead.BARE_FLOOR;
	
	private List<Point> chargingStationLocation = new ArrayList<Point>();
	
	private List<String> log = new ArrayList<String>();
	
	public State() {
		chargingStationLocation.add(new Point(0,0));
	}
	
	public Point getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Point currentLocation) {
		this.currentLocation = currentLocation;
	}

	public CleaningHead getCleaningHead() {
		return cleaningHead;
	}

	public void setCleaningHead(CleaningHead cleaningHead) {
		this.cleaningHead = cleaningHead;
	}

	public Iterator<Point> getFloorPlanKeyIterator(){
		return floorPlan.keySet().iterator();
	}
	
	public void addFloorCell(Point point, FloorCell floorCell) throws ControllerException {
		
		if (point == null) {
			throw new ControllerException("Cannot add FloorCell because Point was not provided.");
		}
		
		if (floorCell == null) {
			throw new ControllerException("Cannot add FloorCell because FloorCell was not provided.");
		}
		
		if(floorCell.isChargingStation()){
			chargingStationLocation.add(point);
		}
		
		floorPlan.put(point, floorCell);
	}
	
	public void removeFloorCell(Point point) throws ControllerException {
		
		if(point == null){
			return;
		}
		
		floorPlan.remove(point);
	}
	
	public int floorPlanSize() {
		return floorPlan.size();
	}
	
	public FloorCell getFloorCell(Point point) throws ControllerException {
		
		if(point == null){
			throw new ControllerException("No Point provided unable to retrieve floor cell");
		}

        return floorPlan.get(point);
	}

    public FloorCell getCurrentFloorCell() throws ControllerException {

		FloorCell retVal = floorPlan.get(currentLocation);
		if(retVal == null){
			throw new ControllerException("Current Location does not have a floor cell");
		}

		return  retVal;
	}

    public FloorCell getSurroundingFloorCell(FloorCell floorCell, Direction direction) throws ControllerException {
        return getSurroundingFloorCell(floorCell.getX(), floorCell.getY(), direction);
    }

    public FloorCell getSurroundingFloorCell(int x, int y, Direction direction) throws ControllerException {
        switch (direction) {
            case LEFT:
                return getLeftFloorCell(x, y);
            case FORWARD:
                return getForwardFloorCell(x, y);
            case RIGHT:
                return getRightFloorCell(x, y);
            case BACKWARD:
                return getBackwardFloorCell(x, y);
        }

        return null;
    }

	public FloorCell getLeftFloorCell(int x, int y) throws ControllerException {
        if ( getFloorCell(new Point(x, y)).getLeftObstacle() == ObstacleType.OPEN ) {
            return floorPlan.get(new Point(x - 1, y));
        }
        return null;
	}

    public FloorCell getBackwardFloorCell(int x, int y) throws ControllerException {
        if ( getFloorCell(new Point(x, y)).getBackwardObstacle() == ObstacleType.OPEN ) {
            return floorPlan.get(new Point(x, y - 1));
        }
        return null;
    }

	public FloorCell getRightFloorCell(int x, int y) throws ControllerException {
        if ( getFloorCell(new Point(x, y)).getRightObstacle() == ObstacleType.OPEN ) {
            return floorPlan.get(new Point(x + 1, y));
        }
        return null;
	}

    public FloorCell getForwardFloorCell(int x, int y) throws ControllerException {
        if ( getFloorCell(new Point(x, y)).getForwardObstacle() == ObstacleType.OPEN ) {
            return floorPlan.get(new Point(x, y + 1));
        }
        return null;
    }

	public List<Point> getChargingStationLocation(){
		return chargingStationLocation;
	}

	public Iterator<String> getLogIterator(){
		return log.iterator();
	}
	
	public void log(String message){
		log.add(message);
	}
	
}
