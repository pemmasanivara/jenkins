package com.llk.notification.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.llk.notification.service.EmailService;
import com.llk.notification.util.Constants;

@Component
public class SchedularEmailReceiver {

	@Autowired
	EmailService emailService;

	@JmsListener(destination = Constants.QUEUE_SCHEDULE_EMAIL)
	public void receiveQueue(String msg) {
		emailService.handleMails(msg);
	}
	
	

}
