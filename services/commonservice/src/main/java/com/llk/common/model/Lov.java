package com.llk.common.model;

public class Lov {
	private String value;
	private String display;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return "Lov [value=" + value + ", display=" + display + "]";
	}

}
