package com.llk.admin.service;

import java.util.List;

import com.llk.admin.exception.UserException;
import com.llk.admin.model.Party;
import com.llk.admin.model.PartyRoleMapping;
import com.llk.admin.model.RequestParams;
import com.llk.admin.model.Role;
import com.llk.admin.model.Users;

public interface UserService {
	List<Party> getUsers() throws UserException;
	List<Party> updateUsers(List<Party> users) throws UserException;
	Party createUser(Party party) throws UserException;
	List<PartyRoleMapping> saveUserRoles(List<PartyRoleMapping> roles) throws UserException;
	Users searchUser(RequestParams params) throws UserException, Exception;
	void deleteMappings(List<PartyRoleMapping> roles) throws UserException;
	List<Role> getMappingExistngRoles(Integer partyId) throws Exception;
}
