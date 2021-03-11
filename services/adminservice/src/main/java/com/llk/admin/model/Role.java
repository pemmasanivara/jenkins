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
@Table(name = "ROLES")
@JsonInclude(Include.NON_NULL)
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4388174894823393418L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID", updatable = false, nullable = false)
	private int id;
	@Column(name = "ROLE_NAME", nullable = false)
	private String roleName;

	@Transient
	List<Responsability> responsabilities;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public List<Responsability> getResponsabilities() {
		return responsabilities;
	}

	public void setResponsabilities(List<Responsability> responsabilities) {
		this.responsabilities = responsabilities;
	}

	public Role() {
		
	}

	public Role(int id, String roleName) {
	super();
	this.id = id;
	this.roleName = roleName;
}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + "]";
	}

	
	
}
