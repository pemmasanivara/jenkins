package com.llk.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.llk.admin.model.Role;
import com.llk.admin.util.Constants;

public class UserRoleRepositoryCustomImpl implements UserRoleRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Role> getRoles(int partyId) throws Exception {
		
		List<Role> roles=new ArrayList<Role>();
		try {
			StringBuffer sql = new StringBuffer(Constants.SQL_ROLES_Mapping);
			MapSqlParameterSource in = new MapSqlParameterSource();
			roles = null;

			roles = namedParameterJdbcTemplate.query(sql.toString(), in, (rs, rowNum) -> {

				Role role = new Role();
				if (partyId == rs.getInt("PARTY_ID")) {
					role.setId(rs.getInt("ID"));
					role.setRoleName(rs.getString("ROLE_NAME"));
				}
				return role;
			});
		} catch (Exception e) {
			System.out.println("Unable to load roles");
		}

		return getRoles(roles);

	}

	private List<Role> getRoles(List<Role> roles) {
		List<Role> rolesList = new ArrayList<Role>();
		if (roles != null) {
			for (Role role : roles) {
				if (role.getRoleName() != null) {
					rolesList.add(role);
				}
			}
		}
		return rolesList;
	}
}
