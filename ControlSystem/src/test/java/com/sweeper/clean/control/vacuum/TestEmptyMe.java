package com.sweeper.clean.control.vacuum;

import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.interfaces.ChargingStation;
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

public class TestEmptyMe extends EmptyMe {

	Mockery context;

	HardwareInterfacePack hardwareInterfacePack;

	ChargingStation chargingStation;

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
		chargingStation = null;
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
	public void test_executeImpl_chargingStation_null() throws Exception {
		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		State state = new State();

		context.checking(new Expectations() {
			{
				oneOf(hardwareInterfacePack).getChargingStation();
				will(returnValue(null));
			}
		});

		executeImpl(hardwareInterfacePack, state);
	}

	@Test
	public void test_executeImpl_successfull() throws Exception {
		hardwareInterfacePack = context.mock(HardwareInterfacePack.class);
		chargingStation = context.mock(ChargingStation.class);

		State state = new State();

		context.checking(new Expectations() {
			{
				exactly(2).of(hardwareInterfacePack).getChargingStation();
				will(returnValue(chargingStation));

				oneOf(chargingStation).emptyMe();
			}
		});
		
		executeImpl(hardwareInterfacePack, state);
	}
	
}
