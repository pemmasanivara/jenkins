package com.llk.therapist.service;

import java.util.List;

import com.llk.common.Response;
import com.llk.common.model.Lov;
import com.llk.common.model.ScheduleEvent;
import com.llk.therapist.exception.CalendarException;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapyEvent;

public interface CalendarService {
	List<TherapyEvent> getTherapyEvents(RequestParams params) throws CalendarException;

	List<Lov> getValues(String lovType) throws CalendarException;

	List<ScheduleEvent> getScheduledEvents(List<Long> therapistScheduleIds,Integer clientId) throws CalendarException;

	Response cancelSchedule(Therapist params) throws CalendarException;
	

}
