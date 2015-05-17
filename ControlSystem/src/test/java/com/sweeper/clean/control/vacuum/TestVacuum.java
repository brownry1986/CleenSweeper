package com.sweeper.clean.control.vacuum;

import java.awt.Point;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.CleaningHead;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.CleaningApparatus;
import com.sweeper.hardware.interfaces.DirtSensor;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class TestVacuum {

	Mockery context;

	HardwareInterfacePack hardwareInterfacePack;

    CleaningApparatus cleaningApparatus;
    
    DirtSensor dirtSensor;

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
		dirtSensor = null;
	}

	@Test(expected = ControllerException.class)
	public void test_execute_null_sensors() throws Exception {
		State state = new State();

		Vacuum action = new Vacuum();
		action.executeImpl(null, state);
	}

	@Test(expected = ControllerException.class)
	public void test_execute_cleaning_apparatus_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getCleaningApparatus();
				will(returnValue(null));
			}
		});

		State state = new State();
		
		Vacuum action = new Vacuum();
		action.executeImpl(hardwareInterfacePack, state);
	}
	
	@Test(expected = ControllerException.class)
	public void test_execute_dirt_sensor_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		cleaningApparatus = context.mock(CleaningApparatus.class);
		
		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(null));
			}
		});

		State state = new State();
		
		Vacuum action = new Vacuum();
		action.executeImpl(hardwareInterfacePack, state);
	}

	@Test(expected = ControllerException.class)
	public void test_execute_null_state() throws Exception {
		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);

		Vacuum action = new Vacuum();
		action.executeImpl(hardwareInterfacePack, null);
	}

	@Test
	public void test_execute_cleaned_location() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		cleaningApparatus = context.mock(CleaningApparatus.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwareInterfacePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));

				oneOf(cleaningApparatus).vacuum();
				
				exactly(2).of(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				oneOf(dirtSensor).isDirty();
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
		
		Vacuum action = new Vacuum();
		action.executeImpl(hardwareInterfacePack, state);
		
		FloorCell resultCell = state.getCurrentFloorCell();
		assertNotNull(resultCell);
		
		assertEquals(FloorCellState.CLEAN, resultCell.getState());
	}
	
	@Test
	public void test_execute_location_still_dirty() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		cleaningApparatus = context.mock(CleaningApparatus.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwareInterfacePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));

				oneOf(cleaningApparatus).vacuum();
				
				exactly(2).of(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				oneOf(dirtSensor).isDirty();
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
		
		Vacuum action = new Vacuum();
		action.executeImpl(hardwareInterfacePack, state);
		
		FloorCell resultCell = state.getCurrentFloorCell();
		assertNotNull(resultCell);
		
		assertEquals(FloorCellState.DIRTY, resultCell.getState());
	}

	@Test(expected = ControllerException.class)
	public void test_getPowerRequired_state_null() throws Exception {

		Vacuum action = new Vacuum();
		action.getPowerRequired(null);
	}

	@Test(expected = ControllerException.class)
	public void test_getPowerRequired_cleaning_head_setting_null() throws Exception {

		State state = new State();
		state.setCleaningHead(null);
		
		Vacuum action = new Vacuum();
		action.getPowerRequired(state);
	}

	@Test
	public void test_getPowerRequired_Bare_Floor() throws Exception {

		State state = new State();
		state.setCleaningHead(CleaningHead.BARE_FLOOR);
		
		Vacuum action = new Vacuum();
		double result = action.getPowerRequired(state);

		assertThat((double) 1, equalTo(result));
	}

	@Test
	public void test_getPowerRequired_Low_Pile() throws Exception {

		
		State state = new State();
		state.setCleaningHead(CleaningHead.LOW_PILE_CARPET);

		Vacuum action = new Vacuum();
		double result = action.getPowerRequired(state);

		assertThat((double) 2, equalTo(result));
	}

	@Test
	public void test_getPowerRequired_High_Pile() throws Exception {
		
		State state = new State();
		state.setCleaningHead(CleaningHead.HIGH_PILE_CARPET);

		Vacuum action = new Vacuum();
		double result = action.getPowerRequired(state);

		assertThat((double) 3, equalTo(result));
	}

	@Test
	public void test_getActivityLogMsg() throws Exception {
		Vacuum action = new Vacuum();
		assertNotNull(action.getActivityInitLogMsg());
	}
	
}
