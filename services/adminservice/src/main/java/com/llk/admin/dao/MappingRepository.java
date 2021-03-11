package com.llk.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.llk.admin.model.Role;
import com.llk.admin.model.RoleResponsabilityMapping;

public interface MappingRepository extends JpaRepository<RoleResponsabilityMapping, Long>,RoleResponsabilityMappingRepositoryCustom {

	List<RoleResponsabilityMapping> findAllByRoleId(Role roleID);

	List<RoleResponsabilityMapping> findByRoleId(int id);

//	List<Role> findByRoleName(String roleName);

}