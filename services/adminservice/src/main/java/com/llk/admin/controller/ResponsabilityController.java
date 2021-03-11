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

import com.llk.admin.model.Responsability;
import com.llk.admin.service.ResponsabilityService;
import com.llk.admin.util.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class ResponsabilityController {
	@Autowired
	ResponsabilityService responsabilityService;

	@PostMapping(Constants.URL_RESPONSABILITY)
	public @ResponseBody List<Responsability> saveRoles(@RequestBody List<Responsability> responsabilities) {
		return responsabilityService.saveResponsabilities(responsabilities);
	}

	@GetMapping(Constants.URL_RESPONSABILITY)
	public @ResponseBody List<Responsability> getRoles() {
		return responsabilityService.getResponsabilities();
	}

	@DeleteMapping(Constants.URL_RESPONSABILITY)
	public String delteRoles(@RequestBody List<Responsability> responsabilities) {
		responsabilityService.delteResponsabilities(responsabilities);
		return "Deleted Successfully.";
	}
}
