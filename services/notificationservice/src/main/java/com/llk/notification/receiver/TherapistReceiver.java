package com.llk.notification.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.llk.notification.service.TherapistService;
import com.llk.notification.util.Constants;

@Component
public class TherapistReceiver {

	@Autowired
	TherapistService therapistService;

	@JmsListener(destination = Constants.QUEUE_THERAPIST)
	public void receiveQueue(String msg) {		
		therapistService.handleTherapistMessage(msg);
	}

}
