package com.llk.schedular;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Message {
	private Integer clientId;
	private Integer therapistId;
	private String clientEmail;
	private String therapistEmail;
	private String clientName;
	private String therapistName;
	private String clientPhone;
	private String therapistPhone;
	private String therapyName;
	private Integer therapyId;

	public String getTherapyName() {
		return therapyName;
	}

	public void setTherapyName(String therapyName) {
		this.therapyName = therapyName;
	}

	public Integer getTherapyId() {
		return therapyId;
	}

	public void setTherapyId(Integer therapyId) {
		this.therapyId = therapyId;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getTherapistPhone() {
		return therapistPhone;
	}

	public void setTherapistPhone(String therapistPhone) {
		this.therapistPhone = therapistPhone;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getTherapistId() {
		return therapistId;
	}

	public void setTherapistId(Integer therapistId) {
		this.therapistId = therapistId;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getTherapistEmail() {
		return therapistEmail;
	}

	public void setTherapistEmail(String therapistEmail) {
		this.therapistEmail = therapistEmail;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getTherapistName() {
		return therapistName;
	}

	public void setTherapistName(String therapistName) {
		this.therapistName = therapistName;
	}

	@Override
	public String toString() {
		return "Message [clientId=" + clientId + ", therapistId=" + therapistId + ", clientEmail=" + clientEmail
				+ ", therapistEmail=" + therapistEmail + ", clientName=" + clientName + ", therapistName="
				+ therapistName + ", clientPhone=" + clientPhone + ", therapistPhone=" + therapistPhone + "]";
	}

}
