package com.llk.client.service;

import com.llk.client.exception.CalendarException;
import com.llk.client.exception.ClientException;
import com.llk.client.model.Client;
import com.llk.client.model.Schedule;

interface MessageService {
	void sendAddClientMessage(Client client) throws ClientException;

	void sendAddClientScheduleMessage(Schedule schedule) throws CalendarException;
}
