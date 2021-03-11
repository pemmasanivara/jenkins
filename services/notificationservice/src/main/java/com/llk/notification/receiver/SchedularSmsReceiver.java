package com.llk.notification.receiver;

import java.util.List;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llk.common.model.Message;
import com.llk.notification.util.ClientSmsHelper;
import com.llk.notification.util.Constants;
import com.llk.notification.util.TherapistSmsHelper;
import com.twilio.Twilio;

@Component
public class SchedularSmsReceiver {
	@JmsListener(destination = "LLK_SCHEDULE_SMS")
	public void receiveQueue(String msg) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<Message> messages = mapper.readValue(msg, new TypeReference<List<Message>>() {
		});
		if (messages != null && messages.size() > 0) {
			messages.stream().forEach(message -> {
				sendClientSMS(message.getClientPhone(), ClientSmsHelper.getClientMessage(message.getClientName(),
						message.getTherapistName(), message.getTherapyName()));
				sendTherapistSMS(message.getTherapistPhone(), TherapistSmsHelper.getTherapistMessage(
						message.getClientName(), message.getTherapistName(), message.getTherapyName()));
			});
		}
	}

	private void sendClientSMS(String clientPhone, String message) {
		sendSMS(clientPhone, message);
	}

	private void sendTherapistSMS(String therapistPhone, String message) {
		sendSMS(therapistPhone, message);
	}

	private static String sendSMS(String toPhoneNum, String mesg) {
		Twilio.init(Constants.ACCOUNT_SID, Constants.AUTH_TOKEN);
		com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
				.creator(new com.twilio.type.PhoneNumber(toPhoneNum),
						new com.twilio.type.PhoneNumber(Constants.FROM_PHONE_NUM), mesg)
				.create();
		return message.getSid();

	}

}
