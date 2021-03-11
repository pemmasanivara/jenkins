package com.llk.user;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.llk.user.util.AccessTokenGenerator;

@Component
public class StaticContextInitializer {
	@Autowired
	private AADConfig aadCofig;

	@PostConstruct
	public void init() {
		AccessTokenGenerator.setAADConfig(aadCofig);
	}
}
