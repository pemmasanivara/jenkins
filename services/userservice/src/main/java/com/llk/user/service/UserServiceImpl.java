package com.llk.user.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llk.user.Graph;
import com.llk.user.dao.UserRepository;
import com.llk.user.exception.UserException;
import com.llk.user.model.Party;
import com.llk.user.model.Role;
import com.llk.user.util.AccessTokenGenerator;
import com.llk.user.util.Util;
import com.microsoft.graph.models.extensions.User;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public Party saveParty(Party party) throws UserException {
		return userRepository.save(party);
	}

	@Override
	public Party getProfile(String email) throws UserException {
		Party sparty = userRepository.findByEmail(email);
		logger.info("getPartyId-->" + sparty.getPartyId());
		List<Role> roles = userRepository.getRoles(sparty.getPartyId());
		sparty.setRoles(roles);
		Set<String> uniqRes = Util.getUniqResponsabilities(roles);
		sparty.setResponsabilities(uniqRes);
		return sparty;
	}

	@Override
	public Party getProfile(Party party) throws UserException {
		Party sparty = userRepository.findByEmail(party.getEmail());
		if (sparty == null || sparty.getEmail() == null) {
			return this.saveParty(party);
		} else {
			logger.info("getPartyId-->" + sparty.getPartyId());
			List<Role> roles = userRepository.getRoles(sparty.getPartyId());
			sparty.setRoles(roles);
			Set<String> uniqRes = Util.getUniqResponsabilities(roles);
			sparty.setResponsabilities(uniqRes);
		}
		return sparty;
	}

	@Override
	public boolean isUserExist(String email) throws UserException {
		Party party = userRepository.findByEmail(email);
		if (party == null || party.getEmail() == null) {
			return false;
		}
		return true;
	}

	@Override
	public String getGraphApiAccessToken(String authorizationCode) throws UserException {
		return AccessTokenGenerator.getAccessToken(authorizationCode);
	}

	@Override
	public User getADUser(String accessToken) throws UserException {
		User user = Graph.getUser(accessToken);
		return user;
	}

	@Override
	public Party getParty(String authorizationCode) throws UserException {
		User user = this.getADUser(this.getGraphApiAccessToken(authorizationCode));
		System.out.println(user.getRawObject());
		Party party = null;
		if (user != null) {
			String msUserId = user.id;
			String firstName = user.givenName;
			String lastName = user.surname;
			String userPrincipalName = user.userPrincipalName;
			String mail = user.mail;
			party = new Party();
			party.setFirstName(firstName);
			party.setLastName(lastName);
			party.setAttributeOne(msUserId);
			party.setAttributeTwo(userPrincipalName);
			party.setEmail(mail);
		}
		return party;
	}

}
