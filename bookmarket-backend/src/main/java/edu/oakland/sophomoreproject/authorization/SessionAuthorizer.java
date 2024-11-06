package edu.oakland.sophomoreproject.authorization;

import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.Session;
import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.SessionsTableAccessor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

// this @Component annotation tells spring to create an instance/object of this class in the global object pool
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
			throw new UnauthorizedException("Session cookie was not present in HTTP request");
		}
		if (sessionCookie == null) {
			throw new UnauthorizedException("Session cookie was not present in HTTP request");
		}

		// check for if the session exists in the database
		String sessionId = sessionCookie.getValue();
		Session dbSession = sessionsTableAccessor.getSessionById(UUID.fromString(sessionId));
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
