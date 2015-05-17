package com.sweeper.clean;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sweeper.clean.DecisionFactory;
import com.sweeper.clean.control.IsEndOfCleanCycle;
import com.sweeper.clean.control.vacuum.IsDirty;



public class TestDecisionFactory {

	@Test
	public void test_getIsDirty() throws Exception {
		DecisionFactory factory = new DecisionFactory();
		assertNotNull(factory.getIsDirty());
		assertTrue(factory.getIsDirty() instanceof IsDirty);
	}
	
	@Test
	public void test_getIsEndOfCleanCycle() throws Exception {
		DecisionFactory factory = new DecisionFactory();
		assertNotNull(factory.getIsEndOfCleanCycle());
		assertTrue(factory.getIsEndOfCleanCycle() instanceof IsEndOfCleanCycle);
	}
	
}
