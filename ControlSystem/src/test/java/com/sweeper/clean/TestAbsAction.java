package com.sweeper.clean;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.interfaces.BatteryPack;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestAbsAction extends AbsAction {

	Mockery context;

	HardwareInterfacePack hardwarePack;

	State state;

	BatteryPack batteryPack;

	boolean executeImplCalled = false;

	double powerRequired = 0;
	double neededToReturn = 0;

	Sequence batteryCallSeq;

	@Before
	public void before() {
		context = new JUnit4Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		hardwarePack = null;
		state = null;

		executeImplCalled = false;
		powerRequired = 0;
		neededToReturn = 0;

		batteryCallSeq = context.sequence("battery-calls");
	}

	@BeforeClass
	public static void beforeClass() {
	}

	@Override
	protected void executeImpl(HardwareInterfacePack sensors, State sweeperState)
			throws ControllerException {
		executeImplCalled = true;
	}

	@Override
	protected String getActivityInitLogMsg() {
		return "Test";
	}

	@Override
	protected double getPowerRequired(State sweeperState) throws ControllerException {
		return powerRequired;
	}

	@Test(expected = ControllerException.class)
	public void test_execute_state_null() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);

		execute(hardwarePack, null);
	}

	@Test(expected = ControllerException.class)
	public void test_execute_sensor_null() throws Exception {
		state = context.mock(State.class);

		execute(null, state);
	}

	@Test(expected = ControllerException.class)
	public void test_execute_battery_sensor_null() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(null));
			}
		});

		execute(hardwarePack, state);

	}

    /*
	@Test(expected = ControllerException.class)
	public void test_execute_battery_sensor_power_zero() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		batteryPack = context.mock(BatteryPack.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).getNumberOfBatteryUnits();
				will(returnValue(0.0));
			}
		});

		execute(hardwarePack, state);

	}

	@Test(expected = ControllerException.class)
	public void test_execute_battery_sensor_power_lt_zero() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		batteryPack = context.mock(BatteryPack.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).getNumberOfBatteryUnits();
				will(returnValue(-1.0));
			}
		});

		execute(hardwarePack, state);

	}

	@Test
	public void test_execute_power_ok() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		batteryPack = context.mock(BatteryPack.class);

		powerRequired = 5;
		neededToReturn = 5;

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(100.0));

				oneOf(state).getPowerNeededToReturn();
				will(returnValue(neededToReturn));

				oneOf(state).log("Test");

				oneOf(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(95.0));

			}
		});

		execute(hardwarePack, state);
		assertThat(executeImplCalled, is(equalTo(true)));
	}

	@Test(expected = ControllerException.class)
	public void test_execute_power_no_power_after_EXE() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		batteryPack = context.mock(BatteryPack.class);

		powerRequired = 5;
		neededToReturn = 5;

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(100.0));

				oneOf(state).getPowerNeededToReturn();
				will(returnValue(neededToReturn));

				oneOf(state).log("Test");

				oneOf(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(0.0));

			}
		});

		execute(hardwarePack, state);
	}

	@Test(expected = ReturnToChargerException.class)
	public void test_execute_power_return_eq_needed() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		batteryPack = context.mock(BatteryPack.class);

		powerRequired = 5;
		neededToReturn = 5;

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).getNumberOfBatteryUnits();
				will(returnValue(5.0));

				oneOf(state).getPowerNeededToReturn();
				will(returnValue(neededToReturn));

				never(state).log("Test");

				never(batteryPack).getNumberOfBatteryUnits();
				will(returnValue(5.0));

			}
		});

		execute(hardwarePack, state);
	}

	@Test(expected = ReturnToChargerException.class)
	public void test_execute_power_at_limit() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		batteryPack = context.mock(BatteryPack.class);

		powerRequired = 1;
		neededToReturn = 9;

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(10.0));

				oneOf(state).getPowerNeededToReturn();
				will(returnValue(neededToReturn));

				never(state).log("Test");

				never(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(10.0));

			}
		});

		execute(hardwarePack, state);
	}
	
	@Test
	public void test_execute_power_one_below_limit() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		batteryPack = context.mock(BatteryPack.class);

		powerRequired = 1;
		neededToReturn = 8;

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(10.0));

				oneOf(state).getPowerNeededToReturn();
				will(returnValue(neededToReturn));

				oneOf(state).log("Test");

				oneOf(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(9.0));

			}
		});

		execute(hardwarePack, state);
		assertThat(executeImplCalled, is(equalTo(true)));

	}
	
	@Test(expected = ReturnToChargerException.class)
	public void test_execute_power_one_above_limit() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		state = context.mock(State.class);
		batteryPack = context.mock(BatteryPack.class);

		powerRequired = 2;
		neededToReturn = 9;

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));

				oneOf(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(10.0));

				oneOf(state).getPowerNeededToReturn();
				will(returnValue(neededToReturn));

				never(state).log("Test");

				never(batteryPack).getNumberOfBatteryUnits();
				inSequence(batteryCallSeq);
				will(returnValue(10.0));

			}
		});

		execute(hardwarePack, state);
	}
    */
}
