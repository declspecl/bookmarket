package authorization;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// this @Component annotation tells spring to create an instance/object of this class in the global object pool
@Component
public class CookieExtractor {
	/// Returns null if no cookie with the provided name could be found in the HTTP request
	public Cookie extractCookieByName(HttpServletRequest request, String cookieName) {
		return Arrays.stream(request.getCookies())
				.filter(cookie -> cookie.getName().equals(cookieName))
				.findFirst()
				.orElse(null);
	}

	/// Returns null if no session cookie could be found in the HTTP request
	public Cookie extractSessionCookie(HttpServletRequest request) {
		return extractCookieByName(request, "session");
	}
}
