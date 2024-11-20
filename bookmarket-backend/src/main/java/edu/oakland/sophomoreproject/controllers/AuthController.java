package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.controllers.requests.LoginRequest;
import edu.oakland.sophomoreproject.controllers.requests.SignUpRequest;
import edu.oakland.sophomoreproject.model.auth.User;
import edu.oakland.sophomoreproject.model.auth.UserWithoutId;
import edu.oakland.sophomoreproject.model.sessions.Session;
import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.SessionsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.Instant;

@Log4j2
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
		log.info("Got login request with payload {}", payload);

		User user = usersTableAccessor.getUserByEmailAndPassword(payload.getEmail(), payload.getPassword());
		if (user == null) {
			return ResponseEntity.status(401).build();
		}

		Session session = Session.newRandomSession(user.getId());
		sessionsTableAccessor.createSession(session);

        HttpHeaders httpHeaders = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(httpHeaders).build();
	}

	@PostMapping("/api/auth/signup")
	public ResponseEntity<Void> signup(
			HttpServletRequest request,
			@RequestBody SignUpRequest payload
	) throws SQLException {
		log.info("Got signup request with payload {}", payload);

		UserWithoutId userWithoutId = new UserWithoutId(
				payload.getFirstName(),
				payload.getLastName(),
				payload.getEmail(),
				payload.getPassword(),
				Instant.now()
		);

		User createdUser = usersTableAccessor.createUser(userWithoutId);

		Session session = Session.newRandomSession(createdUser.getId());
		sessionsTableAccessor.createSession(session);

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
		headers.add("Set-Cookie", "session=;Max-Age=0;Path=/");
		return ResponseEntity.ok().headers(headers).build();
	}
}
