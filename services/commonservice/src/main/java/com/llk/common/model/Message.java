package com.llk.common.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Message {
	private String type;
	private Integer clientId;
	private Integer therapistId;
	private String clientEmail;
	private String therapistEmail;
	private String clientName;
	private String therapistName;
	private String clientPhone;
	private String therapistPhone;
	private String therapyName;
	private Integer therapyId;
	private List<Lov> communicationMode;
	private Integer clientScheduleId;
	private Long therapistAvailabilityId;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String outLookEventId;	

	public String getOutLookEventId() {
		return outLookEventId;
	}

	public void setOutLookEventId(String outLookEventId) {
		this.outLookEventId = outLookEventId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Integer getClientScheduleId() {
		return clientScheduleId;
	}

	public void setClientScheduleId(Integer clientScheduleId) {
		this.clientScheduleId = clientScheduleId;
	}

	public Long getTherapistAvailabilityId() {
		return therapistAvailabilityId;
	}

	public void setTherapistAvailabilityId(Long therapistAvailabilityId) {
		this.therapistAvailabilityId = therapistAvailabilityId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getTherapistPhone() {
		return therapistPhone;
	}

	public void setTherapistPhone(String therapistPhone) {
		this.therapistPhone = therapistPhone;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getTherapistId() {
		return therapistId;
	}

	public void setTherapistId(Integer therapistId) {
		this.therapistId = therapistId;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getTherapistEmail() {
		return therapistEmail;
	}

	public void setTherapistEmail(String therapistEmail) {
		this.therapistEmail = therapistEmail;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getTherapistName() {
		return therapistName;
	}

	public void setTherapistName(String therapistName) {
		this.therapistName = therapistName;
	}

	public List<Lov> getCommunicationMode() {
		return communicationMode;
	}

	public void setCommunicationMode(List<Lov> communicationMode) {
		this.communicationMode = communicationMode;
	}

	@Override
	public String toString() {
		return "Message [type=" + type + ", clientId=" + clientId + ", therapistId=" + therapistId + ", clientEmail="
				+ clientEmail + ", therapistEmail=" + therapistEmail + ", clientName=" + clientName + ", therapistName="
				+ therapistName + ", clientPhone=" + clientPhone + ", therapistPhone=" + therapistPhone
				+ ", therapyName=" + therapyName + ", therapyId=" + therapyId + ", communicationMode="
				+ communicationMode + ", clientScheduleId=" + clientScheduleId + ", therapistAvailabilityId="
				+ therapistAvailabilityId + ", startDate=" + startDate + ", endDate=" + endDate + ", startTime="
				+ startTime + ", endTime=" + endTime + ", outLookEventId=" + outLookEventId + "]";
	}

	

}
