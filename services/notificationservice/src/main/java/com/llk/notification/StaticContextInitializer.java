package com.llk.notification;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.llk.notification.util.ClientEmailHelper;

@Component
public class StaticContextInitializer {
	@Autowired
	private NotificationConfig notificationConfig;
	
	@Autowired
	private AADConfig aadConfig;

	@PostConstruct
	public void init() {
		ClientEmailHelper.setNotificationConfig(notificationConfig);
		ClientEmailHelper.setAADConfig(aadConfig);
	}
}
