package com.llk.client.dao;

import java.util.List;

import com.llk.client.exception.CalendarException;
import com.llk.client.model.ClientEvent;
import com.llk.client.model.RequestParams;
import com.llk.client.model.Schedule;
import com.llk.common.model.Lov;

public interface CalendarDAO {
	List<Schedule> saveClientSchedule(List<Schedule> schedule) throws CalendarException;
	List<ClientEvent> getClientEvents(RequestParams params) throws CalendarException;
	List<Lov> getValues(String lovType) throws CalendarException;	
	boolean cancelSchedule(Schedule schedule) throws CalendarException;
	boolean cancelAllSchedules(Integer clientId) throws CalendarException;
	List<Long> getAllTherapistScheduleIds(Integer clientId) throws CalendarException;
	void updateTherapistSchedules(List<Long> scheduleIds,String status) throws CalendarException; //TODO move this methods to therapist service
}
