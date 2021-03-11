package com.llk.client.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.client.util.Constants;

@JsonInclude(Include.NON_NULL)
public class ClientAvailabilityOccurence {
	private Integer occurenceId;
	private Integer availabilityId;
	private String frequencyType;
	private Integer frequencyRepeats;
	private String weekDays;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate endDate;
	private Integer dayNo;
	private Integer weekOfMonth;
	private Integer monthOfYear;
	private String status;

	public Integer getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(Integer availabilityId) {
		this.availabilityId = availabilityId;
	}

	public Integer getOccurenceId() {
		return occurenceId;
	}

	public void setOccurenceId(Integer occurenceId) {
		this.occurenceId = occurenceId;
	}

	public String getFrequencyType() {
		return frequencyType;
	}

	public void setFrequencyType(String frequencyType) {
		this.frequencyType = frequencyType;
	}

	public Integer getFrequencyRepeats() {
		return frequencyRepeats;
	}

	public void setFrequencyRepeats(Integer frequencyRepeats) {
		this.frequencyRepeats = frequencyRepeats;
	}

	public String getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getDayNo() {
		return dayNo;
	}

	public void setDayNo(Integer dayNo) {
		this.dayNo = dayNo;
	}

	public Integer getWeekOfMonth() {
		return weekOfMonth;
	}

	public void setWeekOfMonth(Integer weekOfMonth) {
		this.weekOfMonth = weekOfMonth;
	}

	public Integer getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(Integer monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ClientAvailabilityOccurence [occurenceId=" + occurenceId + ", availabilityId=" + availabilityId
				+ ", frequencyType=" + frequencyType + ", frequencyRepeats=" + frequencyRepeats + ", weekDays="
				+ weekDays + ", endDate=" + endDate + ", dayNo=" + dayNo + ", weekOfMonth=" + weekOfMonth
				+ ", monthOfYear=" + monthOfYear + ", status=" + status + "]";
	}

}
