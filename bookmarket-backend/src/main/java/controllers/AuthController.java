package controllers;

import controllers.requests.LoginRequest;
import controllers.requests.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	public AuthController() {}

	@PostMapping("/login")
	public ResponseEntity<Void> login(
			HttpServletRequest request,
			@RequestBody LoginRequest payload
	) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(
			HttpServletRequest request,
			@RequestBody SignUpRequest payload
	) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}
}
