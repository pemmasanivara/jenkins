package com.llk.therapist.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.common.model.Event;
import com.llk.common.model.ScheduleEvent;

@JsonInclude(Include.NON_NULL)
public class TherapyEvent extends Event {
	private int therapyId;
	private int therapistId;
	private String therapistName;
	private boolean hide=false;
	private List<ScheduleEvent> scheduledEvents=new ArrayList<>();

	public List<ScheduleEvent> getScheduledEvents() {
		return scheduledEvents;
	}

	public void setScheduledEvents(List<ScheduleEvent> scheduledEvents) {
		this.scheduledEvents = scheduledEvents;
	}

	public String getTherapistName() {
		return therapistName;
	}

	public void setTherapistName(String therapistName) {
		this.therapistName = therapistName;
	}

	public int getTherapyId() {
		return therapyId;
	}

	public void setTherapyId(int therayId) {
		this.therapyId = therayId;
	}

	public int getTherapistId() {
		return therapistId;
	}

	public void setTherapistId(int therapistId) {
		this.therapistId = therapistId;
	}
	
	

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	@Override
	public String toString() {
		return "TherapyEvent [therapyId=" + therapyId + ", therapistId=" + therapistId + ", therapistName="
				+ therapistName + ", hide=" + hide + ", scheduledEvents=" + scheduledEvents + ", getDayName()="
				+ getDayName() + ", getStatus()=" + getStatus() + ", getGroupId()=" + getGroupId()
				+ ", getOrgStartDate()=" + getOrgStartDate() + ", getOrgEndDate()=" + getOrgEndDate()
				+ ", getTherapistScheduleId()=" + getTherapistScheduleId() + ", getTitle()=" + getTitle()
				+ ", getStartDate()=" + getStartDate() + ", getStartTime()=" + getStartTime() + ", getEndDate()="
				+ getEndDate() + ", getEndTime()=" + getEndTime() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}


}
