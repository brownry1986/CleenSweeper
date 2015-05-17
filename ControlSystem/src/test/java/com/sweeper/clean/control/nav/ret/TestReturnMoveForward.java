package com.sweeper.clean.control.nav.ret;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
import com.sweeper.hardware.interfaces.MovementApparatus;

@RunWith(JMock.class)
public class TestReturnMoveForward extends ReturnMoveForward {
	Mockery context;
	
	HardwareInterfacePack hardwarePack;
	
	MovementApparatus movementApparatus;
	
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
		movementApparatus = null;
		
	}
	
	@Test(expected = ControllerException.class)
	public void test_getFurturePoint_null() throws Exception {
		
		getFuturePoint(null);
		
	}

	@Test
	public void test_getFurturePoint_point() throws Exception {
		
		Point current = new Point(3,4);
		
		Point result = getFuturePoint(current);
		
		assertThat(result, equalTo(new Point(3,5)));
		
	}
	
	@Test(expected = ControllerException.class)
	public void test_move_null() throws Exception {
		
		move(null);
			
	}
	
	@Test(expected = ControllerException.class)
	public void test_move_null_movementApparatus() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		movementApparatus = null;

		context.checking(new Expectations() {
			{

				oneOf(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
			}
		});
		
		move(hardwarePack);
			
	}
	
	@Test
	public void test_move_forward() throws Exception {
		hardwarePack = context.mock(HardwareInterfacePack.class);
		movementApparatus = context.mock(MovementApparatus.class);

		context.checking(new Expectations() {
			{

				exactly(2).of(hardwarePack).getMovementApparatus();
				will(returnValue(movementApparatus));
				
				oneOf(movementApparatus).moveForward();
			}
		});
		
		move(hardwarePack);		
	}
	

}
