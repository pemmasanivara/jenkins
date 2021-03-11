package com.llk.therapist.dao;

import java.util.List;

import com.llk.common.model.Lov;
import com.llk.common.model.ScheduleEvent;
import com.llk.therapist.exception.CalendarException;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapyEvent;

public interface CalendarDAO {
	List<TherapyEvent> getTherapyEvents(RequestParams params) throws CalendarException;

	List<Lov> getValues(String lovType) throws CalendarException;

	List<ScheduleEvent> getScheduledEvents(List<Long> therapistScheduleId,Integer clientId) throws CalendarException;

	boolean calcelSchedule(Therapist params) throws CalendarException;	
	
	List<ScheduleEvent> getCancelEvents(List<Integer> therapistScheduleId) throws CalendarException;
	
	void cancelAllTherapistSchedules(Integer therapistId) throws CalendarException;
	
	void updateSchedule(Integer therapistId,Long scheduleId,String status) throws CalendarException;
	
	void updateSchedules(List<Long> scheduleIds,String status) throws CalendarException;
	
}
