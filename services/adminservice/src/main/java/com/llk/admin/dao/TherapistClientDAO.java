package com.llk.admin.dao;

import java.util.List;

import com.llk.admin.model.Client;
import com.llk.admin.model.Therapist;


public interface TherapistClientDAO {
	
	List<Therapist> listTherapists();
	
	Therapist updateTherapist(Therapist therapist);

	List<Client> listClients();

	Client updateClient(Client client);

}
