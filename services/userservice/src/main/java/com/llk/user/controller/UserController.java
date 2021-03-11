package com.llk.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.llk.user.exception.UserException;
import com.llk.user.model.Party;
import com.llk.user.service.UserService;
import com.llk.user.util.Constants;
import com.llk.user.util.Util;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@PostMapping(Constants.URL_USER)
	public @ResponseBody Party savePart(@RequestBody Party party) throws UserException {
		logger.info("party-->" + party);
		return userService.saveParty(party);
	}

	@GetMapping(Constants.URL_PROFILE)
	public @ResponseBody Party getProfile(HttpServletRequest request) throws UserException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		authHeader = authHeader.replace(Constants.TOKEN_TYPE, "");
		System.out.println(authHeader);
		String email = Util.getEmail(authHeader);
		if (userService.isUserExist(email)) {
			return userService.getProfile(email);
		} else {
			Party party = userService.getParty(authHeader);
			// Party party=Util.getParty(authHeader);
			logger.info("party-->" + party);
			return userService.getProfile(party);
		}

	}
}
