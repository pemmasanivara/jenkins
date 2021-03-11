package com.llk.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.llk.admin.dao.PartyRepository;
import com.llk.admin.dao.UserRoleMappingRepository;
import com.llk.admin.exception.UserException;
import com.llk.admin.model.Party;
import com.llk.admin.model.PartyRoleMapping;
import com.llk.admin.model.RequestParams;
import com.llk.admin.model.Role;
import com.llk.admin.model.Users;
import com.llk.admin.util.Constants;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private UserRoleMappingRepository userRoleMappingRepository;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	@Override
	public List<Party> getUsers() throws UserException {
		return partyRepository.findAll();
	}

	@Override
	public List<Party> updateUsers(List<Party> users) throws UserException {
		return partyRepository.saveAll(users);
	}

	@Override
	public Party createUser(Party party) throws UserException {
		return partyRepository.save(party);
	}

	@Override
	public List<PartyRoleMapping> saveUserRoles(List<PartyRoleMapping> roles) throws UserException {
		List<PartyRoleMapping> existingmappingList = getExistingMappingList(roles);
		if (existingmappingList != null) {
			deleteMappings(existingmappingList);
		}
		return userRoleMappingRepository.saveAll(roles);
	}

	private List<PartyRoleMapping> getExistingMappingList(List<PartyRoleMapping> mappings) throws UserException{
		return userRoleMappingRepository.findByPartyId(mappings.get(0).getPartyId());
	}

	@Override
	public void deleteMappings(List<PartyRoleMapping> roles) throws UserException{
		logger.info("Service:Inside deleteMappings()");
		logger.info("Service:therapistId-->" + roles);
		userRoleMappingRepository.deleteAll(roles);
	}

	@Override
	public Users searchUser(RequestParams params) throws Exception{
		logger.info("Service:Inside searchUser()");
		logger.info(params.toString());
		Users userList= new Users();
		try {
			List<Party> partyRoleList = null;
			StringBuffer sql = new StringBuffer(Constants.SQL_SEARCH_USER);
			MapSqlParameterSource in = new MapSqlParameterSource();
			if (params.getSrchTxt() != null && !params.getSrchTxt().isEmpty()) {
				sql.append(
						"\n and ((first_name like :srchTxt or last_name like :srchTxt) || (email like :srchTxt) || (role_name like :srchTxt) )");
				in.addValue("srchTxt", "%" + params.getSrchTxt() + "%");
			}
			if (params.getPartyId() != null && !params.getPartyId().isEmpty()) {
				sql.append("\n and party_id=:party_id");
				in.addValue("party_id", params.getPartyId());
			}
			
			String countQuery = "select count(*) as count from(" + sql.toString() + ") as main_query";

			int totalCount = namedParameterJdbcTemplate.queryForObject(countQuery, in, Integer.class);
			
			logger.info("totalCount-->" + totalCount);
			
			userList.setTotalCount(totalCount);
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
				
			partyRoleList = namedParameterJdbcTemplate.query(sql.toString(), in, (rs, rowNum) -> {
					
					Party prm=new Party();
					prm.setFirstName(rs.getString("FIRST_NAME"));
					prm.setLastName(rs.getString("LAST_NAME"));
					prm.setEmail(rs.getString("EMAIL"));
					prm.setPartyId(rs.getInt("PARTY_ID"));
					
					return prm;
				});
			}
			
			List<Party> ff=new ArrayList<Party>();
			  for(Party party:partyRoleList) {
				  List<Role> result=userRoleMappingRepository.getRoles(party.getPartyId());
				  party.setRoleList(result);
				  ff.add(party);
			  }
			  userList.setUserRoles(ff);
		} catch (Exception e) {
			System.out.println("unable to search user");
		}
	      
	     return userList;
	}

	@Override
	public List<Role> getMappingExistngRoles(Integer partyId)  throws Exception{
		MapSqlParameterSource in = new MapSqlParameterSource();
		List<Role> roles=null;
		roles = namedParameterJdbcTemplate.query(Constants.SQL_ROLES_Mapping, in, (rs, rowNum) -> {
			
			Role role=new Role();
			if(partyId==rs.getInt("PARTY_ID")) {
				role.setId(rs.getInt("ROLE_ID"));
				role.setRoleName(rs.getString("ROLE_NAME"));
			}
			return role;
		});
		return getRoles(roles);
	}

	private List<Role> getRoles(List<Role> roles) {
		List<Role> rolesList=new  ArrayList<Role>();
		if(roles !=null) {
			for(Role role:roles) {
				if(role.getRoleName() !=null) {
					rolesList.add(role);
				}
			}
		}
		return rolesList;
	}
}
