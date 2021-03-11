package com.llk.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.llk.admin.dao.TherapistClientDAO;
import com.llk.admin.model.Client;
import com.llk.admin.model.Therapist;

@Service
public class TherapistClientStatusServiceImpl implements TherapistClientStatusService{
	
	@Autowired
	TherapistClientDAO therapistDAO;

	@Override
	public List<Therapist> listTherapists() {
		return therapistDAO.listTherapists();
	}

	@Override
	@Transactional
	public Therapist updateTherapist(Therapist therapist) {
		therapist = therapistDAO.updateTherapist(therapist);
		return therapist;
	}

	@Override
	public List<Client> listclients() {
		return therapistDAO.listClients();
	}

	@Override
	public Client updateClient(Client client) {
		client=therapistDAO.updateClient(client);
		return client;
	}

}
