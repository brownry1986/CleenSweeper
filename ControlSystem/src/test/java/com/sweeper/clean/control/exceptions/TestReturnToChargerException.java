package com.sweeper.clean.control.exceptions;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sweeper.clean.control.exceptions.ReturnToChargerException;

public class TestReturnToChargerException {

	@Test
	public void test_constructor_no_param() throws Exception {
		ReturnToChargerException exp = new ReturnToChargerException();
		assertNull(exp.getMessage());
		
	}
	
}
