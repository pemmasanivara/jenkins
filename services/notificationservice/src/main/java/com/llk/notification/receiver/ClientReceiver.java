package com.llk.notification.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.llk.notification.service.ClientService;
import com.llk.notification.util.Constants;

@Component
public class ClientReceiver {

	@Autowired
	ClientService clientService;

	@JmsListener(destination = Constants.QUEUE_CLIENT)
	public void receiveQueue(String msg) {		
		clientService.handleClientMessage(msg);
	}
}
