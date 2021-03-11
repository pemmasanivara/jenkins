package com.llk.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.llk.admin.model.Role;
import com.llk.admin.service.RoleService;
import com.llk.admin.util.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class RoleController {

	@Autowired
	RoleService roleService;

	@PostMapping(Constants.URL_ROLE)
	public @ResponseBody List<Role> saveRoles(@RequestBody List<Role> roles) {
		return roleService.saveRoles(roles);
	}

	@GetMapping(Constants.URL_ROLE)
	public @ResponseBody List<Role> getRoles() {
		return roleService.getRoles();
	}

	@DeleteMapping(Constants.URL_ROLE)
	public String delteRoles(@RequestBody List<Role> roles) {
		roleService.delteRoles(roles);
		return "Deleted Successfully.";
	}
}
