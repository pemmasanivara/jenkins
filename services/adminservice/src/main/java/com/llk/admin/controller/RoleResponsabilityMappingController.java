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

import com.llk.admin.exception.RoleResponsabilityMappingException;
import com.llk.admin.model.Responsability;
import com.llk.admin.model.Role;
import com.llk.admin.model.RoleResponsabilityMapping;
import com.llk.admin.service.RoleResponsabilityMappingService;
import com.llk.admin.util.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class RoleResponsabilityMappingController {
	private static final Logger logger = LoggerFactory.getLogger(RoleResponsabilityMappingController.class);
	@Autowired
	RoleResponsabilityMappingService roleResponsabilityMappingService;

	@PostMapping(Constants.URL_ROLE_RESPONSABILITY_MAPPING)
	public @ResponseBody List<RoleResponsabilityMapping> saveMappings(
			@RequestBody List<RoleResponsabilityMapping> mappings)  throws RoleResponsabilityMappingException{
		logger.info("mappings-->"+mappings);
		return roleResponsabilityMappingService.saveMappings(mappings);
	}

	@GetMapping(Constants.URL_ROLE_RESPONSABILITY_MAPPING)
	public @ResponseBody List<Role> getMappings(
			@PathVariable(name = "Id", required = false) List<RoleResponsabilityMapping> map) throws RoleResponsabilityMappingException{
		logger.info("mappings-->"+map);
		return roleResponsabilityMappingService.getMappings(map);
	}

	@PutMapping(Constants.URL_ROLE_RESPONSABILITY_MAPPING)
	public @ResponseBody List<RoleResponsabilityMapping> updateMappings(
			@RequestBody List<RoleResponsabilityMapping> mappings) throws RoleResponsabilityMappingException{
		logger.info("mappings-->"+mappings);
		return roleResponsabilityMappingService.updateMappings(mappings);
	}

	@DeleteMapping(Constants.URL_ROLE_RESPONSABILITY_MAPPING)
	public @ResponseBody String deleteMappings(@RequestBody List<RoleResponsabilityMapping> mappings) throws RoleResponsabilityMappingException{
		logger.info("mappings-->"+mappings);
		roleResponsabilityMappingService.deleteMappings(mappings);
		return "Deleted successfully..";
	}


	@GetMapping(Constants.URL_ROLE_RESPONSABILITY_EXISTING)
	public @ResponseBody List<Responsability> getExistingMappings(@PathVariable("roleId") Integer roleId) throws RoleResponsabilityMappingException{
		logger.info("roleId-->"+roleId);
	return	roleResponsabilityMappingService.getMappingExingRoles(roleId);
	}
}
