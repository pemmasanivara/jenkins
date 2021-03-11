package com.llk.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.common.model.Lov;
import com.llk.common.model.Party;

@JsonInclude(Include.NON_NULL)
public class Client extends Party {
	private int clientId;
	private String guardianName;
	private String guardianRelation;
	private String interestedClasses;
	private String insuranceDetails;
	private String sendNotification;
	private String address;
	private ClientAvailability schedule;
	private List<Lov> communicationMode;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInsuranceDetails() {
		return insuranceDetails;
	}

	public void setInsuranceDetails(String insuranceDetails) {
		this.insuranceDetails = insuranceDetails;
	}

	public String getSendNotification() {
		return sendNotification;
	}

	public void setSendNotification(String sendNotification) {
		this.sendNotification = sendNotification;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianRelation() {
		return guardianRelation;
	}

	public void setGuardianRelation(String guardianRelation) {
		this.guardianRelation = guardianRelation;
	}

	public String getInterestedClasses() {
		return interestedClasses;
	}

	public void setInterestedClasses(String interestedClasses) {
		this.interestedClasses = interestedClasses;
	}

	public ClientAvailability getSchedule() {
		return schedule;
	}

	public void setSchedule(ClientAvailability schedule) {
		this.schedule = schedule;
	}

	public List<Lov> getCommunicationMode() {
		return communicationMode;
	}

	public void setCommunicationMode(List<Lov> communicationMode) {
		this.communicationMode = communicationMode;
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", guardianName=" + guardianName + ", guardianRelation="
				+ guardianRelation + ", interestedClasses=" + interestedClasses + ", insuranceDetails="
				+ insuranceDetails + ", sendNotification=" + sendNotification + ", address=" + address + ", schedule="
				+ schedule + ", communicationMode=" + communicationMode + ", getPartyId()=" + getPartyId()
				+ ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getEmail()="
				+ getEmail() + ", getPhone()=" + getPhone() + ", getGender()=" + getGender() + ", getAvatarUrl()="
				+ getAvatarUrl() + ", getDob()=" + getDob() + ", getName()=" + getName() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
