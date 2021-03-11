package com.llk.therapist.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.llk.common.model.Lov;
import com.llk.common.model.Party;

@JsonInclude(Include.NON_EMPTY)
public class Therapist extends Party {
	private int therapistId;
	private String levelName;
	private Integer levelId;
	private String status;
	private List<Lov> communicationMode;
	private Integer communicationModeId;
	private Integer bcbaId;
	private String certifications;
	private String therapyName;
	private String therapyId;
	private TherapistSchedule schedule;
	private String comunicationId;

	public String getComunicationId() {
		return comunicationId;
	}

	public void setComunicationId(String comunicationId) {
		this.comunicationId = comunicationId;
	}

	public TherapistSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(TherapistSchedule schedule) {
		this.schedule = schedule;
	}

	public String getTherapyName() {
		return therapyName;
	}

	public void setTherapyName(String therapyName) {
		this.therapyName = therapyName;
	}

	public String getTherapyId() {
		return therapyId;
	}

	public void setTherapyId(String therapyId) {
		this.therapyId = therapyId;
	}

	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}

	public Integer getBcbaId() {
		return bcbaId;
	}

	public void setBcbaId(Integer bcbaId) {
		this.bcbaId = bcbaId;
	}

	public List<Lov> getCommunicationMode() {
		return communicationMode;
	}

	public void setCommunicationMode(List<Lov> communicationMode) {
		this.communicationMode = communicationMode;
	}

	public Integer getCommunicationModeId() {
		return communicationModeId;
	}

	public void setCommunicationModeId(Integer communicationModeId) {
		this.communicationModeId = communicationModeId;
	}

	public int getTherapistId() {
		return therapistId;
	}

	public void setTherapistId(int therapistId) {
		this.therapistId = therapistId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Therapist [therapistId=" + therapistId + ", levelName=" + levelName + ", levelId=" + levelId
				+ ", status=" + status + ", communicationMode=" + communicationMode + ", communicationModeId="
				+ communicationModeId + ", bcbaId=" + bcbaId + ", certifications=" + certifications + ", therapyName="
				+ therapyName + ", therapyId=" + therapyId + ", schedule=" + schedule + ", comunicationId="
				+ comunicationId + "]";
	}

}
