package com.llk.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.common.model.Event;

@JsonInclude(Include.NON_NULL)
public class ClientEvent extends Event {
	private Integer clientId;
	private Integer clientScheduleId;
	private String clientName;
	private String clientEmail;
	private Integer therapistId;
	private Integer therapyId;
	private Long therapistScheduleId;
	private String therapistName;
	private String therapyName;	

	public String getTherapyName() {
		return therapyName;
	}

	public void setTherapyName(String therapyName) {
		this.therapyName = therapyName;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getClientScheduleId() {
		return clientScheduleId;
	}

	public void setClientScheduleId(Integer clientScheduleId) {
		this.clientScheduleId = clientScheduleId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public Integer getTherapistId() {
		return therapistId;
	}

	public void setTherapistId(Integer therapistId) {
		this.therapistId = therapistId;
	}

	public Integer getTherapyId() {
		return therapyId;
	}

	public void setTherapyId(Integer therapyId) {
		this.therapyId = therapyId;
	}

	public Long getTherapistScheduleId() {
		return therapistScheduleId;
	}

	public void setTherapistScheduleId(Long therapistScheduleId) {
		this.therapistScheduleId = therapistScheduleId;
	}

	public String getTherapistName() {
		return therapistName;
	}

	public void setTherapistName(String therapistName) {
		this.therapistName = therapistName;
	}

	@Override
	public String toString() {
		return "ClientEvent [clientId=" + clientId + ", clientScheduleId=" + clientScheduleId + ", clientName="
				+ clientName + ", clientEmail=" + clientEmail + ", therapistId=" + therapistId + ", therapyId="
				+ therapyId + ", therapistScheduleId=" + therapistScheduleId + ", therapistName=" + therapistName
				+ ", getDayName()=" + getDayName() + ", getStatus()=" + getStatus() + ", getGroupId()=" + getGroupId()
				+ ", getOrgStartDate()=" + getOrgStartDate() + ", getOrgEndDate()=" + getOrgEndDate() + ", getTitle()="
				+ getTitle() + ", getStartDate()=" + getStartDate() + ", getStartTime()=" + getStartTime()
				+ ", getEndDate()=" + getEndDate() + ", getEndTime()=" + getEndTime() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
