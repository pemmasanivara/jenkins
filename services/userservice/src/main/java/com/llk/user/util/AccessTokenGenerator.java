package com.llk.user.util;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.llk.user.AADConfig;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.OnBehalfOfParameters;
import com.microsoft.aad.msal4j.UserAssertion;

public class AccessTokenGenerator {

	private static final Logger logger = LoggerFactory.getLogger(AccessTokenGenerator.class);

	private static AADConfig aadConfig;

	public static void setAADConfig(AADConfig config) {
		AccessTokenGenerator.aadConfig = config;
		logger.info("aadConfig-->" + aadConfig);
	}

	public static String getAccessToken(String authToken) {
		String accessToken = null;
		try {

			ConfidentialClientApplication application = ConfidentialClientApplication
					.builder(aadConfig.getClientId(), ClientCredentialFactory.create(aadConfig.getClientSecret()))
					.authority(aadConfig.getAuthority()).build();

			OnBehalfOfParameters parameters = OnBehalfOfParameters
					.builder(Collections.singleton("User.Read"), new UserAssertion(authToken)).build();

			IAuthenticationResult auth = application.acquireToken(parameters).join();
			if (auth != null) {
				accessToken = auth.accessToken();
			}

		} catch (Exception ex) {
			logger.error("Accesstoken error-->", ex);
		}

		return accessToken;
	}

}
