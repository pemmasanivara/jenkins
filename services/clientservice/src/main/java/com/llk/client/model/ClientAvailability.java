package com.llk.client.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.client.util.Constants;

@JsonInclude(Include.NON_NULL)
public class ClientAvailability {
	private Integer availabilityId;
	private Integer clientId;
	private Integer therapistId;
	private Integer therapyId;
	private String therapyName;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate startDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime startTime;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate endDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime endTime;
	private String status;
	private ClientAvailabilityOccurence scheduleOccurence;		

	public String getTherapyName() {
		return therapyName;
	}

	public void setTherapyName(String therapyName) {
		this.therapyName = therapyName;
	}

	public Integer getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(Integer availabilityId) {
		this.availabilityId = availabilityId;
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

	public Integer getTherapyId() {
		return therapyId;
	}

	public void setTherapyId(Integer therapyId) {
		this.therapyId = therapyId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ClientAvailabilityOccurence getScheduleOccurence() {
		return scheduleOccurence;
	}

	public void setScheduleOccurence(ClientAvailabilityOccurence scheduleOccurence) {
		this.scheduleOccurence = scheduleOccurence;
	}

	@Override
	public String toString() {
		return "ClientAvailability [availabilityId=" + availabilityId + ", clientId=" + clientId + ", therapistId="
				+ therapistId + ", therapyId=" + therapyId + ", startDate=" + startDate + ", startTime=" + startTime
				+ ", endDate=" + endDate + ", endTime=" + endTime + ", status=" + status + ", scheduleOccurence="
				+ scheduleOccurence + "]";
	}
	
	

}
