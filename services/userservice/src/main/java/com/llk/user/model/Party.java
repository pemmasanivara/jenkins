package com.llk.user.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.user.util.Constants;

@Entity
@Table(name = "PARTY")
@JsonInclude(Include.NON_NULL)
public class Party implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072589638281832510L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARTY_ID", updatable = false, nullable = false)
	private int partyId;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;
	@Column(name = "DOB")
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate dob;
	@Column(name = "PHONE")
	private String phone;
	@Column(name = "GENDER")
	private String gender;
	@Column(name = "ATTRIBUTE1")
	private String attributeOne;
	@Column(name = "ATTRIBUTE2")
	private String attributeTwo;
	@Column(name = "ATTRIBUTE3")
	private String attributeThree;
	@Column(name = "ATTRIBUTE4")
	private String attributeFour;
	@Column(name = "ATTRIBUTE5")
	private String attributeFive;
	@Transient
	private List<Role> roles;
	@Transient
	private Set<String> responsabilities;
	@Transient
	private String msUserId;	
	@Transient
	private String msCalendarId;
	@Transient
    private String msPrincipalName;    

	public String getMsPrincipalName() {
		return msPrincipalName;
	}

	public void setMsPrincipalName(String msPrincipalName) {
		this.msPrincipalName = msPrincipalName;
	}

	public String getMsUserId() {
		return msUserId;
	}

	public void setMsUserId(String msUserId) {
		this.msUserId = msUserId;
	}

	public String getMsCalendarId() {
		return msCalendarId;
	}

	public void setMsCalendarId(String msCalendarId) {
		this.msCalendarId = msCalendarId;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Set<String> getResponsabilities() {
		return responsabilities;
	}

	public void setResponsabilities(Set<String> responsabilities) {
		this.responsabilities = responsabilities;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getAttributeOne() {
		return attributeOne;
	}

	public void setAttributeOne(String attributeOne) {
		this.attributeOne = attributeOne;
	}

	public String getAttributeTwo() {
		return attributeTwo;
	}

	public void setAttributeTwo(String attributeTwo) {
		this.attributeTwo = attributeTwo;
	}

	public String getAttributeThree() {
		return attributeThree;
	}

	public void setAttributeThree(String attributeThree) {
		this.attributeThree = attributeThree;
	}

	public String getAttributeFour() {
		return attributeFour;
	}

	public void setAttributeFour(String attributeFour) {
		this.attributeFour = attributeFour;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAttributeFive() {
		return attributeFive;
	}

	public void setAttributeFive(String attributeFive) {
		this.attributeFive = attributeFive;
	}

	@Override
	public String toString() {
		return "Party [partyId=" + partyId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", dob=" + dob + ", phone=" + phone + ", gender=" + gender + ", attributeOne=" + attributeOne
				+ ", attributeTwo=" + attributeTwo + ", attributeThree=" + attributeThree + ", attributeFour="
				+ attributeFour + ", attributeFive=" + attributeFive + ", roles=" + roles + ", responsabilities="
				+ responsabilities + ", msUserId=" + msUserId + ", msCalendarId=" + msCalendarId + ", msPrincipalName="
				+ msPrincipalName + "]";
	}

	
}
