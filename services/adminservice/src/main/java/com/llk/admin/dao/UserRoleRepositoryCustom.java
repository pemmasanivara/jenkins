package com.llk.admin.dao;

import java.util.List;

import com.llk.admin.model.Role;

public interface UserRoleRepositoryCustom {

	List<Role> getRoles(int partyId) throws Exception;

}
