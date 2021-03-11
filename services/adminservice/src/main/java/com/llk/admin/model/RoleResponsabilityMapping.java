package com.llk.admin.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "ROLES_RES_MAPPING")
@JsonInclude(Include.NON_NULL)
public class RoleResponsabilityMapping implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9057574306192764459L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	private int id;
	@Column(name = "ROLE_ID", nullable = false)
	private int roleId;
	@Column(name = "RES_ID", nullable = false)
	private int resId;
	
	@Transient
	private String roleName;
	
	@Transient
	private List<Role> roles;
	
	@Transient
	private List<Responsability> responsabilities;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Responsability> getResponsabilities() {
		return responsabilities;
	}

	public void setResponsabilities(List<Responsability> responsabilities) {
		this.responsabilities = responsabilities;
	}

	@Override
	public String toString() {
		return "RoleResponsabilityMapping [id=" + id + ", roleId=" + roleId + ", resId=" + resId + "]";
	}


}
