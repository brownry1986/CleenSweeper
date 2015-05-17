package com.sweeper.clean.control.nav.scan;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.FloorCellType;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.DirtSensor;
import com.sweeper.hardware.interfaces.ObstacleSensor;
import com.sweeper.hardware.interfaces.SurfaceSensor;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class TestInitializationScan extends InitializationScan {

	Mockery context;

	HardwareInterfacePack hardwareInterfacePack;

	ObstacleSensor obstacleSensor;

	SurfaceSensor surfaceSensor;

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
		obstacleSensor = null;
		surfaceSensor = null;
		dirtSensor = null;
	}

	@Test(expected = ControllerException.class)
	public void test_executeImpl_null_hardwarePack() throws Exception {

		State state = new State();
		executeImpl(null, state);
		fail("No Exception");
	}

	@Test(expected = ControllerException.class)
	public void test_executeImpl_null_state() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);

		executeImpl(hardwareInterfacePack, null);
		fail("No Exception");
	}

	@Test(expected = ControllerException.class)
	public void test_executeImpl_obstacleSensor_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(null));
			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
		fail("No Exception");
	}

	@Test
	public void test_executeImpl_obstacleSensor_reading_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(null));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(null));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(null));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(null));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0, true, ObstacleType.UNKNOWN, ObstacleType.UNKNOWN,
				ObstacleType.UNKNOWN, ObstacleType.UNKNOWN, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.CLEAN);
		
		assertThat(expected, equalTo(state.getCurrentFloorCell()));

	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_surface_sensor_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(null));

				never(hardwareInterfacePack).getDirtSensor();
				
				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.UNKNOWN));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.STAIRS));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OBSTACLE));

				never(surfaceSensor).getSurfaceType();

				never(dirtSensor).isDirty();

			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
		fail("No Exception");
	}

	@Test(expected = ControllerException.class)
	public void test_executeImpl_dirt_sensor_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(null));
				
				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.UNKNOWN));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.STAIRS));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OBSTACLE));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				never(dirtSensor).isDirty();

			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
		fail("No Exception");

	}
	
	@Test
	public void test_executeImpl_normal_operation() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.UNKNOWN));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.STAIRS));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OBSTACLE));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0, true, ObstacleType.UNKNOWN, ObstacleType.OPEN,
				ObstacleType.STAIRS, ObstacleType.OBSTACLE, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.CLEAN);
		
		assertThat(expected, equalTo(state.getCurrentFloorCell()));
		assertThat(expected, equalTo(state.getFloorCell(new Point(0,0))));
	}
	
	@Test
	public void test_executeImpl_surface_sensor_return_null() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.UNKNOWN));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.STAIRS));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OBSTACLE));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(null));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0, true, ObstacleType.UNKNOWN, ObstacleType.OPEN,
				ObstacleType.STAIRS, ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR,
				FloorCellState.CLEAN);
		
		assertThat(expected, equalTo(state.getCurrentFloorCell()));
		assertThat(expected, equalTo(state.getFloorCell(new Point(0,0))));
	}
	
	@Test
	public void test_executeImpl_back_open() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));

				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));

				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.UNKNOWN));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.STAIRS));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OBSTACLE));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0, true, ObstacleType.UNKNOWN, ObstacleType.OPEN,
				ObstacleType.STAIRS, ObstacleType.OBSTACLE, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.CLEAN);

		assertThat(expected, equalTo(state.getCurrentFloorCell()));
		assertThat(expected, equalTo(state.getFloorCell(new Point(0, 0))));
		assertThat(FloorCellType.VISITED, equalTo(state.getFloorCell(new Point(0, 0)).getType()));

        /*
		FloorCell backCell = new FloorCell(0, -1);
		assertThat(backCell, equalTo(state.getFloorCell(new Point(0, -1))));
		assertThat(FloorCellType.UNKNOWN, equalTo(state.getFloorCell(new Point(0, -1)).getType()));

		FloorCell forwardCell = state.getFloorCell(new Point(0, 1));
		assertNull(forwardCell);

		FloorCell rightCell = state.getFloorCell(new Point(1, 0));
		assertNull(rightCell);

		FloorCell leftCell = state.getFloorCell(new Point(-1, 0));
		assertNull(leftCell);
        */
	}

	@Test
	public void test_executeImpl_forward_open() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));

				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));

				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.UNKNOWN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.STAIRS));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OBSTACLE));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0, true,  ObstacleType.OPEN, ObstacleType.UNKNOWN,
				ObstacleType.STAIRS, ObstacleType.OBSTACLE, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.CLEAN);

		assertThat(new Point(0, 0), equalTo(state.getCurrentLocation()));
		assertThat(expected, equalTo(state.getCurrentFloorCell()));
		assertThat(expected, equalTo(state.getFloorCell(new Point(0, 0))));

