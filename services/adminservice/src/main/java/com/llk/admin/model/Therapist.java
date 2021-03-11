package com.llk.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Table(name = "THERAPIST")
@JsonInclude(Include.NON_NULL)
public class Therapist extends Party implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1795476403418407033L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "THERAPIST_ID", updatable = false, nullable = false)
	private int therapistId;
	
	@Column(name = "STATUS")
	private String status;

	public int getTherapistId() {
		return therapistId;
	}

	public void setTherapistId(int therapistId) {
		this.therapistId = therapistId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Therapist [therapistId=" + therapistId + ", status=" + status + "]";
	}
	

}
