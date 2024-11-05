package edu.oakland.sophomoreproject.controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class LoginRequest {
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}