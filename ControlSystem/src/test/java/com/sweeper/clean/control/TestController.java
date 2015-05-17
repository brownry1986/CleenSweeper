package com.sweeper.clean.control;

import com.sweeper.clean.ActionFactory;
import com.sweeper.clean.DecisionFactory;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.IDecision;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.State;
import com.sweeper.clean.control.nav.NavigationSubController;
import com.sweeper.clean.control.nav.ret.ReturnToChargerController;
import com.sweeper.clean.control.nav.scan.InitializationScan;
import com.sweeper.clean.control.nav.scan.ScanLocation;
import com.sweeper.clean.control.vacuum.IsBinFull;
import com.sweeper.clean.control.vacuum.IsDirty;
import com.sweeper.clean.control.vacuum.IsReturnToCharger;
import com.sweeper.clean.control.vacuum.IsReturnedToCharger;
import com.sweeper.hardware.interfaces.*;
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
public class TestController extends Controller {

	Mockery context;

	HardwareInterfacePack hardwarePack;

	BatteryPack batteryPack;

	CleaningApparatus cleaningApparatus;

	DirtSensor dirtSensor;

	MovementApparatus movementApparatus;

	SurfaceSensor surfaceSensor;
	
	ObstacleSensor obstacleSensor;

	ChargingStation chargingStation;

	ActionFactory actionFactory;
	
	DecisionFactory decisionFactory;
	
	NavigationSubController navigationSubController;
	
	ReturnToChargerController returnToChargerController;
	
	BatteryPack batterySensor;
	
	InitializationScan initializationScan;
	
	ScanLocation scanLocation;
	
	IsEndOfCleanCycle isEndOfCleanCycle;
	
	IsDirty isDirty;
	
	IsBinFull isBinFull;

    IDecision isReturnToCharger;

    IDecision isReturnedToCharger;

	Sequence isEndOfCleanCycleSequence;
	
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

		isEndOfCleanCycleSequence = context.sequence("end-cycle-calls");
		
		hardwarePack = null;
		
		batteryPack = null;
		cleaningApparatus= null;
		dirtSensor= null;
		movementApparatus= null;
		surfaceSensor= null;
		obstacleSensor = null;
		chargingStation = null;

		actionFactory = null;
		decisionFactory = null;
		
