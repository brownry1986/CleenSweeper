package com.sweeper.clean.control.exceptions;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sweeper.clean.control.exceptions.ControllerException;

public class TestContollerException {

	@Test
	public void test_constructor_no_param() throws Exception {
		ControllerException exp = new ControllerException();
		assertNull(exp.getMessage());
		
	}
	
	@Test
	public void test_constructor_message() throws Exception {
		ControllerException exp = new ControllerException("This is a message");
		assertEquals("This is a message", exp.getMessage());
		
	}
}
