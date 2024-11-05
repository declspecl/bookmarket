package edu.oakland.sophomoreproject.components;

import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class ControllerUtils {
	public HttpHeaders getHeadersWithSessionCookie(Session session) {
		session.refresh();

		HttpHeaders headers = new HttpHeaders();
		headers.add(
				"Set-Cookie",
				"session=" + session.getSessionId() + "; Expires=" + session.getExpiresAt()
		);

		return headers;
	}
}
