package com.llk.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.llk.admin.dao.MappingRepository;
import com.llk.admin.exception.RoleResponsabilityMappingException;
import com.llk.admin.model.Responsability;
import com.llk.admin.model.Role;
import com.llk.admin.model.RoleResponsabilityMapping;
import com.llk.admin.util.Constants;

@Service
public class RoleResponsabilityMappingServiceImpl implements RoleResponsabilityMappingService {

	@Autowired
	private MappingRepository mappingRepository;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<RoleResponsabilityMapping> saveMappings(List<RoleResponsabilityMapping> mappings) throws RoleResponsabilityMappingException {
		List<RoleResponsabilityMapping> existingmappingList = getExistingMappingList(mappings);
		if (existingmappingList != null) {
			deleteMappings(existingmappingList);
		}
		return mappingRepository.saveAll(mappings);
	}


	@Override
	public List<Role> getMappings(List<RoleResponsabilityMapping> map) throws RoleResponsabilityMappingException {
		return mappingRepository.getRoles();
	}

	@Override
	public void deleteMappings(List<RoleResponsabilityMapping> mappings) throws RoleResponsabilityMappingException{
		mappingRepository.deleteAll(mappings);

	}

	@Override
	public List<RoleResponsabilityMapping> updateMappings(List<RoleResponsabilityMapping> mappings) throws RoleResponsabilityMappingException {
		return mappingRepository.saveAll(mappings);
	}


	private List<RoleResponsabilityMapping> getExistingMappingList(List<RoleResponsabilityMapping> mappings) {
		return mappingRepository.findByRoleId(mappings.get(0).getRoleId());
	}
	

	@Override
	public List<Responsability> getMappingExingRoles(Integer roleID) throws RoleResponsabilityMappingException{
		
		
		List<Responsability> resList=null;
		MapSqlParameterSource in = new MapSqlParameterSource();
		resList = namedParameterJdbcTemplate.query(Constants.SQL_EXISTING_RES, in, (rs,rowNum)->{
			
			Responsability res=new Responsability();
			if(roleID==rs.getInt("ROLE_ID")) {
			res.setId(rs.getInt("RES_ID"));
			res.setResName(rs.getString("RES_NAME"));
			}
			return res;
		});
		
		return getResList(resList);
	}

	private List<Responsability> getResList(List<Responsability> res) {
		List<Responsability> resList=new  ArrayList<Responsability>();
		if(res !=null) {
			for(Responsability responsabilities:res) {
				if(responsabilities.getResName() !=null) {
					resList.add(responsabilities);
				}
			}
		}
		return resList;
	}

}
