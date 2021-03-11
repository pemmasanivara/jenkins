package com.llk.admin.service;

import java.util.List;

import com.llk.admin.exception.RoleResponsabilityMappingException;
import com.llk.admin.model.Responsability;
import com.llk.admin.model.Role;
import com.llk.admin.model.RoleResponsabilityMapping;

public interface RoleResponsabilityMappingService {
	List<RoleResponsabilityMapping> saveMappings(List<RoleResponsabilityMapping> mappings) throws RoleResponsabilityMappingException;
	List<Role> getMappings(List<RoleResponsabilityMapping> map) throws RoleResponsabilityMappingException;
	void deleteMappings(List<RoleResponsabilityMapping> mappings) throws RoleResponsabilityMappingException;
	List<RoleResponsabilityMapping> updateMappings(List<RoleResponsabilityMapping> mappings) throws RoleResponsabilityMappingException;
	List<Responsability> getMappingExingRoles(Integer roleId) throws RoleResponsabilityMappingException;
	
}
