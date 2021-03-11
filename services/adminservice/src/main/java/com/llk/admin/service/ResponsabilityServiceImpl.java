package com.llk.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llk.admin.dao.ResponsabilityRepository;
import com.llk.admin.model.Responsability;

@Service
public class ResponsabilityServiceImpl implements ResponsabilityService {

	@Autowired
	private ResponsabilityRepository responsabilityRepository;

	@Override
	public List<Responsability> saveResponsabilities(List<Responsability> responsabilities) {
		return responsabilityRepository.saveAll(responsabilities);
	}

	@Override
	public List<Responsability> getResponsabilities() {		
		return responsabilityRepository.findAll();
	}

	@Override
	public void delteResponsabilities(List<Responsability> responsabilities) {
		responsabilityRepository.deleteAll(responsabilities);
	}

}
