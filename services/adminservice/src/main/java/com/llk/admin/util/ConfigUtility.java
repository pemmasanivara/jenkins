package com.llk.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfigUtility {
	@Autowired
	private Environment env;

	public String getProperty(String pPropertyKey) {
		return env.getProperty(pPropertyKey);
	}
	
	public boolean isDevActiveProfle() {
		String[] profiles = env.getActiveProfiles();
		for (String profile : profiles) {
			if (profile != null && profile.equalsIgnoreCase(Constants.PROFILE_DEV)) {
				return true;
			}
		}
		return false;
	}
}
