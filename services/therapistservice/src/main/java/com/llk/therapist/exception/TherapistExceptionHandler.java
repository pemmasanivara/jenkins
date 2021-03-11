package com.llk.therapist.exception;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.llk.common.Response;
import com.llk.common.exception.CommonExceptionHandler;
import com.llk.common.util.Constants;
import com.llk.therapist.controller.TherapistController;
import com.llk.therapist.util.ConfigUtility;

@ControllerAdvice
public class TherapistExceptionHandler extends CommonExceptionHandler {
	@Autowired
	private ConfigUtility configUtility;

	private static final Logger logger = LoggerFactory.getLogger(TherapistController.class);

	@ExceptionHandler(TherapistException.class)
	public ResponseEntity<Object> handleTherapistException(TherapistException ex) {
		logger.error("Exception-->", ex);
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage(ex.getLocalizedMessage());
		if (configUtility.isDevActiveProfle()) {
			res.setDebugMessage(ExceptionUtils.getStackTrace(ex));
		}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CalendarException.class)
	public ResponseEntity<Object> handleCalendarException(CalendarException ex) {
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage(ex.getLocalizedMessage());
		if (configUtility.isDevActiveProfle()) {
			res.setDebugMessage(ExceptionUtils.getStackTrace(ex));
		}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<Object> handleIOException(IOException ex) {
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage(ex.getLocalizedMessage());
		if (configUtility.isDevActiveProfle()) {
			res.setDebugMessage(ExceptionUtils.getStackTrace(ex));
		}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception ex) {
		Response res = new Response();
		res.setStatus(Constants.ERROR_FAILURE);
		res.setMessage(ex.getLocalizedMessage());
		if (configUtility.isDevActiveProfle()) {
			res.setDebugMessage(ExceptionUtils.getStackTrace(ex));
		}
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