		initializationScan = null;
		scanLocation = null;
		isEndOfCleanCycle = null;
		isDirty = null;
		isBinFull = null;
        isReturnToCharger = null;
        isReturnedToCharger = null;
	}
	
	
	
	@Override
	protected NavigationSubController getNavigationController() {
		return navigationSubController;
	}

	@Override
	protected ReturnToChargerController getReturnChargerController() {
		return returnToChargerController;
	}

	@Override
	protected ActionFactory getActionFactory() {
		return actionFactory;
	}

	@Override
	protected DecisionFactory getDecisionFactory() {
		return decisionFactory;
	}

	@Test(expected = ControllerException.class)
	public void test_validateHardwarePack_null_param() throws Exception {
		validateHardwarePack(null);
	}
	
	@Test(expected = ControllerException.class)
	public void test_validateHardwarePack_no_battery() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
        chargingStation = context.mock(ChargingStation.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(null));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(chargingStation));

			}
		});
		
		
		validateHardwarePack(hardwarePack);
	}
	
	@Test(expected = ControllerException.class)
	public void test_validateHardwarePack_no_cleaning() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		chargingStation = context.mock(ChargingStation.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(null));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(chargingStation));

			}
		});
		
		
		validateHardwarePack(hardwarePack);
	}
	
	@Test(expected = ControllerException.class)
	public void test_validateHardwarePack_no_dirt() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		chargingStation = context.mock(ChargingStation.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(null));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(chargingStation));

			}
		});
		
		
		validateHardwarePack(hardwarePack);
	}
	
	@Test(expected = ControllerException.class)
	public void test_validateHardwarePack_no_move() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		chargingStation = context.mock(ChargingStation.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(null));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(chargingStation));

			}
		});
		
		
		validateHardwarePack(hardwarePack);
	}
	
	@Test(expected = ControllerException.class)
	public void test_validateHardwarePack_no_surface() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		chargingStation = context.mock(ChargingStation.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(null));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(chargingStation));

			}
		});
		
		
		validateHardwarePack(hardwarePack);
	}
	
	@Test(expected = ControllerException.class)
	public void test_validateHardwarePack_no_general() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		chargingStation = context.mock(ChargingStation.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(null));

			}
		});
		
		
		validateHardwarePack(hardwarePack);
	}
	

	@Test
	public void test_validateHardwarePack_successful() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batteryPack = context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		chargingStation = context.mock(ChargingStation.class);

		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batteryPack));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(chargingStation));

			}
		});
		
		
		validateHardwarePack(hardwarePack);
	}
	
	@Test
	public void test_run_check_stop_decision() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batterySensor= context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		chargingStation = context.mock(ChargingStation.class);

		actionFactory = context.mock(ActionFactory.class);
		decisionFactory = context.mock(DecisionFactory.class);
		
		initializationScan = context.mock(InitializationScan.class);
		scanLocation = context.mock(ScanLocation.class);
		isEndOfCleanCycle = context.mock(IsEndOfCleanCycle.class);
		isDirty = context.mock(IsDirty.class);
		isBinFull  = context.mock(IsBinFull.class);
        isReturnToCharger = context.mock(IsReturnToCharger.class);
        isReturnedToCharger = context.mock(IsReturnedToCharger.class);

		returnToChargerController = context.mock(ReturnToChargerController.class);
		
		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batterySensor));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(chargingStation));

				oneOf(actionFactory).getInitScan();
				will(returnValue(initializationScan));
				
				oneOf(initializationScan).execute(with(any(HardwareInterfacePack.class)), with(any(State.class)));
				
				oneOf(actionFactory).getScanLocation();
				will(returnValue(scanLocation));
				
				oneOf(decisionFactory).getIsEndOfCleanCycle();
				will(returnValue(isEndOfCleanCycle));
				
				oneOf(decisionFactory).getIsDirty();
				will(returnValue(isDirty));
				
				oneOf(decisionFactory).getIsBinFull();
				will(returnValue(isBinFull));
				
				oneOf(decisionFactory).getIsReturnToCharger();
				will(returnValue(isReturnToCharger));

				oneOf(decisionFactory).getIsReturnedToCharger();
				will(returnValue(isReturnedToCharger));

				oneOf(isEndOfCleanCycle).decide(with(any(HardwareInterfacePack.class)), with(any(State.class)));
				will(returnValue(true));

                oneOf(isReturnedToCharger).decide(with(any(HardwareInterfacePack.class)), with(any(State.class)));
                will(returnValue(true));
				
			}
		});
		
		setHardwarePack(hardwarePack);
		run();
	}
	
	@Test
	public void  test_run_check_bin_full_decision() throws Exception {
		
		hardwarePack = context.mock(HardwareInterfacePack.class);
		batterySensor= context.mock(BatteryPack.class);
		cleaningApparatus= context.mock(CleaningApparatus.class);
		dirtSensor= context.mock(DirtSensor.class);
		movementApparatus= context.mock(MovementApparatus.class);
		surfaceSensor= context.mock(SurfaceSensor.class);
		obstacleSensor = context.mock(ObstacleSensor.class);
		chargingStation = context.mock(ChargingStation.class);

		actionFactory = context.mock(ActionFactory.class);
		decisionFactory = context.mock(DecisionFactory.class);
		
		initializationScan = context.mock(InitializationScan.class);
		scanLocation = context.mock(ScanLocation.class);
		isEndOfCleanCycle = context.mock(IsEndOfCleanCycle.class);
		isDirty = context.mock(IsDirty.class);
		isBinFull  = context.mock(IsBinFull.class);
        isReturnToCharger = context.mock(IsReturnToCharger.class);
        isReturnedToCharger = context.mock(IsReturnedToCharger.class);

		returnToChargerController = context.mock(ReturnToChargerController.class);
		
		context.checking(new Expectations() {
			{
				allowing(hardwarePack).getBatteryPack();
				will(returnValue(batterySensor));
				
				allowing(hardwarePack).getCleaningApparatus();
				will(returnValue(cleaningApparatus));
				
				allowing(hardwarePack).getDirtSensor();
				will(returnValue(dirtSensor));
				
				allowing(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				allowing(hardwarePack).getSurfaceSensor();
				will(returnValue(surfaceSensor));
				
				allowing(hardwarePack).getObstacleSensor();
				will(returnValue(obstacleSensor));
				
				allowing(hardwarePack).getChargingStation();
				will(returnValue(chargingStation));

				oneOf(actionFactory).getInitScan();
				will(returnValue(initializationScan));
				oneOf(initializationScan).execute(with(any(HardwareInterfacePack.class)), with(any(State.class)));
				
				oneOf(actionFactory).getScanLocation();
				will(returnValue(scanLocation));
				
				oneOf(scanLocation).execute(with(any(HardwareInterfacePack.class)), with(any(State.class)));
				
				oneOf(decisionFactory).getIsEndOfCleanCycle();
				will(returnValue(isEndOfCleanCycle));
				
				oneOf(decisionFactory).getIsDirty();
				will(returnValue(isDirty));
				
				oneOf(decisionFactory).getIsBinFull();
				will(returnValue(isBinFull));
				
				oneOf(decisionFactory).getIsReturnToCharger();
				will(returnValue(isReturnToCharger));

				oneOf(decisionFactory).getIsReturnedToCharger();
				will(returnValue(isReturnedToCharger));

				oneOf(isEndOfCleanCycle).decide(with(any(HardwareInterfacePack.class)), with(any(State.class)));
				inSequence(isEndOfCleanCycleSequence);
				will(returnValue(false));
				
				oneOf(isBinFull).decide(with(any(HardwareInterfacePack.class)), with(any(State.class)));
				will(returnValue(true));

                oneOf(isReturnedToCharger).decide(with(any(HardwareInterfacePack.class)), with(any(State.class)));
                will(returnValue(false));
				oneOf(returnToChargerController).returnToStation(with(any(HardwareInterfacePack.class)), with(any(State.class)));
								
				oneOf(isEndOfCleanCycle).decide(with(any(HardwareInterfacePack.class)), with(any(State.class))); 
				inSequence(isEndOfCleanCycleSequence);
				will(returnValue(true));

                oneOf(isReturnedToCharger).decide(with(any(HardwareInterfacePack.class)), with(any(State.class)));
                will(returnValue(true));

            }
		});
		
		setHardwarePack(hardwarePack);
		run();
	}
}

