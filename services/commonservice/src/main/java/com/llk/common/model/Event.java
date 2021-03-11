package com.llk.common.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.common.util.Constants;

@JsonInclude(Include.NON_NULL)
public class Event implements Cloneable{
	private String title;
	private Long therapistScheduleId;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate startDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime startTime;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate endDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime endTime;	
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate orgStartDate;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate orgEndDate;
	
	private int groupId;
	private String status;	
	
	private String dayName;
	

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public LocalDate getOrgStartDate() {
		return orgStartDate;
	}

	public void setOrgStartDate(LocalDate orgStartDate) {
		this.orgStartDate = orgStartDate;
	}

	public LocalDate getOrgEndDate() {
		return orgEndDate;
	}

	public void setOrgEndDate(LocalDate orgEndDate) {
		this.orgEndDate = orgEndDate;
	}	

	public Long getTherapistScheduleId() {
		return therapistScheduleId;
	}

	public void setTherapistScheduleId(Long therapistScheduleId) {
		this.therapistScheduleId = therapistScheduleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Override
	public String toString() {
		return "Event [title=" + title + ", therapistScheduleId=" + therapistScheduleId + ", startDate=" + startDate
				+ ", startTime=" + startTime + ", endDate=" + endDate + ", endTime=" + endTime + ", orgStartDate="
				+ orgStartDate + ", orgEndDate=" + orgEndDate + ", groupId=" + groupId + ", status=" + status
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}



}
