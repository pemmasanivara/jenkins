package com.llk.client.service;

import java.util.List;

import com.llk.client.exception.CalendarException;
import com.llk.client.exception.ClientException;
import com.llk.client.model.Client;
import com.llk.client.model.Clients;
import com.llk.client.model.CommunicationMode;
import com.llk.client.model.RequestParams;
import com.llk.client.model.TherapyType;
import com.llk.common.Response;
import com.llk.common.model.Address;

public interface ClientService {
	Client createParty(Client client) throws ClientException;

	Client createClient(Client client) throws ClientException;

	Client saveAddresses(Client client) throws ClientException;

	Client createAvailability(Client client) throws ClientException;

	Client createAvailabilityOccurence(Client client) throws ClientException;

	Clients searchClients(RequestParams params) throws ClientException;

	Response deleteClient(Integer clientId) throws ClientException, CalendarException;

	Client getClientDetails(Integer clientId) throws ClientException;

	List<Client> getAllClients() throws ClientException;

	Client updateParty(Client client) throws ClientException;

	Client updateClient(Client client) throws ClientException;

	Client updateAddress(Client client) throws ClientException;

	String unsubscribe(Integer clientId) throws ClientException;

	List<CommunicationMode> getCommunicationModes() throws ClientException;

	List<TherapyType> getTherapyTypes() throws ClientException;

	List<Address> getAddresses(int clientId) throws ClientException;
}
