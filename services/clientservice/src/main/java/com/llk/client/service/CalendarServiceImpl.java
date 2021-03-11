package com.llk.client.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llk.client.dao.CalendarDAO;
import com.llk.client.exception.CalendarException;
import com.llk.client.model.ClientEvent;
import com.llk.client.model.RequestParams;
import com.llk.client.model.Schedule;
import com.llk.common.Response;
import com.llk.common.model.Lov;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	CalendarDAO calendarDAO;

	@Autowired
	MessageService messageService;

	@Override
	@Transactional
	public List<Schedule> saveClientSchedule(List<Schedule> schedules) throws CalendarException {
		schedules = calendarDAO.saveClientSchedule(schedules);
		//Update therapist schedule status
		List<Long> therapistScheduleIds=schedules.stream().map(Schedule::getTherapistScheduleId).collect(Collectors.toList());
		calendarDAO.updateTherapistSchedules(therapistScheduleIds,com.llk.common.util.Constants.STATUS_BOOKED);	
		messageService.sendAddClientScheduleMessage(schedules.get(0));
		return schedules;
	}

	@Override
	public List<ClientEvent> getClientEvents(RequestParams params) throws CalendarException {
		return calendarDAO.getClientEvents(params);
	}

	@Override
	public List<Lov> getValues(String lovType) throws CalendarException {
		return calendarDAO.getValues(lovType);
	}

	@Override
	@Transactional
	public Response cancelSchedule(Schedule schedule) throws CalendarException {
		boolean isCancelled=calendarDAO.cancelSchedule(schedule);
		List<Long> therapistScheduleId=Arrays.asList(schedule.getTherapistScheduleId());
		calendarDAO.updateTherapistSchedules(therapistScheduleId,com.llk.common.util.Constants.STATUS_OPEN);
		Response response=null;
		if(isCancelled) {
			response=new Response("Success","Successfully cancelled.");
		}else {
			throw new CalendarException("Unable to cancel event.Please try again.");
		}		
		return response;
	}

}
