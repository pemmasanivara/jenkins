package com.llk.notification.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotificationExceptionHandler {
	@ExceptionHandler(EmailException.class)
	public void handleEmailException(EmailException ex) {
		ex.printStackTrace();
	}

	@ExceptionHandler(RuntimeException.class)
	public void handleRuntimeException(RuntimeException ex) {
		ex.printStackTrace();
	}
}
