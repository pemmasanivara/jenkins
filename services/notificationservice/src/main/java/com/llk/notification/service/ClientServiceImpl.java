package com.llk.notification.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llk.common.model.Lov;
import com.llk.common.model.Message;
import com.llk.notification.dao.ClientDAO;
import com.llk.notification.exception.EmailException;
import com.llk.notification.util.ClientEmailHelper;
import com.llk.notification.util.ClientSmsHelper;
import com.llk.notification.util.Constants;
import com.llk.notification.util.Util;

@Service
public class ClientServiceImpl implements ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	SmsService smsService;

	@Autowired
	EmailService emailService;

	@Autowired
	ClientDAO clientDAO;

	@Autowired
	TherapistService therapistService;

	@Override
	public void handleClientMessage(String msg) {
		logger.info("handleClientMessage-->" + msg.toString());
		Message message = Util.getMessage(msg);
		if (message != null) {
			if (message.getType().equalsIgnoreCase(com.llk.common.util.Constants.ADD_CLIENT_MESSAGE_TYPE)) {
				handleAddClient(message);
			} else if (message.getType().equalsIgnoreCase(com.llk.common.util.Constants.ADD_CLIENT_SCHEDULE)) {
				handleAddClientSchedule(message);
			}

		}

	}

	private void handleAddClient(Message message) {
		logger.info("handleAddClient-->" + message.toString());
		List<Lov> cModes = message.getCommunicationMode();
		if (cModes != null) {
			cModes.forEach(lov -> {
				String mode = lov.getDisplay();
				if (mode != null && mode.equalsIgnoreCase(Constants.COMM_MODE_EMAIL)) {
					sendClientAddEmail(message);
				}
				if (mode != null && mode.equalsIgnoreCase(Constants.COMM_MODE_SMS)) {
					sendClientAddSms(message);
				}
			});
		}
	}

	private void handleAddClientSchedule(Message message) {
		logger.info("handleAddClientSchedule-->" + message.toString());
		Integer clientId = message.getClientId();
		logger.info("clientId-->" + clientId);
		Message client = clientDAO.getClientDetails(clientId);
		logger.info("client-->" + client.toString());
		List<Lov> cModes = client.getCommunicationMode();
		message.setClientName(client.getClientName());
		logger.info("cModes-->" + cModes);
		if (cModes != null) {
			cModes.forEach(lov -> {
				String mode = lov.getDisplay();
				if (mode != null && mode.equalsIgnoreCase(Constants.COMM_MODE_EMAIL)) {
					sendClientScheduleAddEmail(client);

				}
				if (mode != null && mode.equalsIgnoreCase(Constants.COMM_MODE_SMS)) {
					sendClientScheduleAddSms(client);
				}
				try {
					therapistService.sendEmailOnAddClientSchedule(message);
				} catch (Exception ex) {
					logger.error("Error while sending mail-->", ex);
				}
			});
		}
	}

	private void sendClientAddEmail(Message message) {
		try {
			emailService.sendMail(Constants.FROM_EMAIL, message.getClientEmail(),
					ClientEmailHelper.getClientAddEmailSubject(), ClientEmailHelper.getClientAddEmailBody(message));
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	private void sendClientAddSms(Message message) {
		smsService.sendSMS(message.getClientPhone(),
				ClientSmsHelper.getClientAddSmsMessage(message.getClientName(), message.getTherapyName()));
	}

	private void sendClientScheduleAddSms(Message client) {
		smsService.sendSMS(client.getClientPhone(),
				ClientSmsHelper.getClientScheduleAddMessage(client.getClientName()));
	}

	private void sendClientScheduleAddEmail(Message client) {
		try {
			emailService.sendMail(Constants.FROM_EMAIL, client.getClientEmail(), "Hello New scheduled is booked",
					ClientEmailHelper.getClientScheduleAddSubject(client));
		} catch (Exception ex) {
			logger.error("Error while sending mail-->", ex);
		}
	}

}
