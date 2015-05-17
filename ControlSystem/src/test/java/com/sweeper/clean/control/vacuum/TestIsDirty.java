package com.sweeper.clean.control.vacuum;

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

import static org.junit.Assert.*;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.DirtSensor;

@RunWith(JMock.class)
public class TestIsDirty {

	Mockery context;

	HardwareInterfacePack hardwareInterfacePack;
	
	DirtSensor dirtSensorQuery;
	
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
		
		hardwareInterfacePack = null;
		dirtSensorQuery = null;
	}

	@Test(expected = ControllerException.class)
	public void test_execute_null_sensors() throws Exception {
		State state = new State();
		
		IsDirty decision = new IsDirty();
		decision.decideImpl(null, state);
	}

	@Test(expected = ControllerException.class)
	public void test_execute_dirt_sensor_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		State state = new State();

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(null));
			}
		});

		IsDirty decision = new IsDirty();
		decision.decideImpl(hardwareInterfacePack, state);
	}

	@Test
	public void test_execute_dirt_true() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		
		dirtSensorQuery = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensorQuery));
				
				oneOf(dirtSensorQuery).isDirty();
				will(returnValue(true));
			}
		});
		
		State state = new State();
		Point currentPoint = new Point(2, 5);
		
		FloorCell floorCell = new FloorCell(2, 5, ObstacleType.UNKNOWN, ObstacleType.UNKNOWN,
				ObstacleType.UNKNOWN, ObstacleType.UNKNOWN, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.DIRTY);
		state.addFloorCell(currentPoint, floorCell);
		state.setCurrentLocation(currentPoint);

		IsDirty decision = new IsDirty();
		boolean result = decision.decideImpl(hardwareInterfacePack, state);
		assertTrue(result);
		
		FloorCell resultCell = state.getCurrentFloorCell();
		assertNotNull(resultCell);
		
		assertEquals(FloorCellState.DIRTY, resultCell.getState());
	}
	
	@Test
	public void test_execute_dirt_false() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		dirtSensorQuery = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensorQuery));
				
				oneOf(dirtSensorQuery).isDirty();
				will(returnValue(false));
			}
		});
		
		State state = new State();
		Point currentPoint = new Point(2, 5);
		
		FloorCell floorCell = new FloorCell(2, 5, ObstacleType.UNKNOWN, ObstacleType.UNKNOWN,
				ObstacleType.UNKNOWN, ObstacleType.UNKNOWN, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.DIRTY);
		state.addFloorCell(currentPoint, floorCell);
		state.setCurrentLocation(currentPoint);

		IsDirty decision = new IsDirty();
		boolean result = decision.decideImpl(hardwareInterfacePack, state);
		
		assertFalse(result);
		
		FloorCell resultCell = state.getCurrentFloorCell();
		assertNotNull(resultCell);
		
		assertEquals(FloorCellState.CLEAN, resultCell.getState());
		
	}
	
	@Test(expected = ControllerException.class)
	public void test_execute_current_cell_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		dirtSensorQuery = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensorQuery));
				
				oneOf(dirtSensorQuery).isDirty();
				will(returnValue(false));
			}
		});
		
		State state = new State();
		Point currentPoint = new Point(2, 5);
		state.setCurrentLocation(currentPoint);

		IsDirty decision = new IsDirty();
		decision.decideImpl(hardwareInterfacePack, state);
				
	}
	
}
