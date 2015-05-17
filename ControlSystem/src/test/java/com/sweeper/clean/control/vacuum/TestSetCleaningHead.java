package com.sweeper.clean.control.vacuum;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import com.sweeper.hardware.enumerations.SurfaceType;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.CleaningHead;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.interfaces.SurfaceSensor;

@RunWith(JMock.class)
public class TestSetCleaningHead {

	Mockery context;

	HardwareInterfacePack hardwarePack;

	State state;

	SurfaceSensor surfaceSensor;

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

		hardwarePack = null;
		state = null;
		surfaceSensor = null;
	}
	
	@Test(expected = ControllerException.class)
	public void test_execute_sensor_pack_null() throws Exception {
		state = context.mock(State.class);

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(null, state);
	}

	@Test(expected = ControllerException.class)
	public void test_execute_sensor_null() throws Exception {

		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);

		context.checking(new Expectations() {
			{
				oneOf(hardwarePack).getSurfaceSensor();
				will(returnValue(null));
			}
		});

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(hardwarePack, state);
	}

	@Test(expected = ControllerException.class)
	public void test_execute_state_null() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(hardwarePack, null);
	}
	
	@Test
	public void test_execute_switch_bare_floor() throws Exception {

		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		surfaceSensor = context.mock(SurfaceSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.BARE_FLOOR));
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.HIGH_PILE_CARPET));
				
				oneOf(state).setCleaningHead(CleaningHead.BARE_FLOOR);
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.BARE_FLOOR));
				
				oneOf(state).log("Surface Detected: BARE_FLOOR Head Set To: BARE_FLOOR");
			}
		});

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(hardwarePack, state);
	}
	
	@Test
	public void test_execute_switch_high_pile() throws Exception {

		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		surfaceSensor = context.mock(SurfaceSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.HIGH_PILE_CARPET));
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.BARE_FLOOR));
				
				oneOf(state).setCleaningHead(CleaningHead.HIGH_PILE_CARPET);
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.HIGH_PILE_CARPET));
				
				oneOf(state).log("Surface Detected: HIGH_PILE_CARPET Head Set To: HIGH_PILE_CARPET");
			}
		});

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(hardwarePack, state);
	}
	
	@Test
	public void test_execute_switch_low_pile() throws Exception {

		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		surfaceSensor = context.mock(SurfaceSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.BARE_FLOOR));
				
				oneOf(state).setCleaningHead(CleaningHead.LOW_PILE_CARPET);
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.LOW_PILE_CARPET));
				
				oneOf(state).log("Surface Detected: LOW_PILE_CARPET Head Set To: LOW_PILE_CARPET");
			}
		});

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(hardwarePack, state);
	}
	
	@Test
	public void test_execute_already_bare_floor() throws Exception {

		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		surfaceSensor = context.mock(SurfaceSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.BARE_FLOOR));
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.BARE_FLOOR));
				
				never(state).setCleaningHead(CleaningHead.BARE_FLOOR);
				never(state).setCleaningHead(CleaningHead.HIGH_PILE_CARPET);
				never(state).setCleaningHead(CleaningHead.LOW_PILE_CARPET);
				never(state).setCleaningHead(null);
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.BARE_FLOOR));
				
				oneOf(state).log("Surface Detected: BARE_FLOOR Head Set To: BARE_FLOOR");
			}
		});

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(hardwarePack, state);
	}
	
	@Test
	public void test_execute_already_low_pile() throws Exception {

		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		surfaceSensor = context.mock(SurfaceSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.LOW_PILE_CARPET));
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.LOW_PILE_CARPET));
				
				never(state).setCleaningHead(CleaningHead.BARE_FLOOR);
				never(state).setCleaningHead(CleaningHead.HIGH_PILE_CARPET);
				never(state).setCleaningHead(CleaningHead.LOW_PILE_CARPET);
				never(state).setCleaningHead(null);
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.LOW_PILE_CARPET));
				
				oneOf(state).log("Surface Detected: LOW_PILE_CARPET Head Set To: LOW_PILE_CARPET");
			}
		});

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(hardwarePack, state);
	}
	
	@Test
	public void test_execute_already_high_pile() throws Exception {

		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		surfaceSensor = context.mock(SurfaceSensor.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				oneOf(surfaceSensor).getSurfaceType();
				will(returnValue(SurfaceType.HIGH_PILE_CARPET));
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.HIGH_PILE_CARPET));
				
				never(state).setCleaningHead(CleaningHead.BARE_FLOOR);
				never(state).setCleaningHead(CleaningHead.HIGH_PILE_CARPET);
				never(state).setCleaningHead(CleaningHead.LOW_PILE_CARPET);
				never(state).setCleaningHead(null);
				
				oneOf(state).getCleaningHead();
				will(returnValue(CleaningHead.HIGH_PILE_CARPET));
				
				oneOf(state).log("Surface Detected: HIGH_PILE_CARPET Head Set To: HIGH_PILE_CARPET");
			}
		});

		SetCleaningHead action = new SetCleaningHead();
		action.executeImpl(hardwarePack, state);
	}
	
	@Test
	public void test_getPowerRequired() throws Exception{
		
		state = context.mock(State.class);
		SetCleaningHead action = new SetCleaningHead();
	
		double result = action.getPowerRequired(state);
		assertThat((double) 0, equalTo(result));
	}
	
	@Test
	public void test_getActivityLogMsg() throws Exception {
		SetCleaningHead action = new SetCleaningHead();
		assertNotNull(action.getActivityInitLogMsg());
	}
	
}
