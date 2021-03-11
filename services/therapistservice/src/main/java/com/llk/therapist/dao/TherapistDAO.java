package com.llk.therapist.dao;

import java.util.List;

import com.llk.therapist.exception.TherapistException;
import com.llk.therapist.model.CommunicationMode;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapistLevel;
import com.llk.therapist.model.Therapists;
import com.llk.therapist.model.TherapyType;

public interface TherapistDAO {
	Therapist createParty(Therapist therapist) throws TherapistException;

	Therapist createTherapist(Therapist therapist) throws TherapistException;

	Therapist saveTherapistSession(Therapist therapist) throws TherapistException;

	Therapists searchTherapists(RequestParams params) throws TherapistException;

	boolean deleteTherapist(Integer bcbaId, Integer therapistId) throws TherapistException;

	Therapist scheduleAvailability(Therapist therapist) throws TherapistException;

	Therapist scheduleOccurence(Therapist therapist) throws TherapistException;

	Therapist getTherapistDetails(Integer therapistId) throws TherapistException;

	List<Therapist> listTherapists() throws TherapistException;

	Therapist updateParty(Therapist therapist) throws TherapistException;

	Therapist updateTherapist(Therapist therapist) throws TherapistException;

	boolean isValidTherapistAvailability(Therapist therapist) throws TherapistException;

	List<TherapyType> getTherapyTypes() throws TherapistException;

	List<TherapistLevel> getTherapistLevels() throws TherapistException;
	
	List<CommunicationMode> getCommunicationModes() throws TherapistException;
	
	Therapist updateTherapistSchedule(Therapist therapist) throws TherapistException;
	
}
