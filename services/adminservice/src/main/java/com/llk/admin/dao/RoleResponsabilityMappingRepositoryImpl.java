package com.llk.admin.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.llk.admin.model.Role;

@Repository
public class RoleResponsabilityMappingRepositoryImpl implements RoleResponsabilityMappingRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}	
}
