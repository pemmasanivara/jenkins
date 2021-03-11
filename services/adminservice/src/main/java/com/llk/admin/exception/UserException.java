package com.llk.admin.exception;

public class UserException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7735250573863158215L;
	
	public UserException() {
		super();
	}

	public UserException(String message) {
		super(message);
	}
	
	public UserException(Throwable t) {
		super(t);
	}

	public UserException(String message, Throwable t) {
		super(message, t);
	}

}
