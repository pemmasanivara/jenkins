package com.llk.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.llk.admin.model.PartyRoleMapping;

public interface UserRoleMappingRepository extends JpaRepository<PartyRoleMapping, Long>,UserRoleRepositoryCustom{

	List<PartyRoleMapping> findByPartyId(int partyId);

}
 