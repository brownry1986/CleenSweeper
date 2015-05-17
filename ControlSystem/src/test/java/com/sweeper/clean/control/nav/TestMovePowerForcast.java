package com.sweeper.clean.control.nav;

import java.awt.Point;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;

@RunWith(JMock.class)
public class TestMovePowerForcast extends MovePowerForcast {

	Mockery context;
	
	State state;
	
	Point nextLocation;
	
	Point currentLocation;
	
	FloorCell currentCell;
	
	FloorCell nextCell;
	
	@BeforeClass
	public static void beforeClass() {
	}

	@Before
	public void before() {
		context = new JUnit4Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
		
		state = null;
		nextLocation = null;
		currentLocation = null;
		currentCell = null;
		nextCell = null;
	}
	
	@Test
	public void test_checkSurfaces_null_null() throws Exception {
		double result = checkSurfaces(null, null);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_null_Bare() throws Exception {
		double result = checkSurfaces(null, SurfaceType.BARE_FLOOR);
		
		assertThat((double) 2, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_null_Low() throws Exception {
		double result = checkSurfaces(null, SurfaceType.LOW_PILE_CARPET);
		
		assertThat((double) 2.5, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_null_High() throws Exception {
		double result = checkSurfaces(null, SurfaceType.HIGH_PILE_CARPET);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_Bare_null() throws Exception {
		double result = checkSurfaces(SurfaceType.BARE_FLOOR, null);
		
		assertThat((double) 2, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_Bare_Bare() throws Exception {
		double result = checkSurfaces(SurfaceType.BARE_FLOOR, SurfaceType.BARE_FLOOR);
		
		assertThat((double) 1, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_Bare_Low() throws Exception {
		double result = checkSurfaces(SurfaceType.BARE_FLOOR, SurfaceType.LOW_PILE_CARPET);
		
		assertThat((double) 1.5, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_Bare_High() throws Exception {
		double result = checkSurfaces(SurfaceType.BARE_FLOOR, SurfaceType.HIGH_PILE_CARPET);
		
		assertThat((double) 2, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_Low_Null() throws Exception {
		double result = checkSurfaces(SurfaceType.LOW_PILE_CARPET, null);
		
		assertThat((double) 2.5, equalTo(result));
	}
	
	
	@Test
	public void test_checkSurfaces_Low_Bare() throws Exception {
		double result = checkSurfaces(SurfaceType.LOW_PILE_CARPET, SurfaceType.BARE_FLOOR);
		
		assertThat((double) 1.5, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_Low_Low() throws Exception {
		double result = checkSurfaces(SurfaceType.LOW_PILE_CARPET, SurfaceType.LOW_PILE_CARPET);
		
		assertThat((double) 2, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_Low_High() throws Exception {
		double result = checkSurfaces(SurfaceType.LOW_PILE_CARPET, SurfaceType.HIGH_PILE_CARPET );
		
		assertThat((double) 2.5, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_High_Null() throws Exception {
		double result = checkSurfaces(SurfaceType.HIGH_PILE_CARPET, null);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_High_Bare() throws Exception {
		double result = checkSurfaces(SurfaceType.HIGH_PILE_CARPET, SurfaceType.BARE_FLOOR);
		
		assertThat((double) 2, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_High_Low() throws Exception {
		double result = checkSurfaces(SurfaceType.HIGH_PILE_CARPET, SurfaceType.LOW_PILE_CARPET);
		
		assertThat((double) 2.5, equalTo(result));
	}
	
	@Test
	public void test_checkSurfaces_High_High() throws Exception {
		double result = checkSurfaces(SurfaceType.HIGH_PILE_CARPET, SurfaceType.HIGH_PILE_CARPET);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_getPowerRequired_null_null() throws Exception{
		double result = getPowerRequired(null, null);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_getPowerRequired_null_next_point() throws Exception{
		
		state = context.mock(State.class);
		
		context.checking(new Expectations() {
			{
				never(state).getCurrentLocation();
				never(state).getFloorCell(with(any(Point.class)));
			}
		});
		
		double result = getPowerRequired(state, null);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_getPowerRequired_currentLocation_null() throws Exception{
		
		state = context.mock(State.class);
		nextLocation = new Point(1,1);
		currentLocation = new Point(0,1);
		
		context.checking(new Expectations() {
			{
				oneOf(state).getCurrentLocation();
				will(returnValue(null));
			}
		});
		
		double result = getPowerRequired(state, nextLocation);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_getPowerRequired_current_cell_null() throws Exception{
		
		state = context.mock(State.class);
		nextLocation = new Point(1,1);
		currentLocation = new Point(0,1);
		
		context.checking(new Expectations() {
			{
				oneOf(state).getCurrentLocation();
				will(returnValue(currentLocation));
				
				oneOf(state).getFloorCell(currentLocation);
				will(returnValue(null));
			}
		});
		
		double result = getPowerRequired(state, nextLocation);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_getPowerRequired_current_cell_exception() throws Exception{
		
		state = context.mock(State.class);
		nextLocation = new Point(1,1);
		currentLocation = new Point(0,1);
		
		context.checking(new Expectations() {
			{
				oneOf(state).getCurrentLocation();
				will(returnValue(currentLocation));
				
				oneOf(state).getFloorCell(currentLocation);
				will(throwException(new ControllerException()));
			}
		});
		
		double result = getPowerRequired(state, nextLocation);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_getPowerRequired_next_cell_null() throws Exception{
		
		state = context.mock(State.class);
		nextLocation = new Point(1,1);
		currentLocation = new Point(0,1);
		currentCell = new FloorCell(0, 1, ObstacleType.OPEN,  ObstacleType.OPEN,  ObstacleType.OPEN,  ObstacleType.OPEN, SurfaceType.BARE_FLOOR, FloorCellState.DIRTY);
		nextCell = null;
		
		context.checking(new Expectations() {
			{
				oneOf(state).getCurrentLocation();
				will(returnValue(currentLocation));
				
				oneOf(state).getFloorCell(currentLocation);
				will(returnValue(currentCell));
				
				oneOf(state).getFloorCell(nextLocation);
				will(returnValue(null));
			}
		});
		
		double result = getPowerRequired(state, nextLocation);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_getPowerRequired_next_cell_exception() throws Exception{
		
		state = context.mock(State.class);
		nextLocation = new Point(1,1);
		currentLocation = new Point(0,1);
		currentCell = new FloorCell(0, 1, ObstacleType.OPEN,  ObstacleType.OPEN,  ObstacleType.OPEN,  ObstacleType.OPEN, SurfaceType.BARE_FLOOR, FloorCellState.DIRTY);
		nextCell = null;
		
		context.checking(new Expectations() {
			{
				oneOf(state).getCurrentLocation();
				will(returnValue(currentLocation));
				
				oneOf(state).getFloorCell(currentLocation);
				will(returnValue(currentCell));
				
				oneOf(state).getFloorCell(nextLocation);
				will(throwException(new ControllerException()));
			}
		});
		
		double result = getPowerRequired(state, nextLocation);
		
		assertThat((double) 3, equalTo(result));
	}
	
	@Test
	public void test_getPowerRequired_successfull_to_check() throws Exception{
		
		state = context.mock(State.class);
		nextLocation = new Point(1,1);
		currentLocation = new Point(0,1);
		currentCell = new FloorCell(0, 1, ObstacleType.OPEN,  ObstacleType.OPEN,  ObstacleType.OPEN,  ObstacleType.OPEN, SurfaceType.BARE_FLOOR, FloorCellState.DIRTY);
		nextCell = new FloorCell(0, 1, ObstacleType.OPEN,  ObstacleType.OPEN,  ObstacleType.OPEN,  ObstacleType.OPEN, SurfaceType.LOW_PILE_CARPET, FloorCellState.DIRTY);
		
		context.checking(new Expectations() {
			{
				oneOf(state).getCurrentLocation();
				will(returnValue(currentLocation));
				
				oneOf(state).getFloorCell(currentLocation);
				will(returnValue(currentCell));
				
				oneOf(state).getFloorCell(nextLocation);
				will(returnValue(nextCell));
			}
		});
		
		double result = getPowerRequired(state, nextLocation);
		
		assertThat((double) 1.5, equalTo(result));
	}
}
