package com.llk.therapist.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.therapist.util.Constants;

@JsonInclude(Include.NON_NULL)
public class TherapistSchedule {
	private Long scheduleId;
	private Integer therapistId;
	private Integer therapyId;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate startDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime startTime;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate endDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime endTime;
	private String status;
	
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate cancelStartDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime cancelStartTime;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate cancelEndDate;
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	private LocalTime cancelEndTime;
	

	private TherapistScheduleOccurence scheduleOccurence;

	public TherapistScheduleOccurence getScheduleOccurence() {
		return scheduleOccurence;
	}

	public void setScheduleOccurence(TherapistScheduleOccurence scheduleOccurence) {
		this.scheduleOccurence = scheduleOccurence;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
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

	public LocalDate getCancelStartDate() {
		return cancelStartDate;
	}

	public void setCancelStartDate(LocalDate cancelStartDate) {
		this.cancelStartDate = cancelStartDate;
	}

	public LocalTime getCancelStartTime() {
		return cancelStartTime;
	}

	public void setCancelStartTime(LocalTime cancelStartTime) {
		this.cancelStartTime = cancelStartTime;
	}

	public LocalDate getCancelEndDate() {
		return cancelEndDate;
	}

	public void setCancelEndDate(LocalDate cancelEndDate) {
		this.cancelEndDate = cancelEndDate;
	}

	public LocalTime getCancelEndTime() {
		return cancelEndTime;
	}

	public void setCancelEndTime(LocalTime cancelEndTime) {
		this.cancelEndTime = cancelEndTime;
	}

	@Override
	public String toString() {
		return "TherapistSchedule [scheduleId=" + scheduleId + ", therapistId=" + therapistId + ", therapyId="
				+ therapyId + ", startDate=" + startDate + ", startTime=" + startTime + ", endDate=" + endDate
				+ ", endTime=" + endTime + ", status=" + status + ", cancelStartDate=" + cancelStartDate
				+ ", cancelStartTime=" + cancelStartTime + ", cancelEndDate=" + cancelEndDate + ", cancelEndTime="
				+ cancelEndTime + ", scheduleOccurence=" + scheduleOccurence + "]";
	}
	
	

}
