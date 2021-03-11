package com.llk.therapist.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.llk.common.Response;
import com.llk.therapist.exception.CalendarException;
import com.llk.therapist.exception.TherapistException;
import com.llk.therapist.model.CommunicationMode;
import com.llk.therapist.model.RequestParams;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapistLevel;
import com.llk.therapist.model.Therapists;
import com.llk.therapist.model.TherapyType;
import com.llk.therapist.service.TherapistService;
import com.llk.therapist.util.Constants;
import com.llk.therapist.util.Util;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class TherapistController {
	private static final Logger logger = LoggerFactory.getLogger(TherapistController.class);

	@Autowired
	TherapistService therapistService;

	@PostMapping(Constants.URL_SAVE_THERAPIST)
	public @ResponseBody Therapist saveTherapist(
			@RequestPart(value = "profileImg", required = false) MultipartFile profileImg,
			@RequestPart(value = "therapist") Therapist therapist) throws TherapistException, IOException {
		logger.info("In side saveTherapist()");
		String avatarUrl = profileImg == null ? "" : Util.saveProfileImage(profileImg); // TODO what if saving fails?
		therapist.setAvatarUrl(avatarUrl);
		return therapistService.saveTherapist(therapist);
	}

	@GetMapping(Constants.URL_SEARCH_THERAPISTS)
	public @ResponseBody Therapists searchTherapists(RequestParams params) throws TherapistException {
		logger.info("In side searchTherapists()-->"+LocalDateTime.now());		
		return therapistService.searchTherapists(params);
	}

	@DeleteMapping(Constants.URL_DELETE_THERAPIST)
	public @ResponseBody Response deleteTherapist(@PathVariable("therapistId") Integer therapistId,
			@RequestParam(name = "bcbaId", required = false) Integer bcbaId) throws TherapistException, CalendarException {
		logger.info("In side deleteTherapist()");
		return therapistService.deleteTherapist(bcbaId, therapistId);
	}

	@GetMapping(Constants.URL_THERAPIST_DETAILS)
	public @ResponseBody Therapist getTherapistDetails(@PathVariable("therapistId") Integer therapistId)
			throws TherapistException {
		logger.info("In side getTherapistDetails()");
		return therapistService.getTherapistDetails(therapistId);
	}

	@GetMapping(Constants.IMG_UPLOAD_PATH + "/{fileName}")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(name = "fileName") String fileName) throws TherapistException, IOException {
		logger.info("In side downloadFile()");
		File file = new File(Constants.IMG_UPLOAD_PATH + fileName);
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}	

	@GetMapping(Constants.URL_THERAPISTS_ALL)
	public @ResponseBody List<Therapist> listTherapists() throws TherapistException {
		logger.info("In side listTherapists()-->"+LocalDateTime.now());
		return therapistService.listTherapists();
	}

	@PutMapping(Constants.URL_UPDATE_THERAPIST)
	public @ResponseBody Therapist updateTherapist(@RequestBody Therapist therapist) throws TherapistException {
		logger.info("In side updateTherapist()-->"+LocalDateTime.now());
		return therapistService.updateTherapist(therapist);
	}

	@GetMapping(Constants.URL_THERAPIST_LEVELS)
	public @ResponseBody List<TherapistLevel> getTherapistLevels() throws TherapistException {
		logger.info("In side getTherapistLevels()-->"+LocalDateTime.now());
		return therapistService.getTherapistLevels();
	}

	@GetMapping(Constants.URL_THERAPY_TYPES)
	public @ResponseBody List<TherapyType> getTherapyTypes() throws TherapistException {
		logger.info("In side getTherapyTypes()-->"+LocalDateTime.now());
		return therapistService.getTherapyTypes();
	}

	@GetMapping(Constants.URL_COMMUNICATION_MODES)
	public @ResponseBody List<CommunicationMode> getCommunicationModes() throws TherapistException, ParseException {		
		logger.info("In side getCommunicationModes()-->"+LocalDateTime.now());
		
		return therapistService.getCommunicationModes();
	}
}
