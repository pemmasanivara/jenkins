package com.llk.therapist.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.llk.common.Response;
import com.llk.common.model.Lov;
import com.llk.therapist.exception.CalendarException;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapyEvent;
import com.llk.therapist.service.CalendarService;
import com.llk.therapist.util.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class CalendarController {	
	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
	@Autowired
	private CalendarService calendarService;

	@GetMapping(Constants.URL_THERAPY_AVAILABILITIES)
	public @ResponseBody List<TherapyEvent> getTherapyEvents(RequestParams params) throws CalendarException {
		logger.info("Inside getTherapyEvents()-->");
		return calendarService.getTherapyEvents(params);
	}

	@GetMapping(Constants.URL_LOV)
	public @ResponseBody List<Lov> getLov(@PathVariable("type") String type) throws CalendarException {
		logger.info("Inside getLov()-->");
		return calendarService.getValues(type.toUpperCase());
	}

	@DeleteMapping(Constants.URL_CANCEL_THERAPIST_SCHEDULE)
	public @ResponseBody Response cancelSchedule(@RequestBody Therapist therapist) throws CalendarException {
		logger.info("Inside cancelSchedule()-->");
		return calendarService.cancelSchedule(therapist);
	}	
	
}
