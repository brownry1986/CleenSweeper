package com.sweeper.clean;

import com.sweeper.hardware.interfaces.*;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JMock.class)
public class TestHardwarePack {

	Mockery context;
	
	DirtSensor dirtSensor;
	
	BatteryPack batteryPack;
	
	CleaningApparatus cleaningApparatus;
	
	MovementApparatus movementApparatus;
	
	SurfaceSensor surfaceSensor;
	
	ObstacleSensor obstacleSensor;
	
	@Before
	public void before() {
		context = new JUnit4Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		dirtSensor = null;
		batteryPack = null;
		cleaningApparatus = null;
	}
	
	@BeforeClass
	public static void beforeClass(){
	}
	
	@Test
	public void test_dirtSensor() throws Exception{
		
		dirtSensor = context.mock(DirtSensor.class);
		
		HardwareInterfacePack pack = new HardwareInterfacePack();
		pack.setDirtSensor(dirtSensor);
		
		
		assertNotNull(pack.getDirtSensor());
		assertEquals(dirtSensor, pack.getDirtSensor());
	}
	
	@Test
	public void test_batterySensor() throws Exception{
		
		batteryPack = context.mock(BatteryPack.class);
		
		HardwareInterfacePack pack = new HardwareInterfacePack();
		pack.setBatteryPack(batteryPack);
		
		
		assertNotNull(pack.getBatteryPack());
		assertEquals(batteryPack, pack.getBatteryPack());
	}
	
	
	@Test
	public void test_cleaningApparatus() throws Exception{
		
		cleaningApparatus = context.mock(CleaningApparatus.class);
		
		HardwareInterfacePack pack = new HardwareInterfacePack();
		pack.setCleaningApparatus(cleaningApparatus);
		
		
		assertNotNull(pack.getCleaningApparatus());
		assertEquals(cleaningApparatus, pack.getCleaningApparatus());
	}
	
	@Test
	public void test_movementApparatus() throws Exception{
		
		movementApparatus = context.mock(MovementApparatus.class);
		
		HardwareInterfacePack pack = new HardwareInterfacePack();
		pack.setMovementApparatus(movementApparatus);
		
		
		assertNotNull(pack.getMovementApparatus());
		assertEquals(movementApparatus, pack.getMovementApparatus());
	}
	
	@Test
	public void test_surfaceSensor() throws Exception{
		
		surfaceSensor = context.mock(SurfaceSensor.class);
		
		HardwareInterfacePack pack = new HardwareInterfacePack();
		pack.setSurfaceSensor(surfaceSensor);
		
		
		assertNotNull(pack.getSurfaceSensor());
		assertEquals(surfaceSensor, pack.getSurfaceSensor());
	}
	
	@Test
	public void test_sweeperSensorQuery() throws Exception{
		
		obstacleSensor = context.mock(ObstacleSensor.class);
		
		HardwareInterfacePack pack = new HardwareInterfacePack();
		pack.setObstacleSensor(obstacleSensor);
		
		
		assertNotNull(pack.getObstacleSensor());
		assertEquals(obstacleSensor, pack.getObstacleSensor());
	}
}
