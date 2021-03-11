package com.llk.notification.service;

import java.util.List;

import com.llk.common.model.Message;
import com.llk.notification.exception.EmailException;



public interface TherapistService {
	void handleTherapistMessage(String message);
	List<Message> getAllTherapists(Integer therapistId);
	void sendEmailOnAddClientSchedule(Message message) throws EmailException;	
}
