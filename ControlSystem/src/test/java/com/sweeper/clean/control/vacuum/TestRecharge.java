package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.interfaces.BatteryPack;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class TestRecharge extends Recharge {

	Mockery context;

	HardwareInterfacePack hardwareInterfacePack;
	
	BatteryPack batteryPack;
	
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
		batteryPack = null;
	}
	
	@Test
	public void test_getPowerRequired() throws Exception{
		assertThat((double) 0, equalTo(getPowerRequired(null)));
	}
	
	@Test
	public void test_getActivityInitLogMsg() throws Exception {
		assertNotNull(getActivityInitLogMsg());
	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_hardwardSensors_null() throws Exception {
		
		State state = new State();
		executeImpl(null, state);
	}
	
	@Test(expected = ControllerException.class)
	public void test_executeImpl_batterypack_null() throws Exception {
		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		State state = new State();

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getBatteryPack();
				will(returnValue(null));
			}
		});
		
		executeImpl(hardwareInterfacePack, state);
	}
	
	@Test
	public void test_executeImpl_successfull() throws Exception {
		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);

		State state = new State();

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwareInterfacePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).recharge();
			}
		});
		
		executeImpl(hardwareInterfacePack, state);
	}
	
}
