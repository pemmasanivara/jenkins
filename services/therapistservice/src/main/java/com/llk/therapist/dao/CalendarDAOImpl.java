package com.llk.therapist.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.llk.common.model.Lov;
import com.llk.common.model.ScheduleEvent;
import com.llk.therapist.exception.CalendarException;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapyEvent;
import com.llk.therapist.util.Constants;
import com.llk.therapist.util.Util;

@Repository
public class CalendarDAOImpl implements CalendarDAO {
	private static final Logger logger = LoggerFactory.getLogger(CalendarDAOImpl.class);
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<TherapyEvent> getTherapyEvents(RequestParams params) throws CalendarException {
		logger.info("CalendarDAOImpl:Inside getTherapyEvents()-->" + params.toString());
		MapSqlParameterSource in = new MapSqlParameterSource();
		StringBuffer sql = new StringBuffer(Constants.SQL_THERAPY_EVENTS);
		if (params.getTherapyId() != null) {
			sql.append("\n AND therapy_id=:therapy_id");
			in.addValue("therapy_id", params.getTherapyId());
		}
		if (params.getTherapistId() != null) {
			sql.append("\n AND therapist_id=:therapist_id");
			in.addValue("therapist_id", params.getTherapistId());
		}		
		logger.info("sql-->" + sql.toString());
		List<String> sdays = params.getDays();
		logger.info("sdays-->" + sdays);
		List<TherapyEvent> events = namedParameterJdbcTemplate.query(sql.toString(), in, (rs, rowNum) -> {
			TherapyEvent event = new TherapyEvent();
			event.setTherapistId(rs.getInt("therapist_id"));
			event.setTherapistName(rs.getString("name"));
			event.setTitle(rs.getString("therapy_name"));
			event.setTherapyId(rs.getInt("therapy_id"));
			event.setTherapistScheduleId(rs.getLong("schedule_id"));
			event.setGroupId(Integer.valueOf(rs.getString("therapist_id")+""+rs.getInt("therapy_id")));
			LocalDate startDate = rs.getDate("start_date").toLocalDate();
			String dayName = startDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);			
			event.setStartDate(startDate);
			event.setOrgStartDate(rs.getDate("start_date").toLocalDate());
			event.setEndDate(rs.getDate("start_date").toLocalDate());
			event.setOrgEndDate(rs.getDate("end_date").toLocalDate());
			event.setStartTime(rs.getTime("start_time").toLocalTime());
			event.setEndTime(rs.getTime("end_time").toLocalTime());
			event.setStatus(rs.getString("status"));
			int index = 0;
			if (sdays != null && sdays.size() > 0) {
				index = sdays.indexOf(String.valueOf(dayName));
			}			
			event.setDayName(dayName);
			event.setHide(index != -1 ? false : true);
			return event;
		});

		logger.info("events-->" + events);

