package edu.oakland.sophomoreproject.controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class SayHelloRequest {
	private String name;

	// required for @JsonDeserialize
	public SayHelloRequest() {}

	public SayHelloRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
