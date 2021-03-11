package com.llk.therapist;

import java.net.MalformedURLException;
import java.util.Collections;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.OnBehalfOfParameters;
import com.microsoft.aad.msal4j.UserAssertion;

public class AccessTokenGenerator {
	public static void main(String[] a) throws MalformedURLException {
		
		 String authToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlNzWnNCTmhaY0YzUTlTNHRycFFCVEJ5TlJSSSJ9.eyJhdWQiOiI2MzdjYmViYy1kYWUwLTQxNzItYmJmNy1hZDhiODEyMDU5NDEiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vMWU1OTBiMWYtNWZmZi00OTU5LWFiMDgtYTMwMTExNWU4MDI2L3YyLjAiLCJpYXQiOjE1OTIyODc3MjUsIm5iZiI6MTU5MjI4NzcyNSwiZXhwIjoxNTkyMjkxNjI1LCJhaW8iOiJBVFFBeS84UEFBQUFQZklMKzhUS2F5Y3JpN25ndUFhU2FlZ0lhSysxeFRTZ2pZWWNFa3doSHdvUHMxa1MwK0N5b1VzMEpDNnJjRzN6IiwibmFtZSI6IlZhcmFwcmFzYWQgUGVtbWFzYW5pIiwibm9uY2UiOiIzMTVkNzM4YS1jMzE4LTQzMjEtYTZlOS1mZjEzYjkyYWJiOTUiLCJvaWQiOiI4MzllYTNlYy1jMGQ1LTRhZjUtYjQ5Zi0yNTJlNzIxY2RhNzMiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJWYXJhcHJhc2FkLlBlbW1hc2FuaUBqcmRzaS5jb20iLCJzdWIiOiJBbW9kWHdIVFB3QXo5SWRyWDQ5a0RQam8xdVc1b2xsMjNWNE0zb3ZHTmtZIiwidGlkIjoiMWU1OTBiMWYtNWZmZi00OTU5LWFiMDgtYTMwMTExNWU4MDI2IiwidXRpIjoiakZGTGNLcEhQRU8tbDl6cEd1ODdBUSIsInZlciI6IjIuMCJ9.YUyzm0rcppu7AoYu1MUSiYCcsXjIZb5orCEQH_AuQjIslz4JFTcyA_DvIFNE7K2s_Wln3cey45FFmed1ZpPNfyO4diy1Td_2iOyHDdH_MCREJClzl-FegVdjoE_nYdw9Q5dZnYPr17M870hQ0soXHHkrN3-ytlzc6FKTpxEabZ2vSzZ8LNFokdKvp6rr71DxdkG0HfyDHs46hehlDWe9u69Wi4n86VoVGQbtr5XM3_tJXI26xS0BjvB4p9TUh7wdjH6_N94Y1cnutAqVjckcd_D4VylllW_NmY3deRLIvaTizM2nHSr0gKUTPkqKPHp2aZibVWbFiJB5rOzfclorJA";
		 
		 ConfidentialClientApplication application =
		                ConfidentialClientApplication.builder("637cbebc-dae0-4172-bbf7-ad8b81205941", 
						ClientCredentialFactory.create("16_7hu_6R65-B1HsnQM_3HBOxZrCBwFCi6"))
		                        .authority("https://login.microsoftonline.com/common/").build();
								
								
								 OnBehalfOfParameters parameters =
		                    OnBehalfOfParameters.builder(Collections.singleton("User.Read"),
		                            new UserAssertion(authToken))
		                            .build();

								 IAuthenticationResult auth = application.acquireToken(parameters).join();
								 System.out.println(auth.accessToken());

	}
}
