package com.llk.user.util;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.llk.user.exception.UserException;
import com.llk.user.model.Party;
import com.llk.user.model.Responsability;
import com.llk.user.model.Role;
import com.nimbusds.jose.JWSObject;

import net.minidev.json.JSONObject;

public class Util {
	private Util() {};
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	/**
	 * 
	 * @param token
	 * @return
	 */
	public static String getEmail(String token) {
		String email=null;
		try {
			JSONObject obj = JWSObject.parse(token).getPayload().toJSONObject();
			if (obj != null) {
				 email = obj.get("preferred_username").toString();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return email;
	}
	
	public static Party getParty(String token) {
		Party party = new Party();
		try {
			JSONObject obj = JWSObject.parse(token).getPayload().toJSONObject();
			if (obj != null) {
				String email = obj.get("preferred_username").toString();
				String name = obj.get("name").toString();
				party.setEmail(email);
				String[] names = name.split(" ");
				party.setFirstName(names[0]);
				if(names.length > 1) {
				  party.setLastName(names[1]);
				}else {
					party.setLastName("   ");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return party;
	}

	public static Set<String> getUniqResponsabilities(List<Role> roles) throws UserException{		
		Set<String> res = new TreeSet<String>();
		if (roles != null) {
			for (Role role : roles) {
				List<Responsability> lres=role.getResponsabilities();
				logger.info("lres-->"+lres);
				for( Responsability r:lres) {
					res.add(r.getResName());
				}
			}
		}
		return res;
	}
}
