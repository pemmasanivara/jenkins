package com.llk.user.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.llk.user.exception.UserException;
import com.llk.user.model.Responsability;
import com.llk.user.model.Role;
import com.llk.user.util.Constants;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryCustomImpl.class);
	@PersistenceContext
	private EntityManager em;	
	@Override
	public List<Role> getRoles(Integer partyId) throws UserException {
		logger.info("getRoles-->"+partyId);
		List<Role> roles = new ArrayList<Role>();
		try {
			Map<Integer, Role> lRoles = new HashMap<Integer, Role>();
			Query query = em.createNativeQuery(Constants.SQL_ROLES_RES);
			query.setParameter(1, partyId);
			@SuppressWarnings("unchecked")
			List<Object[]> result = query.getResultList();
			logger.info("result-->"+result);
			if (result != null) {
				for (Object[] obj : result) {
					if (obj != null) {
						logger.info("obj-->"+obj);
						Integer roleId = (Integer) obj[0];
						String roleName = (String) obj[1];
						Integer resId = (Integer) obj[2];
						String resName = (String) obj[3];
						if (lRoles.get(roleId) == null) {
							Role role = new Role();							
							role.setRoleName(roleName);
							List<Responsability> resList = new ArrayList<Responsability>();
							resList.add(new Responsability(resId,resName));
							role.setResponsabilities(resList);
							lRoles.put(roleId, role);
						} else {
							Role role = lRoles.get(roleId);
							List<Responsability> resList = role.getResponsabilities();
							resList.add(new Responsability(resId,resName));
							role.setResponsabilities(resList);
							lRoles.put(roleId, role);
						}
					}
				}
			}
			logger.info("lRoles-->"+lRoles);
			lRoles.forEach((roleId,role) ->{
				roles.add(role);
			});

		} catch (Exception ex) {
			throw new UserException("Unable to load user roles");
		}
		return roles;
	}

}
