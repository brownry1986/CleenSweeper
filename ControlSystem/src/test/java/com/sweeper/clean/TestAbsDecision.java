package com.sweeper.clean;

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

import com.sweeper.clean.AbsDecision;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;

@RunWith(JMock.class)
public class TestAbsDecision extends AbsDecision {

	Mockery context;

	HardwareInterfacePack hardwareInterfacePack;

	State state;
	
	boolean executeImplCalled = false;
	
	@Before
	public void before() {
		context = new JUnit4Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		hardwareInterfacePack = null;
		state = null;
		
		executeImplCalled = false;
		
	}
	
	@BeforeClass
	public static void beforeClass(){
	}
	
	
	@Override
	protected boolean decideImpl(HardwareInterfacePack sensors, State sweeperState) throws ControllerException {
		executeImplCalled = true;
		return true;
	}

	@Test(expected = ControllerException.class)
	public void test_execute_state_null() throws Exception {
		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);

		decide(hardwareInterfacePack, null);
	}
	
	@Test(expected = ControllerException.class)
	public void test_execute_sensor_null() throws Exception {
		state = context.mock(State.class);
		
		decide(null, state);
	}
	
	@Test
	public void test_execute_call_impl() throws Exception {
		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		
		context.checking(new Expectations() {
			{
				oneOf(state).log("Test");
			}
		});

		
		decide(hardwareInterfacePack, state);
		assertTrue(executeImplCalled);
	}

	@Override
	protected String getActivityLogMsg() {
		return "Test";
	}
}
