package dependencies.sqlite.sessions;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Session {
	private final UUID sessionId;
	private Instant expiresAt;
	private final int userId;

	public Session(UUID sessionId, Instant expiresAt, int userId) {
		this.sessionId = sessionId;
		this.expiresAt = expiresAt;
		this.userId = userId;
	}

	/// Creates a new session object with a random session ID that expires in one hour
	public static Session newRandomSession(int userId) {
		UUID sessionId = UUID.randomUUID();
		Instant inOneHour = Instant.now().plus(1, ChronoUnit.HOURS);

		return new Session(sessionId, inOneHour, userId);
	}

	public void refresh() {
		Instant inOneHour = Instant.now().plus(1, ChronoUnit.HOURS);

		setExpiresAt(inOneHour);
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public int getUserId() {
		return userId;
	}
}
