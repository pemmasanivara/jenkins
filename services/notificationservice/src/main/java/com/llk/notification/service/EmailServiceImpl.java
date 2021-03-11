package com.llk.notification.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.llk.common.model.Message;
import com.llk.notification.exception.EmailException;
import com.llk.notification.util.ClientEmailHelper;
import com.llk.notification.util.Constants;
import com.llk.notification.util.TherapistEmailHelper;
import com.llk.notification.util.Util;

@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);	
	@Autowired
	private JavaMailSender javaMailSender;

	public void handleMails(String msg) {
		try {
			List<Message> messages = Util.getMessages(msg);
			if (messages != null) {
				messages.stream().forEach(message -> {
					try {
						sendClientEmail(message);
						sendTherapistEmail(message);
					} catch (EmailException e) {
						e.printStackTrace();
					}

				});
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void sendClientEmail(Message message) throws EmailException {
		this.sendMail(Constants.FROM_EMAIL, message.getClientEmail(),
				ClientEmailHelper.getClientSubject(message.getTherapistName()),
				ClientEmailHelper.getClientBody(message));
	}

	private void sendTherapistEmail(Message message) throws EmailException {
		this.sendMail(Constants.FROM_EMAIL, message.getTherapistEmail(),
				TherapistEmailHelper.getTherapistSubject(message.getClientName()),
				TherapistEmailHelper.getTherapistBody(message));
	}

	public void sendMail(String fromAddress, String toAddress, String subject, String body) throws EmailException {
		logger.info("Inside sendMail()");
		logger.info("fromAddress-->"+fromAddress);
		logger.info("toAddress-->"+toAddress);
		logger.info("subject-->"+subject);
		logger.info("body-->"+body);
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		try {
			helper.setFrom(fromAddress);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			helper.setText(body, true);
			javaMailSender.send(mimeMessage);
			logger.info("Mail sent successfully..");
		} catch (MessagingException e) {
			throw new EmailException(Constants.EXCEPTION_EMAIL_MSG + ":" + toAddress);
		}
	}

	
}
