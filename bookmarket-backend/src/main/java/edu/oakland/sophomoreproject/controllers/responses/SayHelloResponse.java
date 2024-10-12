package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;

@JsonSerialize
public class SayHelloResponse {
	private Instant timestamp;
	private String message;

	public SayHelloResponse(Instant timestamp, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
