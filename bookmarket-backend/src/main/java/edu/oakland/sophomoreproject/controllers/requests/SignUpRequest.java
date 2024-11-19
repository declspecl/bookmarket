package edu.oakland.sophomoreproject.controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonDeserialize
public class SignUpRequest {
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private Instant createdAt;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Instant getCreatedAt() { return createdAt; }

	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

}
