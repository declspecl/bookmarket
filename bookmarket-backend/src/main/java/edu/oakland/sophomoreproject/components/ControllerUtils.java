package edu.oakland.sophomoreproject.components;

import edu.oakland.sophomoreproject.model.sessions.Session;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log4j2
@Component
public class ControllerUtils {
	public HttpHeaders getHeadersWithSessionCookie(Session session) {
		session.refresh();

		String sessionHeader = "session=" + session.getSessionId() + "; Max-Age=" + String.valueOf(60 * 60) + "; Path=/";

		log.info("Created session header {} for session {}", sessionHeader, session);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", sessionHeader);

		return headers;
	}
}
