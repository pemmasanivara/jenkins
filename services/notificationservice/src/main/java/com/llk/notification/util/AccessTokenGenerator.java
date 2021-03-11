package com.llk.notification.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.llk.notification.AADConfig;


public class AccessTokenGenerator {

	private static final Logger logger = LoggerFactory.getLogger(AccessTokenGenerator.class);

	private static AADConfig aadConfig;

	public static void setAADConfig(AADConfig config) {
		AccessTokenGenerator.aadConfig = config;
		logger.info("aadConfig-->" + aadConfig);
	}

	
}
