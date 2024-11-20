package edu.oakland.sophomoreproject.authorization;

import edu.oakland.sophomoreproject.model.sessions.Session;
import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.SessionsTableAccessor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

// this @Component annotation tells spring to create an instance/object of this class in the global object pool
@Log4j2
@Component
public class SessionAuthorizer {
	private final CookieExtractor cookieExtractor;
	private final SessionsTableAccessor sessionsTableAccessor;

	// the @Autowired annotation tells spring that this class wants it to automatically pass in the
	// parameters of this constructor. in this case it wants spring to provide CookieExtractor and SessionsTableAccessor
	// objects which are both also @Component
	@Autowired
	public SessionAuthorizer(
			CookieExtractor cookieExtractor,
			SessionsTableAccessor sessionsTableAccessor
	) {
		this.cookieExtractor = cookieExtractor;
		this.sessionsTableAccessor = sessionsTableAccessor;
	}

	public Session authorize(HttpServletRequest request) throws UnauthorizedException, SQLException {
		// check for if HTTP request has a session cookie
		Cookie sessionCookie;
		try {
			sessionCookie = cookieExtractor.extractSessionCookie(request);
		}
		catch (Exception e) {
			log.info("Cookie with the name `session` does not exist");
			throw new UnauthorizedException("Session cookie was not present in HTTP request");
		}
		if (sessionCookie == null) {
			log.info("Cookie with the name `session` does not exist");
			throw new UnauthorizedException("Session cookie was not present in HTTP request");
		}

		// check for if the session exists in the database
		UUID sessionId;
		try {
			sessionId = UUID.fromString(sessionCookie.getValue());
		}
		catch (Exception e) {
			return null;
		}
		Session dbSession = sessionsTableAccessor.getSessionById(sessionId);
		if (dbSession == null) {
			throw new UnauthorizedException("Session does not exist in the database");
		}

		// check for if the session is expired or not. if expired we also want to delete it from the DB
		Instant now = Instant.now();
		if (dbSession.getExpiresAt().isBefore(now)) {
			sessionsTableAccessor.deleteSessionById(dbSession.getSessionId());
			throw new UnauthorizedException("Session is expired");
		}

		// if we got here and no exceptions were thrown, session is valid
		return dbSession;
	}
}
