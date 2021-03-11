package com.llk.admin.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.llk.admin.model.Response;
import com.llk.admin.util.Constants;

@ControllerAdvice
public class AdminExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private org.springframework.core.env.Environment environment;
	 
	private static final Logger logger = LoggerFactory.getLogger(AdminExceptionHandler.class);
	
	private boolean isDevActiveProfle() {
		String[] profiles=environment.getActiveProfiles();
		for(String profile:profiles) {
			if(profile!=null && profile.equalsIgnoreCase(Constants.PROFILE_DEV)) {
				return true;
			}
		}
		return false;
	}

	@ExceptionHandler(RoleException.class)
	public ResponseEntity<Response> handleRoleException(RoleException ex) {
		logger.error("RoleException-->", ex);
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage(Constants.ERROR_MESSAGE);
		if(isDevActiveProfle()) {res.setDebugMessage(ex.getLocalizedMessage());}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResponsabilityException.class)
	public ResponseEntity<Response> handleResponsabilityException(ResponsabilityException ex) {
		logger.error("ResponsabilityException-->", ex);
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage(Constants.ERROR_MESSAGE);
		if(isDevActiveProfle()) {res.setDebugMessage(ex.getLocalizedMessage());}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RoleResponsabilityMappingException.class)
	public ResponseEntity<Response> handleRoleResponsabilityMappingException(RoleResponsabilityMappingException ex) {
		logger.error("RoleResponsabilityMappingException-->", ex);
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage(Constants.ERROR_MESSAGE);
		if(isDevActiveProfle()) {res.setDebugMessage(ex.getLocalizedMessage());}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> handleUserException(UserException ex) {		
		logger.error("UserException-->", ex);
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage(Constants.ERROR_MESSAGE);
		if(isDevActiveProfle()) {res.setDebugMessage(ex.getLocalizedMessage());}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException ex) {		
		logger.error("ConstraintViolationException-->", ex);
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage("Duplicate entry.");
		if(isDevActiveProfle()) {res.setDebugMessage(ex.getLocalizedMessage());}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
