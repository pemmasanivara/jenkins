package com.llk.therapist.exception;

public class CalendarException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalendarException() {
		super();
	}

	public CalendarException(String message) {
		super(message);
	}

	public CalendarException(String message, Throwable t) {
		super(message, t);
	}
}
