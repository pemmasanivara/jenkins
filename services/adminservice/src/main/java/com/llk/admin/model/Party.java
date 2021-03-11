package com.llk.admin.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.admin.util.Constants;

@Entity
@Table(name = "PARTY")
@JsonInclude(Include.NON_NULL)
@DynamicUpdate(true)
public class Party implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7512617889210481597L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARTY_ID", updatable = false, nullable = false)
	private int partyId;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "EMAIL", nullable = false)
	private String email;
	@Column(name = "GENDER")
	private String gender;
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	@Column(name = "DOB")
	private LocalDate dob;
	
	
	@Transient
	private List<Role> roleList;
	

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public int getPartyId() {
		return partyId;
	}

	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Override
	public String toString() {
		return "Party [partyId=" + partyId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", gender=" + gender + ", dob=" + dob + ", roleList=" + roleList + "]";
	}
	
}
