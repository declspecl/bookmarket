package controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class SignUpRequest {
	private String email;
	private String firstName;
	private String lastName;
	private String password;
}
