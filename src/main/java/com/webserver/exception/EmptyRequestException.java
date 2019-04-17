package com.webserver.exception;

public class IllegalRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalRequestException() {
		super();
		
	}
	
	public IllegalRequestException(String message) {
		super(message);
		
	}
	
	

}
