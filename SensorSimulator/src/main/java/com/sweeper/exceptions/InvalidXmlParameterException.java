package com.sweeper.exceptions;

public class InvalidXmlParameterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public InvalidXmlParameterException(String message){
		super(message);
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
