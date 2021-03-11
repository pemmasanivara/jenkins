package com.llk.notification.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llk.common.model.AccessToken;
import com.llk.common.model.Lov;
import com.llk.common.model.Message;
import com.llk.notification.AADConfig;
import com.llk.notification.dao.ClientDAO;
import com.llk.notification.dao.TherapistDAO;
import com.llk.notification.exception.EmailException;
import com.llk.notification.model.CalendarEvent;
import com.llk.notification.model.EventResponse;
import com.llk.notification.model.OutLookCalendar;
import com.llk.notification.util.ClientEmailHelper;
import com.llk.notification.util.ClientSmsHelper;
import com.llk.notification.util.Constants;
import com.llk.notification.util.TherapistEmailHelper;
import com.llk.notification.util.TherapistSmsHelper;
import com.llk.notification.util.Util;

@Service
public class TherapistServiceImpl implements TherapistService {
	private static final Logger logger = LoggerFactory.getLogger(TherapistServiceImpl.class);

	@Autowired
	TherapistDAO therapistDAO;

	@Autowired
	EmailService emailService;

	@Autowired
	SmsService smsService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ClientDAO clientDAO;

	@Override
	public List<Message> getAllTherapists(Integer therapistId) {
		return therapistDAO.getAllTherapists(therapistId);
	}

	@Override
	public void handleTherapistMessage(String msg) {
		Message message = Util.getMessage(msg);
		if (message != null) {
			if (message.getType().equalsIgnoreCase(com.llk.common.util.Constants.ADD_MESSAGE_TYPE)) {
				handleAddTherapistNotifications(message);
			} else if (message.getType().equalsIgnoreCase(com.llk.common.util.Constants.CANCEL_THERAPIST_SCHEDULE)) {
				handleTherapistCancelNotifications(message);
			}
		}

	}

	private void handleAddTherapistNotifications(Message message) {
		if (message != null) {
			List<Lov> commModes = message.getCommunicationMode();
			if (commModes != null) {
				commModes.forEach(lov -> {
					String mode = lov.getDisplay();
					if (mode != null && mode.equalsIgnoreCase(Constants.COMM_MODE_EMAIL)) {
						sendNewTherapistEmail(message);
					}
					if (mode != null && mode.equalsIgnoreCase(Constants.COMM_MODE_SMS)) {
						sendNewTherapistSms(message);
					}
				});
			}
			Integer therapistId = message.getTherapistId();
			List<Message> therapists = this.getAllTherapists(therapistId);
			if (therapists != null && therapists.size() > 0) {
				therapists.stream().forEach(therapist -> {
					List<Lov> cModes = therapist.getCommunicationMode();
					if (cModes != null && cModes.size() > 0) {
						cModes.forEach(lov -> {
							String cMode = lov.getDisplay();
							if (cMode != null && cMode.equalsIgnoreCase(Constants.COMM_MODE_EMAIL)) {
								try {
									emailService.sendMail(Constants.FROM_EMAIL, therapist.getTherapistEmail(),
											TherapistEmailHelper
													.getAddTherapistEmailSubject(therapist.getTherapistName()),
											TherapistEmailHelper.getAddTherapistEmailBody(therapist.getTherapistName(),
													therapist));
								} catch (Exception ex) {
									logger.error("Error while sending email-->", ex);
								}
							}
							if (cMode != null && cMode.equalsIgnoreCase(Constants.COMM_MODE_SMS)) {
								try {
									smsService.sendSMS(therapist.getTherapistPhone(),
											TherapistSmsHelper.getNewTherapistMessage(therapist.getTherapistName()));
								} catch (Exception ex) {
									logger.error("Error while sending email-->", ex);
								}

							}
						});
					}
				});
			}

		} else {
			logger.info("There are no other therapists to send mail.");
		}

	}

	private void handleTherapistCancelNotifications(Message message) {
		List<Message> clients = therapistDAO.getClients(message.getTherapistId(), message.getTherapyId(),
				message.getTherapistAvailabilityId());
		logger.info("clients-->" + clients);
		if (clients != null && clients.size() > 0) {
			for (Message client : clients) {
				// Send communication
				List<Lov> cModes = client.getCommunicationMode();
				if (cModes != null && cModes.size() > 0) {
					for (Lov commMode : cModes) {
						if (commMode != null) {
							if (commMode.getDisplay().equalsIgnoreCase(Constants.COMM_MODE_EMAIL)) {
								try {
									emailService.sendMail(Constants.FROM_EMAIL, client.getClientEmail(),
											ClientEmailHelper.getCancelEventSubject(client),
											ClientEmailHelper.getCancelEventBody(client));
								} catch (Exception ex) {
									logger.error("Exception-->", ex);
								}

							}
							if (commMode.getDisplay().equalsIgnoreCase(Constants.COMM_MODE_SMS)) {
								try {
									smsService.sendSMS(client.getClientPhone(),
											ClientSmsHelper.getCancelEventMessage(client));
								} catch (Exception ex) {
									logger.error("Exception-->", ex);
								}

							}
						}

					}
				}
				// Cancel outlook event
				if (client.getOutLookEventId() != null) {
					cancelEventInOutLook(client.getTherapistEmail(), client.getOutLookEventId());
				}

			}
		}

	}

