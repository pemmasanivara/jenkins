package com.llk.therapist.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llk.common.Response;
import com.llk.common.model.Message;
import com.llk.therapist.dao.CalendarDAO;
import com.llk.therapist.dao.TherapistDAO;
import com.llk.therapist.exception.CalendarException;
import com.llk.therapist.exception.TherapistException;
import com.llk.therapist.model.CommunicationMode;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapistLevel;
import com.llk.therapist.model.Therapists;
import com.llk.therapist.model.TherapyType;
import com.llk.therapist.util.Constants;
import com.llk.therapist.util.Util;

@Service
public class TherapistServiceImpl implements TherapistService {
	
	private static final Logger logger = LoggerFactory.getLogger(TherapistServiceImpl.class);

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	TherapistDAO therapistDAO;

	@Autowired
	CalendarDAO calendarDAO;

	@Transactional
	public Therapist saveTherapist(Therapist therapist) throws TherapistException {
		therapist = therapistDAO.createParty(therapist);
		therapist.setStatus(Constants.STATUS_ACTIVE);
		therapist = therapistDAO.createTherapist(therapist);
		therapist = therapistDAO.saveTherapistSession(therapist);
		therapist = this.scheduleAvailability(therapist);
		//therapist = this.scheduleOccurence(therapist);
		this.sendAddTherapistMessage(therapist);
		return therapist;
	};

	@Override
	public Therapists searchTherapists(RequestParams params) throws TherapistException {
		return therapistDAO.searchTherapists(params);
	}

	@Override
	@Transactional
	public Response deleteTherapist(Integer bcbaId, Integer therapistId) throws TherapistException, CalendarException {
		calendarDAO.cancelAllTherapistSchedules(therapistId);
		boolean isDeleted = therapistDAO.deleteTherapist(bcbaId, therapistId);
		Response response = null;
		if (isDeleted) {
			response = new Response(Constants.MSG_SUCCESS, Constants.THERAPIST_DEL_SUCCESS_MSG);
		} else {
			throw new TherapistException(Constants.THERAPIST_DEL_FAILURE_MSG);
		}
		return response;
	}

	@Override
	public Therapist scheduleAvailability(Therapist therapist) throws TherapistException {
		return therapistDAO.scheduleAvailability(therapist);
	}

	@Override
	public Therapist scheduleOccurence(Therapist therapist) throws TherapistException {
		return therapistDAO.scheduleOccurence(therapist);
	}

	@Override
	public Therapist getTherapistDetails(Integer therapistId) throws TherapistException {
		return therapistDAO.getTherapistDetails(therapistId);
	}

	@Override
	public List<Therapist> listTherapists() throws TherapistException {
		return therapistDAO.listTherapists();
	}

	@Override
	@Transactional
	public Therapist updateTherapist(Therapist therapist) throws TherapistException {
		if (this.isValidTherapistAvailability(therapist)) {
			therapist = therapistDAO.updateParty(therapist);
			therapist = therapistDAO.updateTherapist(therapist);
			therapist = therapistDAO.saveTherapistSession(therapist);
			therapist = therapistDAO.scheduleAvailability(therapist);
			//therapist = therapistDAO.scheduleOccurence(therapist);
			return therapist;
		} else {
			throw new TherapistException("This availability is already published.Please try others.");
		}
	}

	@Override
	public boolean isValidTherapistAvailability(Therapist therapist) throws TherapistException {
		return therapistDAO.isValidTherapistAvailability(therapist);
	}

	@Override
	public List<TherapyType> getTherapyTypes() throws TherapistException {
		return therapistDAO.getTherapyTypes();
	}

	@Override
	public List<TherapistLevel> getTherapistLevels() throws TherapistException {
		return therapistDAO.getTherapistLevels();
	}

	@Override
	public List<CommunicationMode> getCommunicationModes() throws TherapistException {
		return therapistDAO.getCommunicationModes();
	};

	public void sendAddTherapistMessage(Therapist therapist) {
		try {
			Message message = new Message();
			message.setTherapistId(therapist.getTherapistId());
			message.setTherapistName(therapist.getFirstName() + " " + therapist.getLastName());
			message.setTherapistPhone(therapist.getPhone());
			message.setTherapistEmail(therapist.getEmail());
			message.setType(com.llk.common.util.Constants.ADD_MESSAGE_TYPE);
			message.setCommunicationMode(therapist.getCommunicationMode());
			if (message != null) {
				ObjectMapper mapper = new ObjectMapper();
				String msg = mapper.writeValueAsString(message);
				jmsMessagingTemplate.convertAndSend(Util.getMqueue(), msg);

			}
		} catch (Exception ex) {
           logger.error("Unable to notify-->",ex);
		}
	}

	@Override
	public Therapist updateTherapistSchedule(Therapist therapist) throws TherapistException {
		if (this.isValidTherapistAvailability(therapist)) {
			return therapistDAO.updateTherapistSchedule(therapist);
		} else {
			throw new TherapistException("This availability is already published.Please try others.");
		}

	}

}
