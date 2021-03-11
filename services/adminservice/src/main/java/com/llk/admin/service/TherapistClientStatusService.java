package com.llk.admin.service;

import java.util.List;

import com.llk.admin.model.Client;
import com.llk.admin.model.Therapist;

public interface TherapistClientStatusService {

	List<Therapist> listTherapists();

	Therapist updateTherapist(Therapist therapist);
	
	List<Client> listclients();
	
	Client updateClient(Client client);
}
