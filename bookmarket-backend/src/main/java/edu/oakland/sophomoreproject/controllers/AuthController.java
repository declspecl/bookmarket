package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.controllers.requests.LoginRequest;
import edu.oakland.sophomoreproject.controllers.requests.SignUpRequest;
import edu.oakland.sophomoreproject.model.auth.UserWithoutId;
import edu.oakland.sophomoreproject.model.sessions.Session;
import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.SessionsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.Instant;

@RestController
public class AuthController {
	private final ControllerUtils controllerUtils;
	private final SessionAuthorizer sessionAuthorizer;
	private final UsersTableAccessor usersTableAccessor;
	private final SessionsTableAccessor sessionsTableAccessor;

	public AuthController(
			ControllerUtils controllerUtils,
			SessionAuthorizer sessionAuthorizer,
			UsersTableAccessor usersTableAccessor,
			SessionsTableAccessor sessionsTableAccessor
	) {
		this.controllerUtils = controllerUtils;
		this.sessionAuthorizer = sessionAuthorizer;
		this.usersTableAccessor = usersTableAccessor;
		this.sessionsTableAccessor = sessionsTableAccessor;
	}

	@PostMapping("/api/auth/login")
	public ResponseEntity<Void> login(
			HttpServletRequest request,
			@RequestBody LoginRequest payload
	) throws SQLException {
		int userId = 0;
		Session session = Session.newRandomSession(userId);

        HttpHeaders httpHeaders = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(httpHeaders).build();
	}

	@PostMapping("/api/auth/signup")
	public ResponseEntity<Void> signup(
			HttpServletRequest request,
			@RequestBody SignUpRequest payload
	) throws SQLException {
		int userId = 0;

		Instant now = Instant.now();

		UserWithoutId userWithoutId = new UserWithoutId(
				payload.getFirstName(),
				payload.getLastName(),
				payload.getEmail(),
				payload.getPassword(),
				payload.getCreatedAt()
		);

		userId = usersTableAccessor.createUser(userWithoutId);

		Session session = Session.newRandomSession(userId);

		HttpHeaders httpHeaders = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(httpHeaders).build();
	}

	@PostMapping("/api/auth/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) throws SQLException {
			Session session = sessionAuthorizer.authorize(request);
			if (session != null) {
				sessionsTableAccessor.deleteSessionById(session.getSessionId());
			}

			HttpHeaders headers = new HttpHeaders();
			headers.add("Set-Cookie", "session='';Expires=0");

			return ResponseEntity.ok().headers(headers).build();

	}
}
