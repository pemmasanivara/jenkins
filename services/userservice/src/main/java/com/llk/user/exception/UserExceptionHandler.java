package com.llk.user.exception;

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
public class UserExceptionHandler extends CommonExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Object> handleUserException(UserException ex) {
		logger.error("error-->",ex);
		Response res=new Response(Constants.FAILURE_STATUS,Constants.FAILURE_MESSAGE);
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);		
	}

	@ExceptionHandler(PasswordRestException.class)
	public ResponseEntity<Object> handlePasswordResetException(PasswordRestException ex) {
		logger.error("error-->",ex);
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception ex) {
		logger.error("error-->",ex);
		Response res=new Response(Constants.FAILURE_STATUS,Constants.FAILURE_MESSAGE);
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);		
	}
}
