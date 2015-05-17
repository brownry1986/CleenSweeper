package com.sweeper.clean.control.memory;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;

public class TestState {

	@Test(expected = ControllerException.class)
	public void test_addFloorCell_both_null() throws Exception {
		State state = new State();
		state.addFloorCell(null, null);
		fail("No Exception Thrown");
	}

	@Test(expected = ControllerException.class)
	public void test_addFloorCell_floor_cell_null() throws Exception {

		State state = new State();
		Point point = new Point(1, 3);

		state.addFloorCell(point, null);
		fail("No Exception Thrown");
	}

	@Test
	public void test_addFloorCell_chargeingStation() throws Exception {

		State state = new State();
		Point point = new Point(1, 3);

		FloorCell cell = new FloorCell(1, 3, true, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE,
				ObstacleType.OBSTACLE, ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR,
				FloorCellState.DIRTY);
		state.addFloorCell(point, cell);
		
//		List<Point> stations = state.getChargingStationLocation();
//		station
//		assertThat(point, is(equalTo(state.getChargingStationLocation())));
		
		FloorCell result = state.getFloorCell(point);
		assertThat(cell, is(equalTo(result)));
		
	}
	
	@Test
	public void test_addFloorCell_standard() throws Exception {

		State state = new State();
		Point point = new Point(1, 3);

		FloorCell cell = new FloorCell(1, 3, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE,
				ObstacleType.OBSTACLE, ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR,
				FloorCellState.DIRTY);
		state.addFloorCell(point, cell);
		
//		assertThat(point, is(not(equalTo(state.getChargingStationLocation()))));
		
		FloorCell result = state.getFloorCell(point);
		assertThat(cell, is(equalTo(result)));
		
	}
	
	@Test
	public void test_getChargingStationLocation_loaded() throws Exception {

		State state = new State();
		Point point = new Point(1, 3);

		FloorCell cell = new FloorCell(1, 3, true, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE,
				ObstacleType.OBSTACLE, ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR,
				FloorCellState.DIRTY);
		state.addFloorCell(point, cell);
		
//		assertThat(point, is(equalTo(state.getChargingStationLocation())));		
	}
	
	@Test
	public void test_getChargingStationLocation_not_loaded() throws Exception {

		State state = new State();
	
		Point point = new Point(0,0);
		
//		assertThat(point, is(equalTo(state.getChargingStationLocation())));		
	}

	
	@Test
	public void test_CleaningHead_not_loaded() throws Exception {
		State state = new State();
		assertThat(CleaningHead.BARE_FLOOR, is(equalTo(state.getCleaningHead())));
	}
	
	@Test
	public void test_CleaningHead_loaded() throws Exception {
		State state = new State();
		state.setCleaningHead(CleaningHead.HIGH_PILE_CARPET);
		assertThat(CleaningHead.HIGH_PILE_CARPET, is(equalTo(state.getCleaningHead())));
	}
	
	@Test
	public void test_getCurrentFloorCell_loaded() throws Exception {

		State state = new State();
		Point point = new Point(1, 3);

		FloorCell cell = new FloorCell(1, 3, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE,
				ObstacleType.OBSTACLE, ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR,
				FloorCellState.DIRTY);
		state.addFloorCell(point, cell);
		state.setCurrentLocation(point);
	
		FloorCell result = state.getCurrentFloorCell();
		assertThat(cell, is(equalTo(result)));
		
	}
	
	@Test(expected = ControllerException.class)
	public void test_getCurrentFloorCell_not_loaded() throws Exception {

		State state = new State();

		state.getCurrentFloorCell();
		fail("No Exception Thrown");
	}
	
	@Test
	public void test_getCurrentLocation_loaded() throws Exception {

		State state = new State();
		Point point = new Point(1, 3);
		
		state.setCurrentLocation(point);
		
		Point currentLoc = state.getCurrentLocation();
		assertThat(point, is(equalTo(currentLoc)));
	}
	
	@Test
	public void test_getCurrentLocation_not_loaded() throws Exception {

		State state = new State();
		
		Point currentLoc = state.getCurrentLocation();
		assertThat(new Point(0, 0), is(equalTo(currentLoc)));
	}
	
	@Test(expected = ControllerException.class)
	public void test_getFloorCell_point_null() throws Exception {

		State state = new State();
		state.getFloorCell(null);
		fail("No Exception");
	}
	
	@Test
	public void test_getFloorCell_no_floor_cell() throws Exception {

		State state = new State();
		FloorCell cell = state.getFloorCell(new Point(1, 3));
		
		assertNull(cell);
	
	}
	
	@Test
	public void test_getFloorCell_loaded() throws Exception {

		State state = new State();
		Point point = new Point(1, 3);

		FloorCell cell = new FloorCell(1, 3, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE,
				ObstacleType.OBSTACLE, ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR,
				FloorCellState.DIRTY);
		state.addFloorCell(point, cell);
		state.setCurrentLocation(point);
		
		FloorCell result = state.getFloorCell(point);
		assertThat(result, is(equalTo(cell)));
	}
	
	
	@Test
	public void test_getFloorPlanIterator_loaded() throws Exception {

		State state = new State();
		Point point = new Point(1, 3);

		FloorCell cell = new FloorCell(1, 3, ObstacleType.OBSTACLE, ObstacleType.OBSTACLE,
				ObstacleType.OBSTACLE, ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR,
				FloorCellState.DIRTY);
		state.addFloorCell(point, cell);
		state.setCurrentLocation(point);
		
		Iterator<Point> iterator = state.getFloorPlanKeyIterator();
		assertThat(iterator.hasNext(), equalTo(true));
		assertThat(iterator.next(), equalTo(point));
	}
	
	@Test
	public void test_getFloorPlanIterator_not_loaded() throws Exception {

		State state = new State();
		
		Iterator<Point> iterator = state.getFloorPlanKeyIterator();
		assertThat(iterator.hasNext(), equalTo(false));
	}
	
	@Test
	public void test_log_no_entries() throws Exception{
		State state = new State();
		Iterator<String> logIt = state.getLogIterator();
		assertThat(logIt.hasNext(), equalTo(false));
	}
	
	@Test
	public void test_log_entries() throws Exception{
		State state = new State();
		state.log("Test");
		Iterator<String> logIt = state.getLogIterator();
		assertThat(logIt.hasNext(), equalTo(true));
		assertThat(logIt.next(), equalTo("Test"));
	}
	
	@Test
	public void test_removeFloorCell_null_point() throws Exception {
		State state = new State();
		state.removeFloorCell(null);	
	}
	
	@Test
	public void test_removeFloorCell() throws Exception {
		State state = new State();
		state.addFloorCell(new Point(3,4), new FloorCell(3,4));
		
		assertNotNull(state.getFloorCell(new Point(3,4)));
		assertThat(new FloorCell(3,4), equalTo(state.getFloorCell(new Point(3,4))));
		
		state.removeFloorCell(new Point(3,4));	
		assertNull(state.getFloorCell(new Point(3,4)));
	}
	
	@Test
	public void test_floorPlanSize_empty() throws Exception {
		State state = new State();
		assertThat(0, equalTo(state.floorPlanSize()));
	}
	
	@Test
	public void test_floorPlanSize_one() throws Exception {
		State state = new State();
		state.addFloorCell(new Point(3,4), new FloorCell(3,4));
		assertThat(1, equalTo(state.floorPlanSize()));
	}
}
