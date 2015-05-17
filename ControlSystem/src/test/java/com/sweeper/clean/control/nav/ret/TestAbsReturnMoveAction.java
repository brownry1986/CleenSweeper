package com.sweeper.clean.control.nav.ret;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;
import com.sweeper.clean.control.nav.MovePowerForcast;
import com.sweeper.hardware.interfaces.BatteryPack;

@RunWith(JMock.class)
public class TestAbsReturnMoveAction extends AbsReturnMoveAction {

	Mockery context;

	MovePowerForcast movePowerForcast;

	HardwareInterfacePack hardwarePack;

	State state;
	
	BatteryPack batteryPack;

	Point futurePoint;
	
	Point currentLocation; 

	boolean moveCalled = false;
	
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
		batteryPack = null;
		movePowerForcast= null;
		
		futurePoint = null;
		currentLocation = null;
	}
	

	@Override
	protected Point getFuturePoint(Point currentLocation) throws ControllerException {
		return futurePoint;
	}

	@Override
	protected void move(HardwareInterfacePack sensors) throws ControllerException {
		moveCalled = true;
	}


	@Override
	protected MovePowerForcast getMovePowerForcast() {
		return movePowerForcast;
	}

	@Test
	public void test_getMovePowerForcast() {
		assertNotNull(super.getMovePowerForcast());
	}

	@Test(expected = ControllerException.class)
	public void test_getPowerRequired_null_state() throws ControllerException{
		super.getPowerRequired(null);
	}
	
	@Test(expected = ControllerException.class)
	public void test_getPowerRequired_null_current_loc() throws ControllerException{
		
		state = context.mock(State.class);
	
		context.checking(new Expectations() {
			{

				oneOf(state).getCurrentLocation();
				will(returnValue(null));
			}
		});
		
		super.getPowerRequired(state);
	}
	
	@Test(expected = ControllerException.class)
	public void test_getPowerRequired_future_point_null() throws ControllerException{
		
		state = context.mock(State.class);
		movePowerForcast = context.mock(MovePowerForcast.class);
		futurePoint = null;
	
		context.checking(new Expectations() {
			{

				exactly(2).of(state).getCurrentLocation();
				will(returnValue(new Point(-1,4)));
				
				never(movePowerForcast).getPowerRequired(with(any(State.class)), with(any(Point.class)));
			}
		});
		
		getPowerRequired(state);
	}
	
	@Test
	public void test_getPowerRequired_normal() throws ControllerException{
		
		state = context.mock(State.class);
		movePowerForcast = context.mock(MovePowerForcast.class);
		futurePoint = new Point(0,4);
	
		context.checking(new Expectations() {
			{

				exactly(2).of(state).getCurrentLocation();
				will(returnValue(new Point(-1,4)));
				
				oneOf(movePowerForcast).getPowerRequired(state, futurePoint);
				will(returnValue(4.0));
			}
		});
		
		double result = getPowerRequired(state);
		assertThat(result, equalTo(4.0));
	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_null_sensors() throws Exception{
		execute(null, null);
	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_null_state() throws Exception{
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		
		execute(hardwarePack, null);
	}
	

	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_currentLocation_null() throws Exception{
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		state = context.mock(State.class);
		
		context.checking(new Expectations() {
			{
				
				oneOf(state).getCurrentLocation();
				will(returnValue(null));
			}
		});
		
		execute(hardwarePack, state);
	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_futureLocation_null() throws Exception{
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		state = context.mock(State.class);
		
		futurePoint = null;
		currentLocation = new Point(-1,-4);
		
		context.checking(new Expectations() {
			{

				oneOf(state).getCurrentLocation();
				will(returnValue(currentLocation));
			}
		});
		
		execute(hardwarePack, state);
	}

	
}
