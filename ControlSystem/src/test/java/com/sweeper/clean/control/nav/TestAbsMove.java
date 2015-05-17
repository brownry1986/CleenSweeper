package com.sweeper.clean.control.nav;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.interfaces.BatteryPack;
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
import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class TestAbsMove extends AbsMove {

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
	protected String getActivityInitLogMsg() {
		return "Test";
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
		executeImpl(null, null);
	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_null_state() throws Exception{
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		
		executeImpl(hardwarePack, null);
	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_null_battery_sensor() throws Exception{
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		
		context.checking(new Expectations() {
			{
				oneOf(hardwarePack).getBatteryPack();
				will(returnValue(null));
			}
		});
		
		executeImpl(hardwarePack, state);
	}
	

	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_currentLocation_null() throws Exception{
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		state = context.mock(State.class);
		
		context.checking(new Expectations() {
			{
				exactly(2).of(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				oneOf(batteryPack).getNumberOfBatteryUnits();
				will(returnValue(new BigDecimal("1000")));
				
				oneOf(state).getCurrentLocation();
				will(returnValue(null));
			}
		});
		
		executeImpl(hardwarePack, state);
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
				exactly(2).of(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				oneOf(batteryPack).getNumberOfBatteryUnits();
				will(returnValue(new BigDecimal("1000")));
				
				oneOf(state).getCurrentLocation();
				will(returnValue(currentLocation));
			}
		});
		
		executeImpl(hardwarePack, state);
	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_out_of_power() throws Exception{
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		state = context.mock(State.class);
		
		context.checking(new Expectations() {
			{
				exactly(2).of(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				oneOf(batteryPack).getNumberOfBatteryUnits();
				will(returnValue(new BigDecimal("1000")));
				
				oneOf(state).getCurrentLocation();
				will(returnValue(null));
			}
		});
		
		executeImpl(hardwarePack, state);
	}
	
}
