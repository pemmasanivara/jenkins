package com.llk.client.service;

import java.util.List;

import com.llk.client.exception.CalendarException;
import com.llk.client.model.ClientEvent;
import com.llk.client.model.RequestParams;
import com.llk.client.model.Schedule;
import com.llk.common.Response;
import com.llk.common.model.Lov;

public interface CalendarService {
	List<Schedule> saveClientSchedule(List<Schedule> schedule) throws CalendarException;
	List<ClientEvent> getClientEvents(RequestParams params) throws CalendarException;
	List<Lov> getValues(String lovType) throws CalendarException;
	Response cancelSchedule(Schedule schedule) throws CalendarException;
}
