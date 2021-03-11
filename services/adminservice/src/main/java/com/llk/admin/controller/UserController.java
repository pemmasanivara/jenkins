package com.llk.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.llk.admin.exception.UserException;
import com.llk.admin.model.Party;
import com.llk.admin.model.PartyRoleMapping;
import com.llk.admin.model.RequestParams;
import com.llk.admin.model.Role;
import com.llk.admin.model.Users;
import com.llk.admin.service.UserService;
import com.llk.admin.util.Constants;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@PostMapping(Constants.URL_USER)
	public @ResponseBody Party saveUser(@RequestBody Party party) throws UserException{
		logger.info("party-->"+party);
		return userService.createUser(party);
	}

	@GetMapping(Constants.URL_USERS)
	public @ResponseBody List<Party> getUsers() throws UserException {
		return userService.getUsers();
	}
	
	@PostMapping(Constants.URL_USER_ROLE_MAPPING)
	public @ResponseBody List<PartyRoleMapping> saveUserRoles(@RequestBody List<PartyRoleMapping> roles) throws UserException{
		logger.info("roles-->"+roles);
		return userService.saveUserRoles(roles);
	}
	
	@PutMapping(Constants.URL_USER)
	public @ResponseBody List<Party> updateUsers(@RequestBody List<Party> users) throws UserException{
		logger.info("users-->"+users);
		return userService.updateUsers(users);
	}
	
	@GetMapping(Constants.URL_USER_ROLE)
	public @ResponseBody Users searchUser(RequestParams params ) throws Exception{
		logger.info("params-->"+params);
		return userService.searchUser(params);
	}
	

	@DeleteMapping(Constants.URL_USER_ROLE)
	public @ResponseBody String deleteMappings(@RequestBody List<PartyRoleMapping> roles) throws UserException{
		logger.info("roles-->"+roles);
		userService.deleteMappings(roles);
		return "Deleted successfully..";
	}
	
	@GetMapping(Constants.URL_USER_ROLE_EXISTING)
	public @ResponseBody List<Role> getExistingMappings(@PathVariable("partyId") Integer partyId)  throws Exception{
		logger.info("partyId-->"+partyId);
	return	userService.getMappingExistngRoles(partyId);
	}
}
