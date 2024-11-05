package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.controllers.requests.LoginRequest;
import edu.oakland.sophomoreproject.controllers.requests.SignUpRequest;
import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.Session;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	ControllerUtils controllerUtils;

	public AuthController(
			ControllerUtils controllerUtils
	) {
		this.controllerUtils = controllerUtils;
	}

	@PostMapping("/api/login")
	public ResponseEntity<Void> login(
			HttpServletRequest request,
			@RequestBody LoginRequest payload
	) {
		int userId = 0;
		// ... do logic here and get the correct user id

		Session session = Session.newRandomSession(userId);

		HttpHeaders httpHeaders = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(httpHeaders).build();
	}

	@PostMapping("/api/signup")
	public ResponseEntity<Void> signup(
			HttpServletRequest request,
			@RequestBody SignUpRequest payload
	) {
		int userId = 0;
		// ... do logic here and get the correct user id

		Session session = Session.newRandomSession(userId);

		HttpHeaders httpHeaders = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(httpHeaders).build();
	}
}
