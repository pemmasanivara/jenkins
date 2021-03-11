package com.llk.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AADConfig {
	@Value("${azure.activedirectory.client-secret}")
	private String clientSecret;
	@Value("${azure.activedirectory.client-id}")
	private String clientId;
	@Value("${azure.activedirectory.authority}")
	private String authority;

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "AADConfig [clientSecret=" + clientSecret + ", clientId=" + clientId + ", authority=" + authority + "]";
	}
	
	

}
