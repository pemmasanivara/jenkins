package com.llk.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.llk.admin.model.Role;

@Repository
public interface RoleResponsabilityMappingRepositoryCustom {
	
	public List<Role> getRoles();
}
