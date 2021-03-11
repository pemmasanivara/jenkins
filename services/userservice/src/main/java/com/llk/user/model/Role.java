package com.llk.user.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class Role {
	private String roleName;
	private Integer roleId;
	private List<Responsability> responsabilities;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public List<Responsability> getResponsabilities() {
		return responsabilities;
	}
	public void setResponsabilities(List<Responsability> responsabilities) {
		this.responsabilities = responsabilities;
	}
	
}
