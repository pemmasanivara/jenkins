package com.llk.admin.model;

import java.io.Serializable;

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
@Table(name = "RESPONSABILITIES")
@JsonInclude(Include.NON_NULL)
public class Responsability implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7777778023075284858L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RES_ID", updatable = false, nullable = false)
	private int id;
	@Column(name = "RES_NAME", nullable = false)
	private String resName;


	@Transient
	private int mapId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public Responsability() {
		
	}
	
	public Responsability(int id, String resName, int mapId) {
		super();
		this.id = id;
		this.resName = resName;
		this.mapId = mapId;
	}

	@Override
	public String toString() {
		return "Responsability [id=" + id + ", resName=" + resName + "]";
	}
	

}
