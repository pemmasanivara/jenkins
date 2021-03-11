package com.llk.client.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.llk.client.exception.CalendarException;
import com.llk.client.model.ClientEvent;
import com.llk.client.model.RequestParams;
import com.llk.client.model.Schedule;
import com.llk.client.util.Constants;
import com.llk.common.model.Lov;

@Repository
public class CalendarDAOImpl implements CalendarDAO {
	private static final Logger logger = LoggerFactory.getLogger(ClientDAOImpl.class);

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Schedule> saveClientSchedule(List<Schedule> schedules) throws CalendarException {		
		logger.info("Inside saveClientSchedule()");
		logger.info("schedules-->"+schedules);
		if(schedules!=null && schedules.size() > 0) {
			List<Map<String, Object>> batchValues = new ArrayList<>(schedules.size());			
			@SuppressWarnings("unchecked")
			Map<String,?>[] myDataArray = new HashMap[schedules.size()];
			int i=0;
			for(Schedule schedule:schedules) {
				HashMap<String,Object> in = new HashMap<>();
				in.put("CLIENT_ID", schedule.getClientId());
				in.put("THERAPIST_ID", schedule.getTherapistId());
				in.put("SCHEDULE_ID", schedule.getTherapistScheduleId());
				in.put("THERAPY_ID", schedule.getTherapyId());
				in.put("START_DATE", java.sql.Date.valueOf(schedule.getStartDate()));
				in.put("END_DATE", java.sql.Date.valueOf(schedule.getEndDate()));
				in.put("START_TIME", java.sql.Time.valueOf(schedule.getStartTime()));
				in.put("END_TIME", java.sql.Time.valueOf(schedule.getEndTime()));
				in.put("STATUS", com.llk.common.util.Constants.STATUS_BOOKED);
				myDataArray[i]=in;
				i++;
			}
			logger.info("batchValues-->"+batchValues);
			namedParameterJdbcTemplate.batchUpdate(Constants.SQL_SAVE_SCHEDULE, myDataArray);				
		}		
		return schedules;
	}

	@Override
	public List<ClientEvent> getClientEvents(RequestParams params) throws CalendarException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		StringBuffer sql = new StringBuffer(Constants.SQL_CLIENT_EVENTS);
		if (params.getClientId() != null) {
			sql.append("\n AND CLIENT_ID=:CLIENT_ID");
			in.addValue("CLIENT_ID", params.getClientId());
		}
		if (params.getTherapyId() != null) {
			sql.append("\n AND therapy_id=:therapy_id");
			in.addValue("therapy_id", params.getTherapyId());
		}
		if (params.getStartDate() != null) {
			sql.append("\n AND start_date=:start_date");
			in.addValue("start_date", java.sql.Date.valueOf(params.getStartDate()));
		}
		if (params.getEndDate() != null) {
			sql.append("\n AND end_date=:end_date");
			in.addValue("end_date", java.sql.Date.valueOf(params.getEndDate()));
		}
		if (params.getStartTime() != null) {
			sql.append("\n AND start_time=:start_time");
			in.addValue("start_time", java.sql.Time.valueOf(params.getStartTime()));
		}
		if (params.getEndTime() != null) {
			sql.append("\n AND end_time=:end_time");
			in.addValue("end_time", java.sql.Time.valueOf(params.getEndTime()));
		}
		logger.info("params-->"+params);
		if(params.getTherapistSchdeuleId()!=null && params.getTherapistSchdeuleId().size() > 0) {
			sql.append("\n AND THERAPIST_SCHEDULE_ID IN(:THERAPIST_SCHEDULE_ID)");			
			in.addValue("THERAPIST_SCHEDULE_ID", params.getTherapistSchdeuleId());
		}		
		if(params.getStatus()!=null) {
			sql.append("\n AND STATUS =:STATUS");	
			in.addValue("STATUS",params.getStatus());			
		}
		return namedParameterJdbcTemplate.query(sql.toString(), in, (rs, rowNum) -> {
			ClientEvent event = new ClientEvent();
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
	public List<Lov> getValues(String lovType) throws CalendarException {		
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("LOOKUP_TYPE", lovType);
		return namedParameterJdbcTemplate.query(Constants.SQL_LOV, in, (rs,rowNum)->{
			Lov lov=new Lov();
			lov.setValue(rs.getString("VALUE"));
			lov.setDisplay(rs.getString("DISPLAY"));
			return lov;
		});
	}

	@Override
	public boolean cancelSchedule(Schedule schedule) throws CalendarException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("STATUS", com.llk.common.util.Constants.STATUS_CANCELLED);	
		String sql=Constants.SQL_CANCEL_SCHEDULE;
		if(schedule.getClientId()!=null) {
			sql=sql+"\n AND CLIENT_ID=:CLIENT_ID";
			in.addValue("CLIENT_ID", schedule.getClientId());
		}
		if(schedule.getClientScheduleId()!=null) {
			sql=sql+"\n AND CLIENT_SCHEDULE_ID=:CLIENT_SCHEDULE_ID";
			in.addValue("CLIENT_SCHEDULE_ID", schedule.getClientScheduleId());
		}
		namedParameterJdbcTemplate.update(sql, in);		
		return true;
	}

	@Override
	public List<Long> getAllTherapistScheduleIds(Integer clientId) throws CalendarException {		
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("CLIENT_ID",clientId);	
		return namedParameterJdbcTemplate.queryForList(Constants.SQL_THERAPIST_SCHEDULES_ID_ALL, in, Long.class);
	}

	@Override
	public void updateTherapistSchedules(List<Long> scheduleIds,String status) throws CalendarException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("STATUS", status);
		in.addValue("SCHEDULE_ID", scheduleIds);
		namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_THERAPIST_SCHEDULE_STATUS, in);	
		
	}

	@Override
	public boolean cancelAllSchedules(Integer clientId) throws CalendarException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("CLIENT_ID",clientId);	
		in.addValue("STATUS",com.llk.common.util.Constants.STATUS_CANCELLED);	
		int count=namedParameterJdbcTemplate.update(Constants.SQL_CANCEL_SCHEDULE, in);
		if(count > 0) return  true;
		return false;
	}

}
