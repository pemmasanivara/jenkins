package com.llk.therapist.exception;

public class TherapistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TherapistException() {
		super();
	}

	public TherapistException(String message) {
		super(message);
	}

	public TherapistException(String message, Throwable t) {
		super(message, t);
	}
}