	private void sendNewTherapistEmail(Message newTherapist) {
		if (newTherapist != null) {
			try {
				emailService.sendMail(Constants.FROM_EMAIL, newTherapist.getTherapistEmail(), "Welcome to LifeLab Kids",
						"Hello," + newTherapist.getTherapistName() + "<br/>Welcome to LLK<br/>Regards<br/>Team LLK");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void sendNewTherapistSms(Message newTherapist) {
		if (newTherapist != null) {
			try {
				smsService.sendSMS(newTherapist.getTherapistPhone(),
						TherapistSmsHelper.getNewTherapistMessage(newTherapist.getTherapistName()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void sendEmailOnAddClientSchedule(Message client) throws EmailException {
		logger.info("sendEmailOnAddClientSchedule()-->" + client);
		Message therapist = therapistDAO.getTherapist(client.getTherapistId());
		therapist.setClientId(client.getClientId());
		therapist.setTherapyId(client.getTherapyId());
		therapist.setTherapistId(client.getTherapistId());
		therapist.setClientName(client.getClientName());
		logger.info("therapist()-->" + therapist);
		if (therapist != null) {
			// Send to event to therapist calendar
			createEventInOutLook(therapist);
			emailService.sendMail(Constants.FROM_EMAIL, therapist.getTherapistEmail(), "New Appointment Booked",
					TherapistEmailHelper.getClientScheduleAddSubject(therapist));
		}

	}

	private AccessToken getAccessToken() {
		logger.info("##########getAccessToken#########");
		AccessToken token = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
			AADConfig aadConfig = ClientEmailHelper.getAADConfig();
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("client_id", aadConfig.getClientId());
			map.add("scope", aadConfig.getScope());
			map.add("client_secret", aadConfig.getClientSecret());
			map.add("grant_type", aadConfig.getGrantType());

			String url = Constants.MS_EDGE_BASE_URL.replace("{tenentid}", aadConfig.getTenentId());
			logger.info("url-->" + url);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);
			ResponseEntity<AccessToken> response = restTemplate.postForEntity(url, request, AccessToken.class);

			token = response.getBody();
			logger.info("Token-->" + token);
		} catch (Exception ex) {
			logger.error("error-->" + ex);
		}
		return token;
	}

	private void cancelEventInOutLook(String therapistEmail, String eventId) {
		logger.info("cancelEventInOutLook()-->" + therapistEmail);
		logger.info("eventId-->" + eventId);
		try {
			AccessToken token = getAccessToken();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + token.getAccess_token());
			String url = Constants.MS_EDGE_CANCEL_EVENT.replace("{email}", therapistEmail).replace("{eventId}",eventId);
			logger.info("url-->" + url);
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
			logger.info("response-->" + response.getBody());
		} catch (Exception ex) {
			logger.error("error-->" + ex);
		}
	}

	private void createEventInOutLook(Message client) {
		logger.info("createEventInOutLook()-->" + client);
		String therapistEmail = client.getTherapistEmail();
		HashMap<Long, String> eventIds = new HashMap<>();
		try {
			// get all the events
			List<CalendarEvent> events = therapistDAO.getTherapistOutLookEvents(client.getClientId(),
					client.getTherapistId(), client.getTherapyId());
			System.out.println("events-->" + events);
			if (events != null && events.size() > 0) {
				ObjectMapper mapper = new ObjectMapper();
				// Get the access token
				AccessToken token = getAccessToken();
				// Getting the calendar id
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization", "Bearer " + token.getAccess_token());
				String url = Constants.MS_EDGE_CALENDAR_URL.replace("{email}", therapistEmail);
				logger.info("url-->" + url);
				HttpEntity<?> entity = new HttpEntity<Object>(headers);
				ResponseEntity<OutLookCalendar> response = restTemplate.exchange(url, HttpMethod.GET, entity,
						OutLookCalendar.class);
				logger.info("call response-->" + response.getBody());
				String calendarId = response.getBody().getId();
				// Pushing events to calendar
				String calurl = Constants.MS_EDGE_CALENDAR_EVENTS_URL.replace("{email}", therapistEmail)
						.replace("{calId}", calendarId);
				logger.info("calurl-->" + calurl);
				headers.set("Content-Type", "application/json");
				for (CalendarEvent event : events) {
					Long clientScId = event.getClientScheduleId();
					event.setClientScheduleId(null);
					String eventsJson = mapper.writeValueAsString(event);
					HttpEntity<String> calentity = new HttpEntity<String>(eventsJson, headers);
					System.out.println(eventsJson);
					ResponseEntity<EventResponse> res = restTemplate.exchange(calurl, HttpMethod.POST, calentity,
							EventResponse.class);
					eventIds.put(clientScId, res.getBody().getId());
				}
				System.out.println(eventIds);
				// Update client schedule status
				clientDAO.updateOutLookEventSendStatus(client.getClientId(), client.getTherapistId(),
						client.getTherapyId());
				clientDAO.updateOutLookEventIds(eventIds);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("error-->" + ex);
		}
	}

}
