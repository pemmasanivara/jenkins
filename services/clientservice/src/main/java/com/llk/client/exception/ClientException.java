package com.llk.client.exception;

public class ClientException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientException() {
		super();
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(String message, Throwable t) {
		super(message, t);
	}

}
