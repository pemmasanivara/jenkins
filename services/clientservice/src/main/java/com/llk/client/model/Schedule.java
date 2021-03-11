package com.llk.client.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.client.util.Constants;

@JsonInclude(Include.NON_NULL)
public class Schedule {
	private Integer clientScheduleId;
	private Integer clientId;
	private Integer therapistId;
	private Integer therapyId;
	private String therapyName;
	private Long therapistScheduleId;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate startDate;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate endDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime startTime;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime endTime;
	private String status;
	private String attribute1;
	private String attribute2;
	private String attribute3;
	private String attribute4;

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getTherapyName() {
		return therapyName;
	}

	public void setTherapyName(String therapyName) {
		this.therapyName = therapyName;
	}

	public Integer getClientScheduleId() {
		return clientScheduleId;
	}

	public void setClientScheduleId(Integer clientScheduleId) {
		this.clientScheduleId = clientScheduleId;
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

	public Long getTherapistScheduleId() {
		return therapistScheduleId;
	}

	public void setTherapistScheduleId(Long therapistScheduleId) {
		this.therapistScheduleId = therapistScheduleId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Override
	public String toString() {
		return "Schedule [clientScheduleId=" + clientScheduleId + ", clientId=" + clientId + ", therapistId="
				+ therapistId + ", therapyId=" + therapyId + ", therapyName=" + therapyName + ", therapistScheduleId="
				+ therapistScheduleId + ", startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", status=" + status + ", attribute1=" + attribute1 + ", attribute2="
				+ attribute2 + ", attribute3=" + attribute3 + ", attribute4=" + attribute4 + "]";
	}

}
