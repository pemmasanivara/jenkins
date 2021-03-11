package com.llk.notification.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CalendarEvent {
	private String subject;
	private EventBody body;
	private EventTime start;
	private EventTime end;
	private EventLocation location;
	private List<EventAttend> attendees;
	private String id;
	private Long clientScheduleId;

	public Long getClientScheduleId() {
		return clientScheduleId;
	}

	public void setClientScheduleId(Long clientScheduleId) {
		this.clientScheduleId = clientScheduleId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public EventBody getBody() {
		return body;
	}

	public void setBody(EventBody body) {
		this.body = body;
	}

	public EventTime getStart() {
		return start;
	}

	public void setStart(EventTime start) {
		this.start = start;
	}

	public EventTime getEnd() {
		return end;
	}

	public void setEnd(EventTime end) {
		this.end = end;
	}

	public EventLocation getLocation() {
		return location;
	}

	public void setLocation(EventLocation location) {
		this.location = location;
	}

	public List<EventAttend> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<EventAttend> attendees) {
		this.attendees = attendees;
	}

	@Override
	public String toString() {
		return "CalendarEvent [subject=" + subject + ", body=" + body + ", start=" + start + ", end=" + end
				+ ", location=" + location + ", attendees=" + attendees + ", id=" + id + ", clientScheduleId="
				+ clientScheduleId + "]";
	}

}
