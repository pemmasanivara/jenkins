package com.llk.therapist.model;

public class TherapyType {

	private int therapyId;
	private String therapyName;

	public int getTherapyId() {
		return therapyId;
	}

	public void setTherapyId(int therapyId) {
		this.therapyId = therapyId;
	}

	public String getTherapyName() {
		return therapyName;
	}

	public void setTherapyName(String therapyName) {
		this.therapyName = therapyName;
	}

	@Override
	public String toString() {
		return "TherapyTypes [therapyId=" + therapyId + ", therapyName=" + therapyName + "]";
	}

}
