package com.llk.notification.service;

import com.llk.notification.exception.EmailException;

public interface EmailService {
	void handleMails(String msg);
	void sendMail(String fromAddress, String toAddress, String subject, String body) throws EmailException;	
}
