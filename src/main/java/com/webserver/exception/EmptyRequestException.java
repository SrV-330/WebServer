package com.webserver.exception;

public class EmptyRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyRequestException() {
		super();
		
	}
	
	public EmptyRequestException(String message) {
		super(message);
		
	}
	
	

}
