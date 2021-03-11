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
public class Client extends Party implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 792260469857453333L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENT_ID", updatable = false, nullable = false)
	private int clientID;
	
	@Column(name = "STATUS")
	private String status;
	
	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Client [clientID=" + clientID + ", status=" + status + "]";
	}

	

}
