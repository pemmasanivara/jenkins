package com.llk.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ScheduleEvent extends Event {
	private Integer therapistId;
	private Integer clientId;
	private String therapyName;
	private Integer therapyId;
	private String status;
	private String therapistName;
	private String clientName;	
	private Integer clientScheduleId;
	private String clientEmail;	
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
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getTherapyName() {
		return therapyName;
	}
	public void setTherapyName(String therapyName) {
		this.therapyName = therapyName;
	}
	public Integer getTherapyId() {
		return therapyId;
	}
	public void setTherapyId(Integer therapyId) {
		this.therapyId = therapyId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTherapistName() {
		return therapistName;
	}
	public void setTherapistName(String therapistName) {
		this.therapistName = therapistName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Integer getClientScheduleId() {
		return clientScheduleId;
	}
	public void setClientScheduleId(Integer clientScheduleId) {
		this.clientScheduleId = clientScheduleId;
	}
	@Override
	public String toString() {
		return "ScheduleEvent [therapistId=" + therapistId + ", clientId=" + clientId + ", therapyName=" + therapyName
				+ ", therapyId=" + therapyId + ", status=" + status + ", therapistName=" + therapistName
				+ ", clientName=" + clientName + ", clientScheduleId=" + clientScheduleId + ", clientEmail="
				+ clientEmail + ", getDayName()=" + getDayName() + ", getGroupId()=" + getGroupId()
				+ ", getOrgStartDate()=" + getOrgStartDate() + ", getOrgEndDate()=" + getOrgEndDate()
				+ ", getTherapistScheduleId()=" + getTherapistScheduleId() + ", getTitle()=" + getTitle()
				+ ", getStartDate()=" + getStartDate() + ", getStartTime()=" + getStartTime() + ", getEndDate()="
				+ getEndDate() + ", getEndTime()=" + getEndTime() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
}
