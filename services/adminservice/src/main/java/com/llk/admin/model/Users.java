package com.llk.admin.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Users {
	
	private int totalCount;
	
	List<Party> UserRoles;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<Party> getUserRoles() {
		return UserRoles;
	}
	public void setUserRoles(List<Party> userRoles) {
		UserRoles = userRoles;
	}
	
	@Override
	public String toString() {
		return "Users [totalCount=" + totalCount + ", UserRoles=" + UserRoles + "]";
	}
	

}
