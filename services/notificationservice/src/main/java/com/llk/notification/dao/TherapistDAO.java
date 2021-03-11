package com.llk.notification.dao;

import java.util.List;


import com.llk.common.model.Message;
import com.llk.notification.model.CalendarEvent;

public interface TherapistDAO {
	List<Message> getAllTherapists(Integer therapistId);
	Message getTherapist(Integer therapistId);
	List<Message> getClients(Integer therapistId,Integer therapyId,Long therapistScheduleId);
	List<CalendarEvent> getTherapistOutLookEvents(Integer clientId,Integer theraistId,Integer therapyId);
}
