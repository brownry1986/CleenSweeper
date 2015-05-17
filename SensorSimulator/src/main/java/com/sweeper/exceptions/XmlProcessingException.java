package com.sweeper.exceptions;

public class XmlProcessingException extends Exception {

		private static final long serialVersionUID = 1L;
		
		private String message;
		
		public XmlProcessingException(String message){
			super(message);
			this.message = message;
		}
		
		public XmlProcessingException(String message, Throwable cause){
			super(message, cause);
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
