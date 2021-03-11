package com.llk.therapist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.llk.common.model.Lov;
import com.llk.therapist.exception.TherapistException;
import com.llk.therapist.model.CommunicationMode;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapistLevel;
import com.llk.therapist.model.Therapists;
import com.llk.therapist.model.TherapyEvent;
import com.llk.therapist.model.TherapyType;
import com.llk.therapist.util.ConfigUtility;
import com.llk.therapist.util.Constants;
import com.llk.therapist.util.Util;

@Repository
public class TherapistDAOImpl implements TherapistDAO {

	private static final Logger logger = LoggerFactory.getLogger(TherapistDAOImpl.class);

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	ConfigUtility configUtility;

	@Override
	public Therapist createParty(Therapist therapist) throws TherapistException {
		logger.info("DAO:Inside createParty()");
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(Constants.SQL_CREATE_PARTY,
							Statement.RETURN_GENERATED_KEYS);
					ps.setObject(1, therapist.getFirstName());
					ps.setObject(2, therapist.getLastName());
					ps.setObject(3, therapist.getEmail());
					ps.setObject(4, therapist.getPhone());
					ps.setObject(5, therapist.getGender());
					ps.setObject(6, therapist.getDob());
					ps.setObject(7, therapist.getAvatarUrl());
					return ps;
				}
			}, keyHolder);
			if (keyHolder != null) {
				int partyId = keyHolder.getKey().intValue();
				logger.info("partyId -->" + partyId);
				therapist.setPartyId(partyId);
			}
		} catch (DuplicateKeyException ex) {
		    logger.error("Error-->",ex);
			throw new TherapistException(configUtility.getProperty("email.duplicate.error"));
		}catch(Exception ex) {
			   logger.error("Error-->",ex);
				throw new TherapistException(configUtility.getProperty("therapist.save.error"));
		}
		return therapist;
	}

	@Override
	public Therapist createTherapist(Therapist therapist) throws TherapistException {
		logger.info("DAO:therapist-->" + therapist.toString());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.getJdbcTemplate().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String modeId1 = null;
				String modeId2 = null;
				if (therapist.getCommunicationMode() != null) {
					modeId1 = therapist.getCommunicationMode().get(0).getDisplay();
					if (therapist.getCommunicationMode().size() > 1
							&& therapist.getCommunicationMode().get(1) != null) {
						modeId2 = therapist.getCommunicationMode().get(1).getDisplay();
					}
				}

				PreparedStatement ps = con.prepareStatement(Constants.SQL_CREATE_THERAPIST,
						Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1, therapist.getPartyId());
				ps.setObject(2, therapist.getLevelId());
				ps.setObject(3, Constants.STATUS_ACTIVE);
				ps.setObject(4, modeId1);
				ps.setObject(5, therapist.getBcbaId());
				ps.setObject(6, therapist.getCertifications());
				ps.setObject(7, modeId2);
				return ps;
			}
		}, keyHolder);
		if (keyHolder != null) {
			int therapistId = keyHolder.getKey().intValue();
			logger.info("DAO:therapistId-->" + therapistId);
			therapist.setTherapistId(therapistId);
		}

		return therapist;
	}

	@Override
	public Therapist saveTherapistSession(Therapist therapist) throws TherapistException {
		logger.info("DAO:Inside saveTherapistSession()");
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapist.getTherapistId());
		in.addValue("THERAPY_ID", therapist.getTherapyId());
		namedParameterJdbcTemplate.update(Constants.SQL_SAVE_THERAPIST_SESSION, in);
		return therapist;
	}

	@Override
	public Therapists searchTherapists(RequestParams params) throws TherapistException {
		logger.info("DAO:Inside searchTherapists()");
		logger.info(params.toString());
		Therapists therapists = new Therapists();
		List<Therapist> therapistsList = null;
		StringBuffer sql = new StringBuffer(Constants.SQL_SEARCH_THERAPIST);
		MapSqlParameterSource in = new MapSqlParameterSource();
		if (params.getSrchTxt() != null && !params.getSrchTxt().isEmpty()) {
			sql.append(
					"\n and ((first_name like :srchTxt or last_name like :srchTxt) || (email like :srchTxt) || (phone like:srchTxt) )");
			in.addValue("srchTxt", "%" + params.getSrchTxt() + "%");
		}
		if (params.getTherapistId() != null && !params.getTherapistId().isEmpty()) {
			sql.append("\n and therapist_id=:therapist_id");
			in.addValue("therapist_id", params.getTherapistId());
		}

		String countQuery = "select count(*) as count from(" + sql.toString() + ") as main_query";

		int totalCount = namedParameterJdbcTemplate.queryForObject(countQuery, in, Integer.class);

		logger.info("totalCount-->" + totalCount);

		therapists.setTotalCount(totalCount);
		if (totalCount > 0) {

			if (params.getLimit() == 0) {
				params.setLimit(10);
			}
			// Extract total records.
			if (params.getLimit() >= 0) {
				sql.append("\nLIMIT :limit ");
				in.addValue("limit", params.getLimit());
			}
			if (params.getStart() >= 0) {
				sql.append("\nOFFSET :start");
				in.addValue("start", params.getStart());
			}
			therapistsList = namedParameterJdbcTemplate.query(sql.toString(), in, (rs, rowNum) -> {
				Therapist therapist = new Therapist();
				therapist.setFirstName(rs.getString("FIRST_NAME"));
				therapist.setLastName(rs.getString("LAST_NAME"));
				therapist.setEmail(rs.getString("EMAIL"));
				therapist.setPhone(rs.getString("PHONE"));
				therapist.setTherapistId(rs.getInt("THERAPIST_ID"));
				therapist.setAvatarUrl(rs.getString("PROFILE_IMG_URL"));
				return therapist;
			});
		}

		therapists.setTherapists(therapistsList);
		return therapists;
	}

	@Override
	public boolean deleteTherapist(Integer bcbaId, Integer therapistId) throws TherapistException {
		logger.info("DAO:Inside deleteTherapist()");
		logger.info("DAO:therapistId-->" + therapistId);
		StringBuffer sql = new StringBuffer(Constants.SQL_DELETE_THERAPIST);
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("therapist_id", therapistId);
		if (bcbaId != null && bcbaId > 0) {
			sql.append(" AND bcba_id=:bcba_id");
			in.addValue("bcba_id", bcbaId);
		}
		int count = namedParameterJdbcTemplate.update(sql.toString(), in);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Therapist scheduleAvailability(Therapist therapist) throws TherapistException {
		logger.info("DAO:Inside scheduleAvailability()");
		logger.info(therapist.toString());
		List<TherapyEvent> events=Util.getTherapistScheduledEvents(therapist);
		logger.info("Events-->"+events.toString());
		logger.info("therapist.getSchedule().getScheduleOccurence()-->"+therapist.getSchedule().getScheduleOccurence());
		if(events!=null && events.size() > 0) {
			List<Map<String, Object>> batchValues = new ArrayList<>(events.size());	
			@SuppressWarnings("unchecked")
			Map<String,?>[] myDataArray = new HashMap[events.size()];
			int i=0;
			for(TherapyEvent event:events) {
				HashMap<String,Object> in = new HashMap<>();
				in.put("THERAPIST_ID", therapist.getTherapistId());
				in.put("THERAPY_ID", event.getTherapyId());
				in.put("START_DATE", java.sql.Date.valueOf(event.getStartDate()));
				in.put("START_TIME", java.sql.Time.valueOf(event.getStartTime()));
				in.put("END_DATE", java.sql.Date.valueOf(event.getEndDate()));
				in.put("END_TIME", java.sql.Time.valueOf(event.getEndTime()));
				in.put("STATUS", Constants.STATUS_OPEN);
				in.put("FREQUENCE_TYPE",therapist.getSchedule().getScheduleOccurence().getFrequenceType());
				in.put("EVERY_DAY", therapist.getSchedule().getScheduleOccurence().getEveryDay());
				in.put("EVERY_WEEK", therapist.getSchedule().getScheduleOccurence().getEveryWeek());
				in.put("EVERY_MONTH", therapist.getSchedule().getScheduleOccurence().getEveryMonth());
				in.put("WEEK_DAYS", therapist.getSchedule().getScheduleOccurence().getWeekDays());
				in.put("DAY", therapist.getSchedule().getScheduleOccurence().getDay());
				in.put("EVERY", therapist.getSchedule().getScheduleOccurence().getEvery());				
				myDataArray[i]=in;
				i++;
			}	
			logger.info("batchValues-->"+batchValues);
			namedParameterJdbcTemplate.batchUpdate(Constants.SQL_CREATE_THERAPIST_SCHEDULE, myDataArray);
		}		
		return therapist;
	}
	
	
	

	@Override
	public Therapist scheduleOccurence(Therapist therapist) throws TherapistException {
		logger.info("DAO:Inside scheduleOccurence()-->" + therapist.getSchedule().getScheduleOccurence());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("SCHEDULE_ID", therapist.getSchedule().getScheduleId());
		in.addValue("FREQUENCE_TYPE", therapist.getSchedule().getScheduleOccurence().getFrequenceType());
		in.addValue("EVERY_DAY", therapist.getSchedule().getScheduleOccurence().getEveryDay());		
		in.addValue("EVERY_WEEK", therapist.getSchedule().getScheduleOccurence().getEveryWeek());
		in.addValue("EVERY_MONTH", therapist.getSchedule().getScheduleOccurence().getEveryMonth());
		in.addValue("WEEK_DAYS", therapist.getSchedule().getScheduleOccurence().getWeekDays());
		in.addValue("DAY", therapist.getSchedule().getScheduleOccurence().getDay());
		in.addValue("EVERY", therapist.getSchedule().getScheduleOccurence().getEvery());		
		in.addValue("STATUS", Constants.STATUS_ACTIVE);
		namedParameterJdbcTemplate.update(Constants.SQL_CREATE_THERAPIST_SCHEDULE_OCCURENCE, in, keyHolder);
		return therapist;
	}

	@Override
	public Therapist getTherapistDetails(Integer therapistId) throws TherapistException {
		logger.info("DAO:Inside getTherapistDetails()-->" + therapistId);
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapistId);
		Therapist t = namedParameterJdbcTemplate.query(Constants.SQL_THERAPIST_DETAILS, in, (rs) -> {
			Therapist therapist = new Therapist();
			rs.beforeFirst();
			rs.first();
			therapist.setFirstName(rs.getString("FIRST_NAME"));
			therapist.setLastName(rs.getString("LAST_NAME"));
			therapist.setEmail(rs.getString("EMAIL"));
			therapist.setPhone(rs.getString("PHONE"));
			therapist.setTherapistId(rs.getInt("THERAPIST_ID"));
			therapist.setAvatarUrl(rs.getString("PROFILE_IMG_URL"));
			therapist.setCertifications(rs.getString("CERTIFICATAIONS"));
			therapist.setGender(rs.getString("GENDER"));
			therapist.setLevelId(rs.getInt("LEVEL_ID"));
			therapist.setLevelName(rs.getString("LEVEL_NAME"));
			therapist.setDob(rs.getDate("DOB").toLocalDate());
			String mode1 = rs.getString("COMMUNICATION_MODE");
			String mode2 = rs.getString("ATTRIBUTE1");
			// TODO this is not correct way..we have to due to last minute requirement
			// change
			List<Lov> commModes = new ArrayList<Lov>();
			if (mode1 != null) {
				Lov l = new Lov();
				l.setValue("1");
				l.setDisplay(mode1);
				commModes.add(l);
			}
			if (mode2 != null) {
				Lov l = new Lov();
				l.setValue("1");
				l.setDisplay(mode2);
				commModes.add(l);
			}
			therapist.setCommunicationMode(commModes);
			return therapist;
		});
		return t;
	}

	@Override
	public List<Therapist> listTherapists() throws TherapistException {
		logger.info("DAO:Inside listTherapists()");
		return namedParameterJdbcTemplate.query(Constants.SQL_THERAPISTS_ALL, (rs, rowNum) -> {
			Therapist therapist = new Therapist();
			therapist.setName(rs.getString("NAME"));
			therapist.setTherapistId(rs.getInt("THERAPIST_ID"));
			return therapist;
		});
	}

	@Override
	public Therapist updateParty(Therapist therapist) throws TherapistException {
		logger.info("DAO:Inside Therapist()-->" + therapist.toString());
		Integer partyId = getPartyId(therapist.getTherapistId());
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("FIRST_NAME", therapist.getFirstName());
		in.addValue("LAST_NAME", therapist.getLastName());
		in.addValue("DOB", java.sql.Date.valueOf(therapist.getDob()));
		in.addValue("EMAIL", therapist.getEmail());
		in.addValue("PHONE", therapist.getPhone());
		in.addValue("PARTY_ID", partyId);
		namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_PARTY, in);
		return therapist;
	}

	@Override
	public Therapist updateTherapist(Therapist therapist) throws TherapistException {
		logger.info("DAO:Inside updateTherapist()-->" + therapist.toString());
		String modeId1 = null;
		String modeId2 = null;
		if (therapist.getCommunicationMode() != null) {
			modeId1 = therapist.getCommunicationMode().get(0).getDisplay();
			if (therapist.getCommunicationMode().size() > 1 && therapist.getCommunicationMode().get(1) != null) {
				modeId2 = therapist.getCommunicationMode().get(1).getDisplay();
			}
		}
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("COMMUNICATION_MODE", modeId1);
		in.addValue("ATTRIBUTE1", modeId2);
		in.addValue("CERTIFICATAIONS", therapist.getCertifications());
		in.addValue("LEVEL_ID", therapist.getLevelId());
		in.addValue("THERAPIST_ID", therapist.getTherapistId());
		namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_THERAPIST, in);
		return therapist;
	}

	/**
	 * 
	 * @param therapist_id
	 * @return
	 * @throws TherapistException
	 */
	private Integer getPartyId(Integer therapist_id) throws TherapistException {
		logger.info("DAO:Inside getPartyId()-->" + therapist_id);
		return namedParameterJdbcTemplate.queryForObject(Constants.SQL_PARTY_ID,
				new MapSqlParameterSource().addValue("THERAPIST_ID", therapist_id), Integer.class);
	}

	@Override
	public boolean isValidTherapistAvailability(Therapist therapist) throws TherapistException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapist.getTherapistId());
		in.addValue("START_DATE", java.sql.Date.valueOf(therapist.getSchedule().getStartDate()));
		in.addValue("END_DATE", java.sql.Date.valueOf(therapist.getSchedule().getStartDate()));
		in.addValue("START_TIME", java.sql.Time.valueOf(therapist.getSchedule().getStartTime()));
		in.addValue("END_TIME", java.sql.Time.valueOf(therapist.getSchedule().getEndTime()));
		int count = namedParameterJdbcTemplate.queryForObject(Constants.SQL_VALIDATE_THERAPIST_AVAILABILITY, in,
				Integer.class);
		if (count > 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<TherapyType> getTherapyTypes() throws TherapistException {
		logger.info("DAO:Inside getTherapyTypes()");
		return namedParameterJdbcTemplate.query(Constants.SQL_THERAPY_TYPES, (rs, rowNum) -> {
			TherapyType type = new TherapyType();
			type.setTherapyId(rs.getInt("VALUE"));
			type.setTherapyName(rs.getString("DISPLAY"));
			return type;
		});
	}

	@Override
	public List<TherapistLevel> getTherapistLevels() throws TherapistException {
		logger.info("DAO:Inside getTherapistLevels()");
		return namedParameterJdbcTemplate.query(Constants.SQL_THERAPIST_LEVELS, (rs, rowNum) -> {
			TherapistLevel type = new TherapistLevel();
			type.setLevelId(rs.getInt("level_id"));
			type.setLevelName(rs.getString("level_name"));
			return type;
		});
	}

	@Override
	public List<CommunicationMode> getCommunicationModes() throws TherapistException {
		return namedParameterJdbcTemplate.query(Constants.SQL_COMMUNICATION_MODES, (rs, rowNum) -> {
			CommunicationMode type = new CommunicationMode();
			type.setValue(rs.getInt("value"));
			type.setDisplay(rs.getString("display"));
			return type;
		});
	}

	@Override
	public Therapist updateTherapistSchedule(Therapist therapist) throws TherapistException {
		logger.info("DAO:therapist-->" + therapist.toString());
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("THERAPIST_ID", therapist.getTherapistId());
		in.addValue("SCHEDULE_ID", therapist.getSchedule().getScheduleId());
		in.addValue("THERAPY_ID", therapist.getTherapyId());
		in.addValue("START_DATE", java.sql.Date.valueOf(therapist.getSchedule().getStartDate()), Types.DATE);
		in.addValue("END_DATE", java.sql.Date.valueOf(therapist.getSchedule().getStartDate()), Types.DATE);
		in.addValue("START_TIME", java.sql.Time.valueOf(therapist.getSchedule().getStartTime()), Types.TIME);
		in.addValue("END_TIME", java.sql.Time.valueOf(therapist.getSchedule().getEndTime()), Types.TIME);
		int count = namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_THERAPIST_SCHEDULE, in);
		if (count == 0) {
			throw new TherapistException("Unable to update  schedule.");
		}
		return therapist;
	}

}
