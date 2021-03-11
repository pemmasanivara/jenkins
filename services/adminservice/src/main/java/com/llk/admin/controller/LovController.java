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

import com.llk.admin.exception.UserException;
import com.llk.admin.model.TherapistLevel;
import com.llk.admin.service.LovService;
import com.llk.admin.util.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class LovController {
	@Autowired
	private LovService lovService;

	@PostMapping(Constants.URL_THERAPIST_LEVEL)
	public @ResponseBody List<TherapistLevel> saveTherapistLevels(@RequestBody List<TherapistLevel> levels)
			throws UserException {
		return lovService.saveTherapistLevels(levels);
	}

	@GetMapping(Constants.URL_THERAPIST_LEVEL)
	public @ResponseBody List<TherapistLevel> supdateTherapistLevels() throws UserException {
		return lovService.getTherapistLevels();
	}

	@DeleteMapping(Constants.URL_THERAPIST_LEVEL)
	public @ResponseBody String deleteTherapistLevels(@RequestBody List<TherapistLevel> levels) throws UserException {
		lovService.deleteTherapistLevels(levels);
		return "Deleted successfully..";
	}
}
