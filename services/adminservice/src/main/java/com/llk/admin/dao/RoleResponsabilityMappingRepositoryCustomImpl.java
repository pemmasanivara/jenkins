package com.llk.admin.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.llk.admin.model.Responsability;
import com.llk.admin.model.Role;
import com.llk.admin.util.Constants;



public class RoleResponsabilityMappingRepositoryCustomImpl implements RoleResponsabilityMappingRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Role> getRoles() {
		
		List<Role> roles = new ArrayList<Role>();
		try {
			Map<Integer, Role> lRoles = new HashMap<Integer, Role>();
			Query query = em.createNativeQuery(Constants.SQL_ROLES_RES);
			@SuppressWarnings("unchecked")
			List<Object[]> result = query.getResultList();
			if (result != null) {
				for (Object[] obj : result) {
					if (obj != null) {
						Integer mapId = (Integer) obj[0];
						Integer roleId = (Integer) obj[1];
						String roleName = (String) obj[2];
						Integer resId = (Integer) obj[3];
						String resName = (String) obj[4];
						if (lRoles.get(roleId) == null) {
							Role role = new Role();			
							role.setId(roleId);
							role.setRoleName(roleName);
							List<Responsability> resList = new ArrayList<Responsability>();
							resList.add(new Responsability(resId, resName, mapId));
							role.setResponsabilities(resList);
							lRoles.put(roleId, role);
						} else {
							Role role = lRoles.get(roleId);
							List<Responsability> resList = role.getResponsabilities();
							resList.add(new Responsability(resId, resName, mapId));
							role.setResponsabilities(resList);
							lRoles.put(roleId, role);
						}
					}
				}
			}
			lRoles.forEach((roleId,role) ->{
				roles.add(role);
			});

		} catch (Exception ex) {
		}
		return roles;
	}

}
