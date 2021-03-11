package com.llk.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {
	@Value("${host.url}")
	private String hostUrl;

	public String getHostUrl() {
		return hostUrl;
	}

}
