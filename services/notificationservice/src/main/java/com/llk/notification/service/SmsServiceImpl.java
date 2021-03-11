package com.llk.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.llk.notification.util.Constants;
import com.twilio.Twilio;

@Service
public class SmsServiceImpl implements SmsService {
	private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	@Override
	public String sendSMS(String toPhoneNum, String mesg) {
		logger.info("Inside sendSMS()");
		logger.info("toPhoneNum-->"+toPhoneNum);
		logger.info("mesg-->"+mesg);
		Twilio.init(Constants.ACCOUNT_SID, Constants.AUTH_TOKEN);
		com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
				.creator(new com.twilio.type.PhoneNumber(toPhoneNum),
						new com.twilio.type.PhoneNumber(Constants.FROM_PHONE_NUM), mesg)
				.create();
		logger.info("message from twilo-->"+message.toString());
		return message.getSid();
	}

}
