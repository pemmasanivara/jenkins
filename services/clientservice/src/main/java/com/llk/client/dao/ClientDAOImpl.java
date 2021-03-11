package com.llk.client.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.llk.client.exception.ClientException;
import com.llk.client.model.Client;
import com.llk.client.model.Clients;
import com.llk.client.model.CommunicationMode;
import com.llk.client.model.RequestParams;
import com.llk.client.model.TherapyType;
import com.llk.client.util.Constants;
import com.llk.common.model.Address;
import com.llk.common.model.Lov;

@Repository
public class ClientDAOImpl implements ClientDAO {

	private static final Logger logger = LoggerFactory.getLogger(ClientDAOImpl.class);

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Client createParty(Client client) throws ClientException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("FIRST_NAME", client.getFirstName());
		in.addValue("LAST_NAME", client.getLastName());
		in.addValue("EMAIL", client.getEmail());
		in.addValue("PHONE", client.getPhone());
		in.addValue("GENDER", client.getGender());
		in.addValue("DOB", client.getDob());
		in.addValue("PROFILE_IMG_URL", client.getAvatarUrl());
		namedParameterJdbcTemplate.update(Constants.SQL_SAVE_PARTY, in, keyHolder);
		if (keyHolder != null) {
			int partyId = keyHolder.getKey().intValue();
			client.setPartyId(partyId);
		}
		return client;
	}

	@Override
	public Client createClient(Client client) throws ClientException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource in = new MapSqlParameterSource();
		List<Lov> cModes = client.getCommunicationMode();
		String cMode1 = null;
		String cMode2 = null;
		if (cModes != null && cModes.size() > 0) {
			cMode1 = cModes.get(0).getDisplay();
			if (cModes.size() > 1) {
				cMode2 = cModes.get(1).getDisplay();
			}
		}
		in.addValue("PARTY_ID", client.getPartyId());
		in.addValue("COMMUNICATION_MODE", cMode1);
		in.addValue("ATTRIBUTE1", cMode2);
		in.addValue("GUARDIAN_NAME", client.getGuardianName());
		in.addValue("GUARDIAN_RELATION", client.getGuardianRelation());
		in.addValue("CLASSES", client.getInterestedClasses());
		in.addValue("INSURANCE_DETAILS", client.getInsuranceDetails());
		in.addValue("SEND_NOTIFICATION", client.getSendNotification());
		in.addValue("STATUS", "ACTIVE");
		in.addValue("ATTRIBUTE2", client.getAddress());
		namedParameterJdbcTemplate.update(Constants.SQL_SAVE_CLIENT, in, keyHolder);
		if (keyHolder != null) {
			int clientId = keyHolder.getKey().intValue();
			client.setClientId(clientId);
			client.getSchedule().setClientId(clientId);
		}
		return client;
	}

	@Override
	public Client createAvailability(Client client) throws ClientException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("CLIENT_ID", client.getClientId());
		in.addValue("THERAPIST_ID", client.getSchedule().getTherapistId());
		in.addValue("THERAPY_ID", client.getSchedule().getTherapyId());
		in.addValue("START_DATE", java.sql.Date.valueOf(client.getSchedule().getStartDate()));
		in.addValue("END_DATE", java.sql.Date.valueOf(client.getSchedule().getEndDate()));
		in.addValue("START_TIME", java.sql.Time.valueOf(client.getSchedule().getStartTime()));
		in.addValue("END_TIME", java.sql.Time.valueOf(client.getSchedule().getEndTime()));
		in.addValue("STATUS", "ACTIVE");
		namedParameterJdbcTemplate.update(Constants.SQL_SAVE_CLIENT_AVAILABILITY, in, keyHolder);
		if (keyHolder != null) {
			int availabilityId = keyHolder.getKey().intValue();
			client.getSchedule().setAvailabilityId(availabilityId);
			client.getSchedule().getScheduleOccurence().setAvailabilityId(availabilityId);
		}
		return client;
	}

	@Override
	public Client createAvailabilityOccurence(Client client) throws ClientException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("AVAILABILITY_ID", client.getSchedule().getScheduleOccurence().getAvailabilityId());
		in.addValue("FREQUENCE_TYPE", client.getSchedule().getScheduleOccurence().getFrequencyType());
		in.addValue("FREQUENCY_REPEATS", client.getSchedule().getScheduleOccurence().getFrequencyRepeats());
		in.addValue("WEEK_DAYAS", client.getSchedule().getScheduleOccurence().getWeekDays());
		in.addValue("END_DATE", java.sql.Date.valueOf(client.getSchedule().getScheduleOccurence().getEndDate()));
		in.addValue("DAY_NO", client.getSchedule().getStartDate().getDayOfYear());
		in.addValue("WEEK_OF_MONTH", client.getSchedule().getStartDate().getDayOfWeek().getValue());
		in.addValue("MONTH_OF_YEAR", client.getSchedule().getStartDate().getMonth().getValue());
		in.addValue("STATUS", "ACTIVE");
		namedParameterJdbcTemplate.update(Constants.SQL_SAVE_CLIENT_AVAILABILITY_OCCURENCE, in, keyHolder);
		if (keyHolder != null) {
			int occurenceId = keyHolder.getKey().intValue();
			client.getSchedule().getScheduleOccurence().setOccurenceId(occurenceId);
		}
		return client;
	}

	@Override
	public Clients searchClients(RequestParams params) throws ClientException {
		logger.info("request params-->" + params);
		Clients clients = new Clients();
		MapSqlParameterSource in = new MapSqlParameterSource();
		StringBuffer sql = new StringBuffer(Constants.SQL_SEARCH_CLIENT);
		if (params.getSrchTxt() != null && !params.getSrchTxt().isEmpty()) {
			sql.append(
					"\n and ((first_name like :srchTxt or last_name like :srchTxt) || (email like :srchTxt) || (therapy_name like :srchTxt) || (phone like:srchTxt))");
			in.addValue("srchTxt", "%" + params.getSrchTxt() + "%");
		}

		logger.info("search sql->" + sql.toString());

		String countQuery = "select count(*) as count from(" + sql.toString() + ") as main_query";

		int totalCount = namedParameterJdbcTemplate.queryForObject(countQuery, in, Integer.class);

		clients.setTotalCount(totalCount);
		if (totalCount > 0) {
			if (params.getLimit() == 0) {
				params.setLimit(10);
			}
			if (params.getLimit() >= 0) {
				sql.append("\nLIMIT :limit ");
				in.addValue("limit", params.getLimit());
			}
			if (params.getStart() >= 0) {
				sql.append("\nOFFSET :start");
				in.addValue("start", params.getStart());
			}
			List<Client> listClients = null;
			listClients = namedParameterJdbcTemplate.query(sql.toString(), in, (rs, rowNum) -> {
				Client client = new Client();
				client.setClientId(rs.getInt("CLIENT_ID"));
				client.setFirstName(rs.getString("FIRST_NAME"));
				client.setLastName(rs.getString("LAST_NAME"));
				client.setEmail(rs.getString("EMAIL"));
				client.setPhone(rs.getString("PHONE"));
				client.setGuardianName(rs.getString("GUARDIAN_NAME"));
				client.setGuardianRelation(rs.getString("GUARDIAN_RELATION"));
				client.setAvatarUrl(rs.getString("profile_img_url"));
				return client;
			});

			clients.setClients(listClients);
		}

		return clients;
	}

	@Override
	public boolean deleteClient(Integer clientId) throws ClientException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("CLIENT_ID", clientId);
		int count = namedParameterJdbcTemplate.update(Constants.SQL_DELETE_CLIENT, in);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Client getClientDetails(Integer clientId) throws ClientException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("CLIENT_ID", clientId);
		Client c = namedParameterJdbcTemplate.query(Constants.SQL_CLIENT_DETAILS, in, (rs) -> {
			rs.first();
			Client client = new Client();
			client.setClientId(rs.getInt("CLIENT_ID"));
			client.setFirstName(rs.getString("FIRST_NAME"));
			client.setLastName(rs.getString("LAST_NAME"));
			client.setEmail(rs.getString("EMAIL"));
			client.setPhone(rs.getString("PHONE"));
			client.setDob(rs.getDate("DOB").toLocalDate());
			client.setGender(rs.getString("GENDER"));
			client.setAvatarUrl(rs.getString("PROFILE_IMG_URL"));
			client.setGuardianName(rs.getString("GUARDIAN_NAME"));
			client.setGuardianRelation(rs.getString("GUARDIAN_RELATION"));
			client.setInsuranceDetails(rs.getString("INSURANCE_DETAILS"));
			client.setInterestedClasses(rs.getString("classes"));
			client.setAddress(rs.getString("ATTRIBUTE2"));
			String cMode1 = rs.getString("COMMUNICATION_MODE");
			String cMode2 = rs.getString("ATTRIBUTE1");
			List<Lov> cModes = new ArrayList<Lov>();
			if (cMode1 != null) {
				Lov l = new Lov();
				l.setValue("1");
				l.setDisplay(cMode1);
				cModes.add(l);
			}
			if (cMode2 != null) {
				Lov l = new Lov();
				l.setValue("2");
				l.setDisplay(cMode2);
				cModes.add(l);
			}
			client.setCommunicationMode(cModes);
			return client;
		});

		List<Address> addresses = this.getAddresses(clientId);
		c.setAddresses(addresses);
		return c;
	}

	@Override
	public List<Client> getAllClients() throws ClientException {
		return namedParameterJdbcTemplate.query(Constants.SQL_CLIENTS_ALL, (rs, rowNum) -> {
			Client client = new Client();
			client.setName(rs.getString("NAME"));
			client.setClientId(rs.getInt("CLIENT_ID"));
			return client;
		});
	}

	@Override
	public Client updateParty(Client client) throws ClientException {
		Integer partyId = getPartyId(client.getClientId());
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("FIRST_NAME", client.getFirstName());
		in.addValue("LAST_NAME", client.getLastName());
		in.addValue("PHONE", client.getPhone());
		in.addValue("EMAIL", client.getEmail());
		in.addValue("GENDER", client.getGender());
		in.addValue("DOB", java.sql.Date.valueOf(client.getDob()));
		in.addValue("PARTY_ID", partyId);
		namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_PARTY, in);
		return client;
	}

	@Override
	public Client updateClient(Client client) throws ClientException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		List<Lov> cModes = client.getCommunicationMode();
		String cMode1 = null;
		String cMode2 = null;
		if (cModes != null && cModes.size() > 0) {
			cMode1 = cModes.get(0).getDisplay();
			if (cModes.size() > 1) {
				cMode2 = cModes.get(1).getDisplay();
			}
		}
		in.addValue("COMMUNICATION_MODE", cMode1);
		in.addValue("ATTRIBUTE1", cMode2);
		in.addValue("GUARDIAN_NAME", client.getGuardianName());
		in.addValue("GUARDIAN_RELATION", client.getGuardianRelation());
		in.addValue("CLASSES", client.getInterestedClasses());
		in.addValue("INSURANCE_DETAILS", client.getInsuranceDetails());
		in.addValue("CLIENT_ID", client.getClientId());
		in.addValue("ATTRIBUTE2", client.getAddress());
		namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_CLIENT, in);
		return client;
	}

	private Integer getPartyId(Integer client_id) throws ClientException {
		return namedParameterJdbcTemplate.queryForObject(Constants.SQL_PARTY_ID,
				new MapSqlParameterSource().addValue("CLIENT_ID", client_id), Integer.class);
	}

	@Override
	public List<TherapyType> getTherapyTypes() throws ClientException {
		return namedParameterJdbcTemplate.query(Constants.SQL_THERAPY_TYPES, (rs, rowNum) -> {
			TherapyType type = new TherapyType();
			type.setTherapyId(rs.getInt("VALUE"));
			type.setTherapyName(rs.getString("DISPLAY"));
			return type;
		});
	}

	@Override
	public List<CommunicationMode> getCommunicationModes() throws ClientException {
		return namedParameterJdbcTemplate.query(Constants.SQL_COMMUNICATION_MODES, (rs, rowNum) -> {
			CommunicationMode type = new CommunicationMode();
			type.setValue(rs.getInt("value"));
			type.setDisplay(rs.getString("display"));
			return type;
		});
	}

	@Override
	public boolean unsubscribe(Integer clientId) throws ClientException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("CLIENT_ID", clientId);
		int count = namedParameterJdbcTemplate.update(Constants.SQL_CLIENT_COMM_UNSUBSCRIBE, in);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Client saveAddresses(Client client) throws ClientException {
		int clientId = client.getClientId();
		int partyId = getPartyId(clientId);
		for (Address address : client.getAddresses()) {
			if(address!=null) {
			  int siteId = insertAddress(partyId, address);
			  address.setSiteId(siteId);
			}
		}
		return client;
	}

	/**
	 * 
	 * @param address
	 * @return
	 * @throws ClientException
	 */
	private int insertAddress(int partyId, Address address) throws ClientException {
		logger.info("address-->"+address.toString());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int siteId = 0;
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("PARTY_ID", partyId);
		in.addValue("ADDRESS1", address.getAddressOne());
		in.addValue("ADDRESS2", address.getAddressTwo());
		in.addValue("ADDRESS3", null);
		in.addValue("CITY", address.getCity());
		in.addValue("STATE", address.getState());
		in.addValue("COUNTRY", address.getCountry());
		in.addValue("ZIP", address.getZip());
		in.addValue("USE_TYPE", address.getType());
		in.addValue("CELL_PHONE", address.getCellPhone());
		in.addValue("HOME_PHONE", address.getHomePhone());
		in.addValue("WORK_PHONE", address.getWorkPhone());
		in.addValue("ATTRIBUTE1", address.getWorkPhoneExt());
		namedParameterJdbcTemplate.update(Constants.SQL_SAVE_ADDRESS, in, keyHolder);
		if (keyHolder != null) {
			siteId = keyHolder.getKey().intValue();
		}
		return siteId;
	}

	@Override
	public List<Address> getAddresses(int clientId) throws ClientException {
		Integer partyId = getPartyId(clientId);
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("PARTY_ID", partyId);
		return namedParameterJdbcTemplate.query(Constants.SQL_GET_ADDRESS, in, (rs, rowNum) -> {
			Address address = new Address();
			address.setSiteId(rs.getInt("SITE_ID"));
			address.setAddressOne(rs.getString("ADDRESS1"));
			address.setAddressTwo(rs.getString("ADDRESS2"));
			address.setCity(rs.getString("CITY"));
			address.setState(rs.getString("STATE"));
			address.setCountry(rs.getString("COUNTRY"));			
			address.setZip(rs.getString("ZIP"));
			address.setType(rs.getString("USE_TYPE"));
			address.setCellPhone(rs.getString("CELL_PHONE"));
			address.setHomePhone(rs.getString("HOME_PHONE"));
			address.setWorkPhone(rs.getString("WORK_PHONE"));
			address.setWorkPhoneExt(rs.getString("ATTRIBUTE1"));
			return address;
		});

	}

	@Override
	public Client updateAddress(Client client) throws ClientException {
		List<Address> addresses = client.getAddresses();
		for (Address address : addresses) {
			updateAddress(address);
		}
		return client;
	}

	private void updateAddress(Address address) throws ClientException {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("SITE_ID", address.getSiteId());
		in.addValue("ADDRESS1", address.getAddressOne());
		in.addValue("ADDRESS2", address.getAddressTwo());
		in.addValue("ADDRESS3", null);
		in.addValue("CITY", address.getCity());
		in.addValue("STATE", address.getState());
		in.addValue("COUNTRY", address.getCountry());
		in.addValue("ZIP", address.getZip());
		in.addValue("USE_TYPE", address.getType());
		in.addValue("CELL_PHONE", address.getCellPhone());
		in.addValue("HOME_PHONE", address.getHomePhone());
		in.addValue("WORK_PHONE", address.getWorkPhone());
		in.addValue("ATTRIBUTE1", address.getWorkPhoneExt());
		namedParameterJdbcTemplate.update(Constants.SQL_UPDATE_ADDRESS, in);
	}

}
