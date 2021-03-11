package com.llk.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llk.admin.dao.RoleRepository;
import com.llk.admin.model.Role;

@Service
public class RolesServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<Role> saveRoles(List<Role> roles) {
		return roleRepository.saveAll(roles);
	}

	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public void delteRoles(List<Role> roles) {
		roleRepository.deleteInBatch(roles);
	}

}
