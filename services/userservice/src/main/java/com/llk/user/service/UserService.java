package com.llk.user.service;

import com.llk.user.exception.UserException;
import com.llk.user.model.Party;
import com.microsoft.graph.models.extensions.User;

public interface UserService {
	Party saveParty(Party party) throws UserException;
	
	Party getProfile(Party party) throws UserException;
	
	Party getProfile(String email) throws UserException;
	
	boolean isUserExist(String email) throws UserException;
	
	String getGraphApiAccessToken(String authorizationCode) throws UserException;
	
	User getADUser(String accessToken) throws UserException;
	
	Party getParty(String authorizationCode) throws UserException;
}
