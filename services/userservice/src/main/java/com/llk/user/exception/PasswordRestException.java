package com.llk.user.exception;

public class PasswordRestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordRestException() {
		super();
	}

	public PasswordRestException(String message) {
		super(message);
	}

	public PasswordRestException(String message, Throwable t) {
		super(message, t);
	}

}
