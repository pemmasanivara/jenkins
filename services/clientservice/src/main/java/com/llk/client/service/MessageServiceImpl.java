package com.llk.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llk.client.model.Client;
import com.llk.client.model.Schedule;
import com.llk.client.util.Util;
import com.llk.common.model.Message;

@Service
public class MessageServiceImpl implements MessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	public void sendAddClientMessage(Client client) {
		Message message = new Message();
		message.setClientId(client.getClientId());
		message.setClientEmail(client.getEmail());
		message.setClientName(client.getFirstName() + " " + client.getLastName());
		message.setClientPhone(client.getPhone());
		message.setType(com.llk.common.util.Constants.ADD_CLIENT_MESSAGE_TYPE);
		message.setCommunicationMode(client.getCommunicationMode());
		message.setTherapyName(client.getSchedule().getTherapyName());
		if (message != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String msg = mapper.writeValueAsString(message);
				jmsMessagingTemplate.convertAndSend(Util.getMqueue(), msg);
			} catch (Exception ex) {
				logger.error("Unable to send message-->"+ex);
			}
		}
	}

	@Override
	public void sendAddClientScheduleMessage(Schedule schedule) {
		Message message = new Message();
		message.setType(com.llk.common.util.Constants.ADD_CLIENT_SCHEDULE);
		message.setClientId(schedule.getClientId());
		message.setTherapistId(schedule.getTherapistId());
		message.setTherapyId(schedule.getTherapyId());
		message.setTherapyName(schedule.getTherapyName());
		message.setClientScheduleId(schedule.getClientScheduleId());
		message.setTherapistAvailabilityId(schedule.getTherapistScheduleId());
		if (message != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				String msg = mapper.writeValueAsString(message);
				logger.info("msg-->"+msg);
				jmsMessagingTemplate.convertAndSend(Util.getMqueue(), msg);
			} catch (Exception ex) {
				logger.error("Unable to send message-->"+ex);
			}
		}
	}
}
