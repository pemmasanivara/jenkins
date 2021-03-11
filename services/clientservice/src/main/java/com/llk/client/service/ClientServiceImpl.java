package com.llk.client.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llk.client.dao.CalendarDAO;
import com.llk.client.dao.ClientDAO;
import com.llk.client.exception.CalendarException;
import com.llk.client.exception.ClientException;
import com.llk.client.model.Client;
import com.llk.client.model.Clients;
import com.llk.client.model.CommunicationMode;
import com.llk.client.model.RequestParams;
import com.llk.client.model.TherapyType;
import com.llk.client.util.Constants;
import com.llk.common.Response;
import com.llk.common.model.Address;

@Service
public class ClientServiceImpl implements ClientService {
	private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
	@Autowired
	MessageService messageService;

	@Autowired
	private ClientDAO clientDAO;

	@Autowired
	CalendarDAO calendarDAO;

	@Override
	@Transactional
	public Client createClient(Client client) throws ClientException {
		client = this.createParty(client);
		client = clientDAO.createClient(client);
		client = this.saveAddresses(client);
		client = this.createAvailability(client);
		client = this.createAvailabilityOccurence(client);
		messageService.sendAddClientMessage(client);
		return client;
	}

	@Override
	public Client createParty(Client client) throws ClientException {
		return clientDAO.createParty(client);
	}

	@Override
	public Clients searchClients(RequestParams params) throws ClientException {
		return clientDAO.searchClients(params);
	}

	@Override
	@Transactional
	public Response deleteClient(Integer clientId) throws ClientException, CalendarException {
		boolean isDeleted = clientDAO.deleteClient(clientId);
		boolean cancelled = calendarDAO.cancelAllSchedules(clientId);
		logger.info("cancelled-->"+cancelled);
		if (cancelled) {
			List<Long> therapistScheduleIds = calendarDAO.getAllTherapistScheduleIds(clientId);
			logger.info("therapistScheduleIds-->"+therapistScheduleIds);
			if (therapistScheduleIds != null && therapistScheduleIds.size() > 0) {
				calendarDAO.updateTherapistSchedules(therapistScheduleIds, com.llk.common.util.Constants.STATUS_OPEN);
			}
		}
		Response response = null;
		if (isDeleted) {
			response = new Response(Constants.SUCCESS, Constants.MSG_CLIENT_DELETE_SUCCESS);
		} else {
			response = new Response(Constants.FAILURE, Constants.MSG_CLIENT_DELETE_FAILURE);
		}
		return response;
	}

	@Override
	public Client getClientDetails(Integer clientId) throws ClientException {
		return clientDAO.getClientDetails(clientId);
	}

	@Override
	public Client createAvailability(Client client) throws ClientException {
		return clientDAO.createAvailability(client);
	}

	@Override
	public Client createAvailabilityOccurence(Client client) throws ClientException {
		return clientDAO.createAvailabilityOccurence(client);
	}

	@Override
	public List<Client> getAllClients() throws ClientException {
		return clientDAO.getAllClients();
	}

	@Override
	public Client updateParty(Client client) throws ClientException {
		return clientDAO.updateParty(client);
	}

	@Override
	@Transactional
	public Client updateClient(Client client) throws ClientException {
		client = this.updateParty(client);
		client = clientDAO.updateClient(client);
		client = this.updateAddress(client);
		client = this.createAvailability(client);
		client = this.createAvailabilityOccurence(client);
		return client;
	}

	@Override
	public String unsubscribe(Integer clientId) throws ClientException {
		boolean unsubscribe = clientDAO.unsubscribe(clientId);
		String msg = "";
		if (unsubscribe) {
			msg = "<b>Unsubscribed successfully.</b>";
		} else {
			msg = "<b>Unafble to unsubscribe.Please try later.</b>";
		}
		return msg;
	}

	@Override
	public List<CommunicationMode> getCommunicationModes() throws ClientException {
		return clientDAO.getCommunicationModes();
	}

	@Override
	public List<TherapyType> getTherapyTypes() throws ClientException {
		return clientDAO.getTherapyTypes();
	}

	@Override
	public Client saveAddresses(Client client) throws ClientException {
		return clientDAO.saveAddresses(client);
	}

	@Override
	public List<Address> getAddresses(int clientId) throws ClientException {
		return clientDAO.getAddresses(clientId);
	}

	@Override
	public Client updateAddress(Client client) throws ClientException {
		return clientDAO.updateAddress(client);
	}

}
