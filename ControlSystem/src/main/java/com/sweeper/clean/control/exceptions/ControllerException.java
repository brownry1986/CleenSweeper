package com.sweeper.clean.control.exceptions;

/**
 * General exception in the robots controller
 * @author Dave
 *
 */
public class ControllerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ControllerException(){
		super();
	}
	
	public ControllerException(String message){
		super(message);
	}
	
}
