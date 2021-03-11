package com.llk.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.llk.client.exception.CalendarException;
import com.llk.client.model.ClientEvent;
import com.llk.client.model.RequestParams;
import com.llk.client.model.Schedule;
import com.llk.client.service.CalendarService;                 
import com.llk.client.util.Constants;
import com.llk.common.Response;
import com.llk.common.model.Lov;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class CalendarController {

	@Autowired
	CalendarService calendarService;

	@PostMapping(Constants.URL_SAVE_CLIENT_SCHEDULE)
	public @ResponseBody List<Schedule> saveClientSchedule(@RequestBody List<Schedule> schedule) throws CalendarException {
		return calendarService.saveClientSchedule(schedule);
	}

	@GetMapping(Constants.URL_CLIENT_SCHEDULE_EVENTS)
	public @ResponseBody List<ClientEvent> getClientEvents(RequestParams params) throws CalendarException {
		return calendarService.getClientEvents(params);
	}
	
	@DeleteMapping(Constants.URL_CANCEL_CLIENT_SCHEDULE)
	public @ResponseBody Response cancelScheduleEvent(@RequestBody Schedule schedule) throws CalendarException {
		return calendarService.cancelSchedule(schedule);
	}
	
	@GetMapping(Constants.URL_LOV)
	public @ResponseBody List<Lov> getLov(@PathVariable("type") String type) throws CalendarException {
		return calendarService.getValues(type.toUpperCase());
	}

}
