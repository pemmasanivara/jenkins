package com.llk.client.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llk.client.util.Constants;

public class RequestParams {
	private String srchTxt;
	private Integer clientId;
	private Integer start = 0;
	private Integer limit = 0;
	private Integer therapyId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIME_FORMAT)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime startTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIME_FORMAT)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime endTime;
	private String status;
	private String therapistSchdeuleId;

	public List<Integer> getTherapistSchdeuleId() {
		List<Integer> ids = null;
		if (therapistSchdeuleId != null && therapistSchdeuleId.trim().length() > 0) {
			ids = new ArrayList<>();
			String[] data = therapistSchdeuleId.split(",");
			if (data.length > 0) {
				for (String id : data) {
					ids.add(Integer.valueOf(id));
				}
			}
		}
		return ids;
	}

	public void setTherapistSchdeuleId(String therapistSchdeuleId) {
		this.therapistSchdeuleId = therapistSchdeuleId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getSrchTxt() {
		return srchTxt;
	}

	public void setSrchTxt(String srchTxt) {
		this.srchTxt = srchTxt;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "RequestParams [srchTxt=" + srchTxt + ", clientId=" + clientId + ", start=" + start + ", limit=" + limit
				+ ", therapyId=" + therapyId + ", startDate=" + startDate + ", endDate=" + endDate + ", startTime="
				+ startTime + ", endTime=" + endTime + ", status=" + status + ", therapistSchdeuleId="
				+ therapistSchdeuleId + "]";
	}

}
