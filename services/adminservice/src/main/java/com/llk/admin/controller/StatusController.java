package com.llk.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.llk.admin.model.Client;
import com.llk.admin.model.Therapist;
import com.llk.admin.service.TherapistClientStatusService;
import com.llk.admin.util.Constants;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class StatusController {

	@Autowired
	TherapistClientStatusService therapistService;
	
	@GetMapping(Constants.URL_THERAPIST)
	public @ResponseBody List<Therapist> listTherapists(){
		return therapistService.listTherapists();
	}
	
	@PutMapping(Constants.URL_UPDATE_THERAPIST)
	public @ResponseBody Therapist updateTherapist(@RequestBody Therapist therapist){
		return therapistService.updateTherapist(therapist);
	}
	
	@GetMapping(Constants.URL_CLIENT)
	public @ResponseBody List<Client> listClients(){
		return therapistService.listclients();
	}
	
	@PutMapping(Constants.URL_UPDATE_CLIENT)
	public @ResponseBody Client updateClient(@RequestBody Client client){
		return therapistService.updateClient(client);
	}
	
}
