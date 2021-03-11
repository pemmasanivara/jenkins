package com.llk.client.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.llk.common.Response;
import com.llk.common.exception.ApiError;
import com.llk.common.exception.CommonExceptionHandler;
import com.llk.common.util.Constants;

@ControllerAdvice
public class ClientExceptionHandler extends CommonExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);
	@ExceptionHandler(ClientException.class)
	public ResponseEntity<Object> handleUserException(ClientException ex) {
		logger.error("handleUserException-->", ex);
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	@ExceptionHandler(CalendarException.class)
	public ResponseEntity<Object> handleCalendarException(CalendarException ex) {
		logger.error("handleCalendarException-->", ex);
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception ex) {		
		logger.error("Exception-->", ex);
		Response res = new Response(Constants.FAILURE_STATUS,Constants.FAILURE_MESSAGE);		
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
