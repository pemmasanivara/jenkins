package com.llk.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Response {
	private String status;
	private String message;
	private String debugMessage;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp = LocalDateTime.now();

	public Response() {
	};

	public Response(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + ", debugMessage=" + debugMessage + ", timestamp="
				+ timestamp + "]";
	}

}
