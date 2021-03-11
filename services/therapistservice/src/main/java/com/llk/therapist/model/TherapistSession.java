package com.llk.therapist.model;

public class TherapistSession {
	private int sessionId;
	private String sessionName;

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	@Override
	public String toString() {
		return "TherapistSessions [sessionId=" + sessionId + ", sessionName=" + sessionName + "]";
	}

}
