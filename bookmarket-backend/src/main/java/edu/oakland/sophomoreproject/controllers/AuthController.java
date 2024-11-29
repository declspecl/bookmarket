package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.components.PasswordHasher;
import edu.oakland.sophomoreproject.controllers.requests.LoginRequest;
import edu.oakland.sophomoreproject.controllers.requests.SignUpRequest;
import edu.oakland.sophomoreproject.model.auth.User;
import edu.oakland.sophomoreproject.model.auth.UserWithoutId;
import edu.oakland.sophomoreproject.model.sessions.Session;
import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.SessionsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.Instant;

@Log4j2
@RestController
public class AuthController {
	private final PasswordHasher passwordHasher;
	private final ControllerUtils controllerUtils;
	private final SessionAuthorizer sessionAuthorizer;
	private final UsersTableAccessor usersTableAccessor;
	private final SessionsTableAccessor sessionsTableAccessor;

	public AuthController(
			PasswordHasher passwordHasher,
			ControllerUtils controllerUtils,
			SessionAuthorizer sessionAuthorizer,
			UsersTableAccessor usersTableAccessor,
			SessionsTableAccessor sessionsTableAccessor
	) {
		this.passwordHasher = passwordHasher;
		this.controllerUtils = controllerUtils;
		this.sessionAuthorizer = sessionAuthorizer;
		this.usersTableAccessor = usersTableAccessor;
		this.sessionsTableAccessor = sessionsTableAccessor;
	}

	@PostMapping("/api/auth/login")
	public ResponseEntity<Void> login(
			HttpServletRequest request,
			@Validated @RequestBody @NonNull LoginRequest payload
	) throws SQLException {
		if (payload.getEmail() == null || payload.getEmail().isBlank()
			|| payload.getPassword() == null || payload.getPassword().isBlank()
		) {
			return ResponseEntity.status(400).build();
		}

		log.info("Got login request with payload {}", payload);

		String hashedPassword = passwordHasher.hashPassword(payload.getPassword());
		User user = usersTableAccessor.getUserByEmailAndPassword(payload.getEmail(), hashedPassword);
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
			@Validated @NonNull @RequestBody SignUpRequest payload
	) throws SQLException {
		if (
				payload.getFirstName() == null || payload.getFirstName().isBlank()
				|| payload.getLastName() == null || payload.getLastName().isBlank()
				|| payload.getEmail() == null || payload.getEmail().isBlank()
				|| payload.getPassword() == null || payload.getPassword().isBlank()
		) {
			return ResponseEntity.status(400).build();
		}

		log.info("Got signup request with payload {}", payload);

		String hashedPassword = passwordHasher.hashPassword(payload.getPassword());
		UserWithoutId userWithoutId = new UserWithoutId(
				payload.getFirstName(),
				payload.getLastName(),
				payload.getEmail(),
				hashedPassword,
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
		try {
			Session session = sessionAuthorizer.authorize(request);
			sessionsTableAccessor.deleteSessionById(session.getSessionId());
		}
		catch (Exception e){
			log.warn("Got logout request for nonexistent session");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "session=;Max-Age=0;Path=/");
		return ResponseEntity.ok().headers(headers).build();
	}
}
