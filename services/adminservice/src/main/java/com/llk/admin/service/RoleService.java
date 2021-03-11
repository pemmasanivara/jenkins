package com.llk.admin.service;

import java.util.List;

import com.llk.admin.model.Role;

public interface RoleService {
	List<Role> saveRoles(List<Role> roles);
	List<Role> getRoles();
	void delteRoles(List<Role> roles);
}
