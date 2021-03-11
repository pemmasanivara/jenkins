package com.llk.admin.model;

public class RequestParams {
	
	private String firstName;
	private String lastName;
	private String roleName;
	private String srchTxt;
	private String partyId;
	private Integer roleId;
	private int start;
	private int limit;
	private String email;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getSrchTxt() {
		return srchTxt;
	}
	public void setSrchTxt(String srchTxt) {
		this.srchTxt = srchTxt;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "RequestParams [firstName=" + firstName + ", lastName=" + lastName + ", roleName=" + roleName
				+ ", srchTxt=" + srchTxt + ", partyId=" + partyId + ", roleId=" + roleId + ", start=" + start
				+ ", limit=" + limit + ", email=" + email + "]";
	}
	

}
