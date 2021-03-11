package com.llk.therapist.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llk.common.Response;
import com.llk.common.model.Lov;
import com.llk.common.model.Message;
import com.llk.common.model.ScheduleEvent;
import com.llk.therapist.dao.CalendarDAO;
import com.llk.therapist.exception.CalendarException;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapistScheduleOccurence;
import com.llk.therapist.model.TherapyEvent;
import com.llk.therapist.util.Constants;
import com.llk.therapist.util.Util;

@Service
public class CalendarServiceImpl implements CalendarService {
	
	private static final Logger logger = LoggerFactory.getLogger(CalendarServiceImpl.class);

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	CalendarDAO calendarDAO;
	
	@Autowired
	TherapistService therapistService;

	@Override
	public List<TherapyEvent> getTherapyEvents(RequestParams params) throws CalendarException {
		return calendarDAO.getTherapyEvents(params);
	}

	@Override
	public List<Lov> getValues(String lovType) throws CalendarException {
		return calendarDAO.getValues(lovType);
	}

	@Override
	public List<ScheduleEvent> getScheduledEvents(List<Long> therapistScheduleIds,Integer clientId) throws CalendarException {
		return calendarDAO.getScheduledEvents(therapistScheduleIds,clientId);
	}

	@Override
	@Transactional
	public Response cancelSchedule(Therapist therapist) throws CalendarException {
		Response res = null;
		Long oldScheduleId=therapist.getSchedule().getScheduleId();
		logger.info("oldScheduleId-->"+oldScheduleId);
		logger.info("therapist-->"+therapist);
		//Update schedule status
		calendarDAO.updateSchedule(therapist.getTherapistId(), oldScheduleId, Constants.STATUS_CANCELLED);
		if(therapist.getSchedule()!=null && therapist.getSchedule().getStartDate()!=null) {
			try {
				TherapistScheduleOccurence occurence=new TherapistScheduleOccurence();	
			    therapist.getSchedule().setScheduleOccurence(occurence);
				therapist=therapistService.scheduleAvailability(therapist);	
			    logger.info("therapist-->"+therapist);
			}catch(Exception ex) {
				ex.printStackTrace();
				throw new CalendarException("Unable to create new schedule.");
			}
		}
		therapist.getSchedule().setScheduleId(oldScheduleId);
		boolean deleted = calendarDAO.calcelSchedule(therapist);
		if (deleted) {
			res = new Response(Constants.SUCCESS_STATUS, "Cancelled successfully.");
			sendCancelScheduleMessage(therapist);
		}
		return res;
	}

	private void sendCancelScheduleMessage(Therapist therapist) {
		Message message = new Message();
		message.setTherapistId(Integer.valueOf(therapist.getTherapistId()));
		message.setTherapyId(Integer.valueOf(therapist.getTherapyId()));
		message.setTherapistAvailabilityId(therapist.getSchedule().getScheduleId());
		message.setType(com.llk.common.util.Constants.CANCEL_THERAPIST_SCHEDULE);
		if (message != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String msg = mapper.writeValueAsString(message);
				jmsMessagingTemplate.convertAndSend(Util.getMqueue(), msg);
			} catch (Exception ex) {
				logger.error("Error-->",ex);
			}
		}
	}

}
