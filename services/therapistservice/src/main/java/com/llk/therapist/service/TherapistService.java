package com.llk.therapist.service;

import java.util.List;

import com.llk.common.Response;
import com.llk.therapist.exception.CalendarException;
import com.llk.therapist.exception.TherapistException;
import com.llk.therapist.model.CommunicationMode;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapistLevel;
import com.llk.therapist.model.Therapists;
import com.llk.therapist.model.TherapyType;

public interface TherapistService {
	/**
	 * 
	 * @param therapist
	 * @return
	 * @throws TherapistException
	 */
	Therapist saveTherapist(Therapist therapist) throws TherapistException;

	/**
	 * 
	 * @param params
	 * @return
	 * @throws TherapistException
	 */
	Therapists searchTherapists(RequestParams params) throws TherapistException;

	/**
	 * 
	 * @param bcbaId
	 * @param therapistId
	 * @return
	 * @throws TherapistException
	 */
	Response deleteTherapist(Integer bcbaId, Integer therapistId) throws TherapistException,CalendarException;

	/**
	 * 
	 * @param therapist
	 * @return
	 * @throws TherapistException
	 */
	Therapist scheduleAvailability(Therapist therapist) throws TherapistException;

	/**
	 * 
	 * @param therapist
	 * @return
	 * @throws TherapistException
	 */
	Therapist scheduleOccurence(Therapist therapist) throws TherapistException;

	/**
	 * 
	 * @param therapistId
	 * @return
	 * @throws TherapistException
	 */
	Therapist getTherapistDetails(Integer therapistId) throws TherapistException;

	/**
	 * 
	 * @return
	 * @throws TherapistException
	 */
	List<Therapist> listTherapists() throws TherapistException;

	/**
	 * 
	 * @param therapist
	 * @return
	 * @throws TherapistException
	 */
	Therapist updateTherapist(Therapist therapist) throws TherapistException;

	boolean isValidTherapistAvailability(Therapist therapist) throws TherapistException;

	List<TherapyType> getTherapyTypes() throws TherapistException;

	List<TherapistLevel> getTherapistLevels() throws TherapistException;
	
	List<CommunicationMode> getCommunicationModes() throws TherapistException;
	
	public void sendAddTherapistMessage(Therapist therapist) throws TherapistException;
	
	Therapist updateTherapistSchedule(Therapist therapist) throws TherapistException;
}
