package com.llk.notification.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.llk.common.model.Lov;
import com.llk.common.model.Message;
import com.llk.notification.model.CalendarEvent;
import com.llk.notification.model.EmailAddress;
import com.llk.notification.model.EventAttend;
import com.llk.notification.model.EventBody;
import com.llk.notification.model.EventLocation;
import com.llk.notification.model.EventTime;
import com.llk.notification.util.Constants;

@Repository
public class TherapistDAOImpl implements TherapistDAO {
	private static final Logger logger = LoggerFactory.getLogger(TherapistDAOImpl.class);
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Message> getAllTherapists(Integer therapistId) {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapistId);
		List<Message> messages = namedParameterJdbcTemplate.query(Constants.SQL_THERAPIST_ALL, in, (rs, rowNum) -> {
			Message message = new Message();
			message.setTherapistName(rs.getString("NAME"));
			message.setTherapistEmail(rs.getString("EMAIL"));
			message.setTherapistPhone(rs.getString("PHONE"));
			message.setTherapistId(rs.getInt("THERAPIST_ID"));
			String mode1 = rs.getString("communication_mode");
			String mode2 = rs.getString("attribute1");
			List<Lov> communicationMode = new ArrayList<Lov>();
			if (mode1 != null) {
				Lov l = new Lov();
				l.setValue("1");
				l.setDisplay(mode1);
				communicationMode.add(l);
			}
			if (mode2 != null) {
				Lov l = new Lov();
				l.setValue("2");
				l.setDisplay(mode2);
				communicationMode.add(l);
			}
			message.setCommunicationMode(communicationMode);
			return message;
		});
		return messages;
	}

	@Override
	public Message getTherapist(Integer therapistId) {
		logger.info("getTherapist-->" + therapistId);
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapistId);
		return namedParameterJdbcTemplate.query(Constants.SQL_THERAPIST, in, rs -> {
			rs.first();
			Message message = new Message();
			message.setTherapistName(rs.getString("NAME"));
			message.setTherapistEmail(rs.getString("EMAIL"));
			message.setTherapistPhone(rs.getString("PHONE"));
			message.setTherapistId(rs.getInt("THERAPIST_ID"));
			String mode1 = rs.getString("communication_mode");
			String mode2 = rs.getString("attribute1");
			List<Lov> communicationMode = new ArrayList<Lov>();
			if (mode1 != null) {
				Lov l = new Lov();
				l.setValue("1");
				l.setDisplay(mode1);
				communicationMode.add(l);
			}
			if (mode2 != null) {
				Lov l = new Lov();
				l.setValue("2");
				l.setDisplay(mode2);
				communicationMode.add(l);
			}
			message.setCommunicationMode(communicationMode);

			return message;
		});
	}

	@Override
	public List<Message> getClients(Integer therapistId, Integer therapyId, Long therapistScheduleId) {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapistId);
		in.addValue("THERAPY_ID", therapyId);
		in.addValue("THERAPIST_SCHEDULE_ID", therapistScheduleId);
		return namedParameterJdbcTemplate.query(Constants.SQL_EVENT_CANCEL_CLIENTS, in, (rs, rowNum) -> {
			Message msg = new Message();
			msg.setClientName(rs.getString("CLIENT_NAME"));
			msg.setTherapistName(rs.getString("THERAPIST_NAME"));
			msg.setTherapyName(rs.getString("THERAPY_NAME"));
			msg.setClientEmail(rs.getString("CLIENT_EMAIL"));
			msg.setClientPhone(rs.getString("CLIENT_PHONE"));
			msg.setOutLookEventId(rs.getString("OUTLOOK_EVENT_ID"));
			msg.setTherapistEmail(rs.getString("THERAPIST_EMAIL"));
			String mode1 = rs.getString("CLIENT_COMM_MODE");
			String mode2 = rs.getString("CLIENT_COMM_MODE1");
			List<Lov> communicationMode = new ArrayList<Lov>();
			if (mode1 != null) {
				Lov l = new Lov();
				l.setValue("1");
				l.setDisplay(mode1);
				communicationMode.add(l);
			}
			if (mode2 != null) {
				Lov l = new Lov();
				l.setValue("2");
				l.setDisplay(mode2);
				communicationMode.add(l);
			}
			msg.setCommunicationMode(communicationMode);
			return msg;
		});

	}

	@Override
	public List<CalendarEvent> getTherapistOutLookEvents(Integer clientId, Integer theraistId, Integer therapyId) {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("CLIENT_ID", clientId);
		in.addValue("THERAPIST_ID", theraistId);
		in.addValue("THERAPY_ID", therapyId);
		return namedParameterJdbcTemplate.query(Constants.THERAPIST_OUTLLOK_SCHEDULES, in, (rs, rowNum) -> {
			String clientName = rs.getString("CLIENT_NAME");
			String clientEmail = rs.getString("CLIENT_EMAIL");
			String therapistName = rs.getString("THERAPIST_NAME");
			String therapyName = rs.getString("THERAPY_NAME");
			LocalDate startDate = rs.getDate("START_DATE").toLocalDate();
			LocalDate endDate = rs.getDate("END_DATE").toLocalDate();
			LocalTime startTime = rs.getTime("START_TIME").toLocalTime();
			LocalTime endTime = rs.getTime("END_TIME").toLocalTime();
			CalendarEvent event = new CalendarEvent();
			event.setClientScheduleId(rs.getLong("CLIENT_SCHEDULE_ID"));
			event.setSubject(therapyName);
			EventBody body=new EventBody();
			StringBuffer content=new StringBuffer();
			content.append(therapyName).append(" with ").append(therapistName);			
			body.setContent(content.toString());
			body.setContentType("HTML");
			event.setBody(body);
			EventLocation location=new EventLocation();
			location.setDisplayName("Conference Room");
			event.setLocation(location);				
			EventAttend attend=new EventAttend();			
			EmailAddress email=new EmailAddress();
			email.setAddress(clientEmail);
			email.setName(clientName);
			attend.setEmailAddress(email);
			attend.setType("required");			
			List<EventAttend> lattend=new ArrayList<>();
			lattend.add(attend);			
			event.setAttendees(lattend);				
			EventTime start=new EventTime();
			start.setDateTime(startDate.toString()+"T"+startTime.toString());
			start.setTimeZone("Eastern Standard Time");			
			event.setStart(start);			
			EventTime end=new EventTime();
			end.setDateTime(endDate.toString()+"T"+endTime.toString());
			end.setTimeZone("Eastern Standard Time");
			event.setEnd(end);			
			return event;
		});
	}

}
