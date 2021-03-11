package com.llk.notification.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.llk.common.model.Lov;
import com.llk.common.model.Message;
import com.llk.notification.util.Constants;

@Repository
public class ClientDAOImpl implements ClientDAO {
	private static final Logger logger = LoggerFactory.getLogger(ClientDAOImpl.class);
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Message getClientDetails(Integer clientId) {
		logger.info("clientId-->" + clientId);
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("CLIENT_ID", clientId);
		return namedParameterJdbcTemplate.query(Constants.SQL_CLIENT, in, rs -> {
			rs.first();
			Message message = new Message();
			message.setClientName(rs.getString("NAME"));
			message.setClientEmail(rs.getString("EMAIL"));
			message.setClientPhone(rs.getString("PHONE"));
			message.setClientId(rs.getInt("CLIENT_ID"));
			String cMode = rs.getString("COMMUNICATION_MODE");
			String cMode1 = rs.getString("ATTRIBUTE1");
			List<Lov> cModes = new ArrayList<Lov>();
			if (cMode != null) {
				Lov l = new Lov();
				l.setValue("1");
				l.setDisplay(cMode);
				cModes.add(l);
			}
			if (cMode1 != null) {
				Lov l = new Lov();
				l.setValue("2");
				l.setDisplay(cMode1);
				cModes.add(l);
			}
			message.setCommunicationMode(cModes);
			return message;
		});
	}

	@Override
	public void updateOutLookEventSendStatus(Integer clientId, Integer theraistId, Integer therapyId) {
		try {
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("CLIENT_ID", clientId);
			in.addValue("THERAPIST_ID", theraistId);
			in.addValue("THERAPY_ID", therapyId);
			namedParameterJdbcTemplate.update(Constants.CLIENT_OUTLOOK_EVENT_UPDATE, in);
		} catch (Exception ex) {
			logger.error("updateOutLookEventSendStatus-->", ex);
		}
	}

	@Override
	public void updateOutLookEventIds(Map<Long, String> eventIds) {	
		logger.error("############## updateOutLookEventIds #################");
		try {
		Iterator<Long> itr=eventIds.keySet().iterator();
		@SuppressWarnings("unchecked")
		Map<String,?>[] myDataArray = new HashMap[eventIds.size()];
		int i=0;
		while(itr.hasNext()) {
			HashMap<String,Object> in = new HashMap<>();
			Long key=itr.next();
			in.put("ID", eventIds.get(key));
			in.put("CLIENT_SCHEDULE_ID", key);			
			myDataArray[i]=in;
			i++;
		}		
		namedParameterJdbcTemplate.batchUpdate(Constants.UPDATE_OUTLLOK_EVENT_ID, myDataArray);
		}catch(Exception ex) {
			logger.error("updateOutLookEventIds-->", ex);
		}
		
	}

}
