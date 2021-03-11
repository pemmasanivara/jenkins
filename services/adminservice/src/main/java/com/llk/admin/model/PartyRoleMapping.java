package com.llk.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name = "PARTY_ROLES_MAPING")
@JsonInclude(Include.NON_NULL)
public class PartyRoleMapping implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1016550090496817910L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "PARTY_ID")
	private int partyId;
	@Column(name = "ROLE_ID")
	private int roleId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPartyId() {
		return partyId;
	}

	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public PartyRoleMapping() {
		
	}
	
	public PartyRoleMapping(int id, int partyId, int roleId, String roleName) {
		super();
		this.id = id;
		this.partyId = partyId;
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "PartyRoleMapping [partyId=" + partyId + ", roleId=" + roleId + "]";
	}
	
}
