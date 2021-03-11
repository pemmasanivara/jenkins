package com.llk.notification.exception;

public class EmailException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailException() {
		super();
	}

	public EmailException(String message) {
		super(message);
	}

	public EmailException(String message, Throwable t) {
		super(message, t);
	}
}