//		assertThat(new FloorCell(0, 1), equalTo(state.getFloorCell(new Point(0, 1))));

		assertNull(state.getFloorCell(new Point(0, -1)));
		assertNull(state.getFloorCell(new Point(1, 0)));
		assertNull(state.getFloorCell(new Point(-1, 0)));

	}

	@Test
	public void test_executeImpl_left_open() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));

				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));

				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.OBSTACLE));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.UNKNOWN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.STAIRS));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();
	
		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0, true,  ObstacleType.OBSTACLE, ObstacleType.UNKNOWN,
				ObstacleType.STAIRS, ObstacleType.OPEN, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.CLEAN);

		assertThat(new Point(0, 0), equalTo(state.getCurrentLocation()));
		assertThat(expected, equalTo(state.getCurrentFloorCell()));
		assertThat(expected, equalTo(state.getFloorCell(new Point(0, 0))));

//		assertThat(new FloorCell(-1, 0), equalTo(state.getFloorCell(new Point(-1, 0))));

		assertNull(state.getFloorCell(new Point(0, 1)));
		assertNull(state.getFloorCell(new Point(0, -1)));
		assertNull(state.getFloorCell(new Point(1, 0)));

	}

	@Test
	public void test_executeImpl_right_open() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));

				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));

				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.OBSTACLE));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.UNKNOWN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.STAIRS));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0, true,  ObstacleType.OBSTACLE, ObstacleType.UNKNOWN,
				ObstacleType.OPEN, ObstacleType.STAIRS, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.CLEAN);

		assertThat(expected, equalTo(state.getCurrentFloorCell()));
		assertThat(expected, equalTo(state.getFloorCell(new Point(0, 0))));

//		assertThat(new FloorCell(1, 0), equalTo(state.getFloorCell(new Point(1, 0))));

		assertNull(state.getFloorCell(new Point(0, 1)));
		assertNull(state.getFloorCell(new Point(0, -1)));
		assertNull(state.getFloorCell(new Point(-1, 0)));

	}

	@Test
	public void test_executeImpl_all_open() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));

				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));

				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();

		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0,  true,  ObstacleType.OPEN, ObstacleType.OPEN,
				ObstacleType.OPEN, ObstacleType.OPEN, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.CLEAN);

		assertThat(expected, equalTo(state.getCurrentFloorCell()));
		assertThat(expected, equalTo(state.getFloorCell(new Point(0,0))));

        /*
		assertThat(new FloorCell(1, 0), equalTo(state.getFloorCell(new Point(1, 0))));
		assertThat(new FloorCell(-1, 0), equalTo(state.getFloorCell(new Point(-1, 0))));
		assertThat(new FloorCell(0, -1), equalTo(state.getFloorCell(new Point(0, -1))));
		assertThat(new FloorCell(0, 1), equalTo(state.getFloorCell(new Point(0, 1))));
        */
	}
	
	@Test
	public void test_executeImpl_neighboring_cells_visted() throws Exception {

		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		surfaceSensor = context.mock(SurfaceSensor.class);
		dirtSensor = context.mock(DirtSensor.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getObstacleSensor();
				will(returnValue(obstacleSensor));

				oneOf(hardwareInterfacePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));

				oneOf(hardwareInterfacePack).getDirtSensor();
				will(returnValue(dirtSensor));

				oneOf(obstacleSensor).getForwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getBackwardSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getRightSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(obstacleSensor).getLeftSensorQuery();
				will(returnValue(ObstacleType.OPEN));

				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));

				oneOf(dirtSensor).isDirty();
				will(returnValue(false));
			}
		});

		State state = new State();

		state.addFloorCell(new Point(1, 0), new FloorCell(1, 0, ObstacleType.OPEN,
				ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN,
				SurfaceType.LOW_PILE_CARPET, FloorCellState.CLEAN));
		
		state.addFloorCell(new Point(-1, 0), new FloorCell(-1, 0, ObstacleType.OPEN,
				ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN,
				SurfaceType.LOW_PILE_CARPET, FloorCellState.CLEAN));

		state.addFloorCell(new Point(0, 1), new FloorCell(0, 1, ObstacleType.OPEN,
				ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN,
				SurfaceType.LOW_PILE_CARPET, FloorCellState.CLEAN));
		
		state.addFloorCell(new Point(0, -1), new FloorCell(0, -1, ObstacleType.OPEN,
				ObstacleType.OPEN, ObstacleType.OPEN, ObstacleType.OPEN,
				SurfaceType.LOW_PILE_CARPET, FloorCellState.CLEAN));
		
		executeImpl(hardwareInterfacePack, state);
//		assertThat(new Point(0, 0), equalTo(state.getChargingStationLocation()));

		FloorCell expected = new FloorCell(0, 0, true, ObstacleType.OPEN, ObstacleType.OPEN,
				ObstacleType.OPEN, ObstacleType.OPEN, SurfaceType.LOW_PILE_CARPET,
				FloorCellState.CLEAN);

		assertThat(new Point(0, 0), equalTo(state.getCurrentLocation()));
		assertThat(expected, equalTo(state.getCurrentFloorCell()));
		assertThat(expected, equalTo(state.getFloorCell(new Point(0, 0))));

		assertThat(new FloorCell(1, 0), not(equalTo(state.getFloorCell(new Point(1, 0)))));
		assertThat(new FloorCell(-1, 0), not(equalTo(state.getFloorCell(new Point(-1, 0)))));
		assertThat(new FloorCell(0, 1), not(equalTo(state.getFloorCell(new Point(0, 1)))));
		assertThat(new FloorCell(0, -1), not(equalTo(state.getFloorCell(new Point(0, -1)))));

	}

	
}
