package com.llk.therapist.model;

public class TherapistLevel {
	private int levelId;
	private String levelName;

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	@Override
	public String toString() {
		return "TherapistLevels [levelId=" + levelId + ", levelName=" + levelName + "]";
	}

}
