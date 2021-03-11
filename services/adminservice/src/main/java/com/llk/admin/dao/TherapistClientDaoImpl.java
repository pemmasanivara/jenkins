package com.llk.admin.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.llk.admin.model.Client;
import com.llk.admin.model.Therapist;
import com.llk.admin.util.Constants;


@Repository
public class TherapistClientDaoImpl implements TherapistClientDAO {
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Therapist> listTherapists() {
		return namedParameterJdbcTemplate.query(Constants.SQL_THERAPISTS_ALL, (rs, rowNum) -> {
			Therapist therapist = new Therapist();
			therapist.setStatus(rs.getString("STATUS") );
			therapist.setTherapistId(rs.getInt("THERAPIST_ID"));
			therapist.setFirstName(rs.getString("FIRST_NAME"));
			therapist.setLastName(rs.getString("LAST_NAME"));;
			return therapist;
		});
	}

	@Override
	public Therapist updateTherapist(Therapist therapist) {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("STATUS", therapist.getStatus());
		in.addValue("THERAPIST_ID", therapist.getTherapistId());
		namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_THERAPIST, in);
		return therapist;
	}

	@Override
	public List<Client> listClients() {
		return namedParameterJdbcTemplate.query(Constants.SQL_CLIENTS_ALL, (rs, rowNum) -> {
			Client client = new Client();
			client.setStatus(rs.getString("STATUS") );
			client.setClientID(rs.getInt("CLIENT_ID"));;
			client.setFirstName(rs.getString("FIRST_NAME"));
			client.setLastName(rs.getString("LAST_NAME"));;
			return client;
		});
	}

	@Override
	public Client updateClient(Client client) {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("STATUS", client.getStatus());
		in.addValue("THERAPIST_ID", client.getClientID());
		namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_CLIENT, in);
		return client;
	}

}
