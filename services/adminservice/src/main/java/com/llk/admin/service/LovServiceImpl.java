package com.llk.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llk.admin.dao.TherapistLevelRepository;
import com.llk.admin.exception.UserException;
import com.llk.admin.model.TherapistLevel;

@Service
public class LovServiceImpl implements LovService {
	@Autowired
	private TherapistLevelRepository therapistLevelRepository;

	@Override
	public List<TherapistLevel> getTherapistLevels() throws UserException {

		return therapistLevelRepository.findAll();
	}

	@Override
	public List<TherapistLevel> saveTherapistLevels(List<TherapistLevel> levels) {
		return therapistLevelRepository.saveAll(levels);
	}

	@Override
	public void deleteTherapistLevels(List<TherapistLevel> levels) {
		therapistLevelRepository.deleteAll(levels);

	}

}
