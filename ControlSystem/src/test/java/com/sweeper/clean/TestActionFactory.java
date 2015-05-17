package com.sweeper.clean;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sweeper.clean.control.nav.MoveBackward;
import com.sweeper.clean.control.nav.MoveForward;
import com.sweeper.clean.control.nav.MoveLeft;
import com.sweeper.clean.control.nav.MoveRight;
import com.sweeper.clean.control.nav.ret.ReturnMoveBackward;
import com.sweeper.clean.control.nav.ret.ReturnMoveForward;
import com.sweeper.clean.control.nav.ret.ReturnMoveLeft;
import com.sweeper.clean.control.nav.ret.ReturnMoveRight;
import com.sweeper.clean.control.nav.scan.InitializationScan;
import com.sweeper.clean.control.nav.scan.ScanLocation;
import com.sweeper.clean.control.vacuum.SetCleaningHead;
import com.sweeper.clean.control.vacuum.Vacuum;

public class TestActionFactory {

	@Test
	public void test_getSetCleaningHead() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertNotNull(factory.getSetCleaningHead());
		assertTrue(factory.getSetCleaningHead() instanceof SetCleaningHead);
	}

	@Test
	public void test_getVacuum() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertNotNull(factory.getVacuum());
		assertTrue(factory.getVacuum() instanceof Vacuum);
	}

	@Test
	public void test_getBackward() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertNotNull(factory.getBackward());
		assertTrue(factory.getBackward() instanceof MoveBackward);
	}

	@Test
	public void test_getForward() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertNotNull(factory.getForward());
		assertTrue(factory.getForward() instanceof MoveForward);
	}

	@Test
	public void test_getLeft() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertNotNull(factory.getLeft());
		assertTrue(factory.getLeft() instanceof MoveLeft);
	}

	@Test
	public void test_getRight() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertNotNull(factory.getRight());
		assertTrue(factory.getRight() instanceof MoveRight);
	}

	@Test
	public void test_getScanLocation() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertNotNull(factory.getScanLocation());
		assertTrue(factory.getScanLocation() instanceof ScanLocation);
	}
	
	@Test
	public void test_getInitScan() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertNotNull(factory.getInitScan());
		assertTrue(factory.getInitScan() instanceof InitializationScan);
	}
	
	@Test
	public void test_getReturnMoveBackward() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertThat(factory.getReturnMoveBackward(), notNullValue());
		assertThat(factory.getReturnMoveBackward(), instanceOf(ReturnMoveBackward.class));
	}
	
	@Test
	public void test_getReturnMoveForward() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertThat(factory.getReturnMoveForward(), notNullValue());
		assertThat(factory.getReturnMoveForward(), instanceOf(ReturnMoveForward.class));
	}
	
	@Test
	public void test_getReturnMoveLeft() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertThat(factory.getReturnMoveLeft(), notNullValue());
		assertThat(factory.getReturnMoveLeft(), instanceOf(ReturnMoveLeft.class));
	}
	
	@Test
	public void test_getReturnMoveRight() throws Exception {
		ActionFactory factory = new ActionFactory();
		assertThat(factory.getReturnMoveRight(), notNullValue());
		assertThat(factory.getReturnMoveRight(), instanceOf(ReturnMoveRight.class));
	}

}
