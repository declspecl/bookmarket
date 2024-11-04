package controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class LoginRequest {
	private String email;
	private String password;
}
