package com.llk.admin.service;

import java.util.List;

import com.llk.admin.exception.UserException;
import com.llk.admin.model.TherapistLevel;

public interface LovService {
	List<TherapistLevel> getTherapistLevels() throws UserException;
	List<TherapistLevel> saveTherapistLevels(List<TherapistLevel> levels) throws UserException;
	void deleteTherapistLevels(List<TherapistLevel> levels) throws UserException;
}