		if (events != null && events.size() > 0) {			
			// Apply date Filters
			events = events.stream().filter(fev -> {
				boolean include = true;				
				if (params.getStartDate() != null) {
					include = !fev.getStartDate()
							.isBefore(Util.parseDate(params.getStartDate(), Constants.DATE_FORMAT));
				}
				if (include && params.getEndDate() != null) {
					include = !fev.getEndDate().isAfter(Util.parseDate(params.getEndDate(), Constants.DATE_FORMAT));
				}
				if(include && params.getStartTime()!=null) {
					LocalTime rqs=Util.parseTime(params.getStartTime(), Constants.TIME_FORMAT);					
					include=!fev.getStartTime().isBefore(rqs);					
				}	
				
				if(include && params.getEndTime()!=null) {
					LocalTime rqe=Util.parseTime(params.getEndTime(), Constants.TIME_FORMAT);					
					include=!fev.getEndTime().isAfter(rqe);					
				}				
				
				return include;

			}).collect(Collectors.toList());

			logger.info("events-->" + events);

			List<Long> therapistScheduleIds = events.stream().map(TherapyEvent::getTherapistScheduleId).distinct()
					.collect(Collectors.toList());

			if (therapistScheduleIds != null && therapistScheduleIds.size() > 0) {
				logger.info("therapistScheduleIds-->" + therapistScheduleIds);
				List<ScheduleEvent> schedulesEvents = getScheduledEvents(therapistScheduleIds, params.getClientId());
				logger.info("schedulesEvents size-->" + schedulesEvents.size());
				events.stream().forEach(event -> {	
					schedulesEvents.stream().forEach(sev -> {
						if (sev.getTherapistScheduleId().intValue() == event.getTherapistScheduleId().intValue()
								&& sev.getTherapyId().intValue() == event.getTherapyId()
								&& sev.getStartDate().isEqual(event.getStartDate())
								&& sev.getEndDate().isEqual(event.getEndDate())
								&& sev.getStartTime().compareTo(event.getStartTime()) == 0) {
							event.getScheduledEvents().add(sev);
							if (!event.getStatus().equalsIgnoreCase(Constants.STATUS_CANCELLED)) {
								if (sev.getStatus().equalsIgnoreCase(Constants.STATUS_CANCELLED)) {
									event.setStatus(Constants.STATUS_OPEN);
								} else {
									event.setStatus(Constants.STATUS_BOOKED);
								}
							}
						}
					});
				});
			}

		}
		return events;
	}

	@Override
	public List<Lov> getValues(String lovType) throws CalendarException {
		logger.info("CalendarDAOImpl:Inside getValues()-->"+lovType);		
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("LOOKUP_TYPE", lovType);
		return namedParameterJdbcTemplate.query(Constants.SQL_LOV, in, (rs, rowNum) -> {
			Lov lov = new Lov();
			lov.setValue(rs.getString("VALUE"));
			lov.setDisplay(rs.getString("DISPLAY"));
			return lov;
		});
	}

	@Override
	public List<ScheduleEvent> getScheduledEvents(List<Long> therapistScheduleIds, Integer clientId)
			throws CalendarException {
		String sql = Constants.SQL_SCHEDULED_EVENTS;
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_SCHEDULE_ID", therapistScheduleIds);
		if (clientId != null) {
			sql = sql + "AND CLIENT_ID=:CLIENT_ID";
			in.addValue("CLIENT_ID", clientId);
		}
		logger.info("sql-->" + sql);
		return namedParameterJdbcTemplate.query(Constants.SQL_SCHEDULED_EVENTS, in, (rs, rowNum) -> {
			ScheduleEvent event = new ScheduleEvent();
			event.setTherapistScheduleId(rs.getLong("THERAPIST_SCHEDULE_ID"));
			event.setTherapistId(rs.getInt("THERAPIST_ID"));
			event.setTherapistName(rs.getString("THERAPIST_NAME"));
			event.setClientId(rs.getInt("CLIENT_ID"));
			event.setClientScheduleId(rs.getInt("CLIENT_SCHEDULE_ID"));
			event.setClientName(rs.getString("CLIENT_NAME"));
			event.setClientEmail(rs.getString("CLIENT_EMAIL"));
			event.setTherapyId(rs.getInt("THERAPY_ID"));
			event.setTherapyName(rs.getString("THERAPY_NAME"));
			event.setStartDate(rs.getDate("START_DATE").toLocalDate());
			event.setEndDate(rs.getDate("END_DATE").toLocalDate());
			event.setStartTime(rs.getTime("START_TIME").toLocalTime());
			event.setEndTime(rs.getTime("END_TIME").toLocalTime());
			event.setStatus(rs.getString("STATUS"));
			return event;
		});

	}

	@Override
	public boolean calcelSchedule(Therapist therapist) throws CalendarException {
		logger.info("therapist-->" + therapist);
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapist.getTherapistId());
		in.addValue("THERAPIST_SCHEDULE_ID", therapist.getSchedule().getScheduleId());
		in.addValue("THERAPY_ID", therapist.getTherapyId());
		in.addValue("CLIENT_ID", null);
		in.addValue("CLIENT_SCHEDULE_ID", null);
		in.addValue("START_DATE", java.sql.Date.valueOf(therapist.getSchedule().getCancelStartDate()), Types.DATE);
		in.addValue("START_TIME", java.sql.Time.valueOf(therapist.getSchedule().getCancelStartTime()), Types.TIME);
		in.addValue("END_DATE", java.sql.Date.valueOf(therapist.getSchedule().getCancelEndDate()), Types.DATE);
		in.addValue("END_TIME", java.sql.Time.valueOf(therapist.getSchedule().getCancelEndTime()), Types.TIME);
		int count = namedParameterJdbcTemplate.execute(Constants.SQL_CANCEL_SCHEDULE, in,
				new PreparedStatementCallback<Integer>() {
					@Override
					public Integer doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<ScheduleEvent> getCancelEvents(List<Integer> therapistScheduleIds) throws CalendarException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_SCHEDULE_ID", therapistScheduleIds);
		return namedParameterJdbcTemplate.query(Constants.SQL_CANCEL_SCHEDULES, in, (rs, rowNum) -> {
			ScheduleEvent event = new ScheduleEvent();
			event.setTherapistScheduleId(rs.getLong("THERAPIST_SCHEDULE_ID"));
			event.setTherapistId(rs.getInt("THERAPIST_ID"));
			event.setClientId(rs.getInt("CLIENT_ID"));
			event.setTherapyId(rs.getInt("THERAPY_ID"));
			event.setStartDate(rs.getDate("START_DATE").toLocalDate());
			event.setEndDate(rs.getDate("END_DATE").toLocalDate());
			event.setStartTime(rs.getTime("START_TIME").toLocalTime());
			event.setEndTime(rs.getTime("END_TIME").toLocalTime());
			return event;
		});
	}

	@Override
	public void cancelAllTherapistSchedules(Integer therapistId) throws CalendarException {
		logger.info("cancelAllTherapistSchedules therapistId-->" + therapistId);
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapistId);
		int count = namedParameterJdbcTemplate.update(Constants.SQL_INACTIVE_ALL_SCHEDULES, in);
		logger.info("cancelAllTherapistSchedules count-->" + count);

	}

	@Override
	public void updateSchedule(Integer therapistId, Long scheduleId, String status) throws CalendarException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapistId);
		in.addValue("SCHEDULE_ID", scheduleId);
		in.addValue("STATUS", status);
		int count = namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_STATUS_SCHEDULES, in);
		logger.info("cancelAllTherapistSchedules count-->" + count);
		
	}

	@Override
	public void updateSchedules(List<Long> scheduleIds, String status) throws CalendarException {
		
		
	}

}
