package com.llk.schedular;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SmsSchedularSendar {
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	

	@Scheduled(fixedDelay = 180000)
	public void pickSMSNotification() throws JsonProcessingException {
		List<Message> messages = namedParameterJdbcTemplate.query(Constants.SQL_SMS_NOTIFICATIONS, (rs, rowNum) -> {
			Message msg = new Message();
			msg.setClientId(rs.getInt("CLIENT_ID"));
			msg.setTherapistId(rs.getInt("THERAPIST_ID"));
			msg.setClientName(rs.getString("CLIENT_NAME"));
			msg.setTherapistName(rs.getString("THERAPIST_NAME"));
			msg.setClientPhone(rs.getString("CLIENT_PHONE"));
			msg.setTherapistPhone(rs.getString("THERAPIST_PHONE"));
			msg.setTherapyName(rs.getString("THERAPY_NAME"));
			msg.setTherapyId(rs.getInt("THERAPY_ID"));
			return msg;
		});
		ObjectMapper mapper = new ObjectMapper();
		String msg = mapper.writeValueAsString(messages);
		jmsMessagingTemplate.convertAndSend(new ActiveMQQueue("LLK_SCHEDULE_SMS"), msg);
	}
}
