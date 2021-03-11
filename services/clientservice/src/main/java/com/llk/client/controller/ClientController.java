package com.llk.client.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.llk.client.exception.CalendarException;
import com.llk.client.exception.ClientException;
import com.llk.client.model.Client;
import com.llk.client.model.Clients;
import com.llk.client.model.CommunicationMode;
import com.llk.client.model.RequestParams;
import com.llk.client.model.TherapyType;
import com.llk.client.service.ClientService;
import com.llk.client.util.Constants;
import com.llk.client.util.Util;
import com.llk.common.Response;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Constants.API_VERSION)
public class ClientController {
	@Autowired
	private ClientService clientService;

	@PostMapping(Constants.URL_SAVE_CLIENT)
	public @ResponseBody Client createClient(@RequestPart(value = "profileImg",required = false) MultipartFile profileImg,
			@RequestPart(value = "client") Client client) throws ClientException, IOException {
		String avatarUrl = profileImg == null ? "" : Util.saveProfileImage(profileImg);// TODO what if saving fails?
		client.setAvatarUrl(avatarUrl);
		return clientService.createClient(client);
	}

	@GetMapping(Constants.URL_SEARCH_CLIENTS)
	public @ResponseBody Clients searchClients(RequestParams params) throws ClientException {		
		return clientService.searchClients(params);
	}

	@DeleteMapping(Constants.URL_DELETE_CLIENT)
	public @ResponseBody Response deleteClient(@PathVariable("clientId") Integer clientId) throws ClientException, CalendarException {
		return clientService.deleteClient(clientId);
	}

	@GetMapping(Constants.IMG_UPLOAD_PATH + "/{fileName}")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(name = "fileName") String fileName) throws ClientException, IOException {
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

	@GetMapping(Constants.URL_CLIENTS_ALL)
	public @ResponseBody List<Client> getAllClients() throws ClientException {
		return clientService.getAllClients();
	}

	@PutMapping(Constants.URL_UPDATE_CLIENT)
	public @ResponseBody Client udateClient(@RequestBody Client client) throws ClientException {
		return clientService.updateClient(client);
	}
	
	@GetMapping(Constants.URL_DETAIL_CLIENT)
	public @ResponseBody Client getClientDetails(@PathVariable("clientId") Integer clientId) throws ClientException {
		return clientService.getClientDetails(clientId);
	}

	@GetMapping(Constants.URL_CLIENT_UNSUBSCRIBE)
	public @ResponseBody String unsubscribe(@PathVariable("clientId") Integer clientId) throws ClientException {
		return clientService.unsubscribe(clientId);
	}
	
	@GetMapping(Constants.URL_THERAPY_TYPES)
	public @ResponseBody List<TherapyType> getTherapyTypes() throws ClientException {
		return clientService.getTherapyTypes();
	}

	@GetMapping(Constants.URL_COMMUNICATION_MODES)
	public @ResponseBody List<CommunicationMode> getCommunicationModes() throws ClientException {
		return clientService.getCommunicationModes();
	}
}
