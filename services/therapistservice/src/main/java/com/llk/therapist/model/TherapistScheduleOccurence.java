package com.llk.therapist.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TherapistScheduleOccurence {
	private Long scheduleId;
	private String frequenceType;
	private String weekDays;
	private Integer everyDay;
	private Integer everyWeek;
	private Integer everyMonth;
	private Integer day;
	private String every;
	private String status = "";
	private int occurenceId;

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getFrequenceType() {
		return frequenceType;
	}

	public void setFrequenceType(String frequenceType) {
		this.frequenceType = frequenceType;
	}

	public String getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public Integer getEveryDay() {
		if(everyDay == null) {
			everyDay=1;
		}
		return everyDay;
	}

	public void setEveryDay(Integer everyDay) {
		this.everyDay = everyDay;
	}

	public Integer getEveryWeek() {
		if(everyWeek == null) {
			everyWeek=1;
		}
		return everyWeek;
	}

	public void setEveryWeek(Integer everyWeek) {
		this.everyWeek = everyWeek;
	}

	public Integer getEveryMonth() {
		if(everyMonth == null) {
			everyMonth=1;
		}
		return everyMonth;
	}

	public void setEveryMonth(Integer everyMonth) {
		this.everyMonth = everyMonth;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		if(day == null) {
			day=1;
		}
		this.day = day;
	}

	public String getEvery() {
		return every;
	}

	public void setEvery(String every) {
		this.every = every;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOccurenceId() {
		return occurenceId;
	}

	public void setOccurenceId(int occurenceId) {
		this.occurenceId = occurenceId;
	}

	@Override
	public String toString() {
		return "TherapistScheduleOccurence [scheduleId=" + scheduleId + ", frequenceType=" + frequenceType
				+ ", weekDays=" + weekDays + ", everyDay=" + everyDay + ", everyWeek=" + everyWeek + ", everyMonth="
				+ everyMonth + ", day=" + day + ", every=" + every + ", status=" + status + ", occurenceId="
				+ occurenceId + "]";
	}
	

}
