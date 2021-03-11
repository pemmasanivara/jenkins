package com.llk.notification;

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
	@Value("${azure.activedirectory.granttype}")
	private String grantType;
	@Value("${azure.activedirectory.scope}")
	private String scope;
	@Value("${azure.activedirectory.tenant-id}")
	private String tenentId;	

	public String getTenentId() {
		return tenentId;
	}

	public void setTenentId(String tenentId) {
		this.tenentId = tenentId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

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

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	@Override
	public String toString() {
		return "AADConfig [clientSecret=" + clientSecret + ", clientId=" + clientId + ", authority=" + authority
				+ ", grantType=" + grantType + ", scope=" + scope + ", tenentId=" + tenentId + "]";
	}

	
}
