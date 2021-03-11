package com.llk.admin.service;

import java.util.List;

import com.llk.admin.model.Responsability;

public interface ResponsabilityService {
	List<Responsability> saveResponsabilities(List<Responsability> responsabilities);

	List<Responsability> getResponsabilities();

	void delteResponsabilities(List<Responsability> responsabilities);
}
