package com.llk.therapist.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestParams {
	private String therapistId;
	private String srchTxt;
	private int start;
	private int limit;
	private Integer clientId;
	private Integer therapyId;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private Long therapistScheduleId;
	private Integer clientScheduleId;
	private String days;

	public List<String> getDays() {
		List<String> ldays = null;
		if (days != null && days.trim().length() > 0) {
			ldays = new ArrayList<>();
			ldays=Arrays.asList(days.split(","));
		}
		return ldays;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Long getTherapistScheduleId() {
		return therapistScheduleId;
	}

	public void setTherapistScheduleId(Long therapistScheduleId) {
		this.therapistScheduleId = therapistScheduleId;
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

	public Integer getTherapyId() {
		return therapyId;
	}

	public void setTherapyId(Integer therapyId) {
		this.therapyId = therapyId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTherapistId() {
		return therapistId;
	}

	public void setTherapistId(String therapistId) {
		this.therapistId = therapistId;
	}

	public String getSrchTxt() {
		return srchTxt;
	}

	public void setSrchTxt(String srchTxt) {
		this.srchTxt = srchTxt;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "RequestParams [therapistId=" + therapistId + ", srchTxt=" + srchTxt + ", start=" + start + ", limit="
				+ limit + ", clientId=" + clientId + ", therapyId=" + therapyId + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", therapistScheduleId=" + therapistScheduleId + ", clientScheduleId=" + clientScheduleId + ", days="
				+ days + "]";
	}

}
