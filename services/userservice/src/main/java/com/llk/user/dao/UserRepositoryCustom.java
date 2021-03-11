package com.llk.user.dao;

import java.util.List;

import com.llk.user.exception.UserException;
import com.llk.user.model.Role;

public interface UserRepositoryCustom {
 public List<Role> getRoles(Integer partyId) throws UserException;
}
