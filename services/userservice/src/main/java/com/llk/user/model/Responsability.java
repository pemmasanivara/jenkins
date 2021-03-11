package com.llk.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Responsability {
	private Integer resId;
	private String resName;

	public Responsability() {
	}
	
	public Responsability(String resName) {
		this.resName = resName;
	}

	public Responsability(Integer resId, String resName) {
		this.resId = resId;
		this.resName = resName;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

}
