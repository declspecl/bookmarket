package dependencies.sqlite.sessions;

import dependencies.sqlite.TableAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.UUID;

// this @Component annotation tells spring to create an instance/object of this class in the global object pool
@Component
public class SessionsTableAccessor extends TableAccessor {
	// the @Autowired annotation tells spring that this class wants it to automatically pass in the
	// parameters of this constructor. in this case it wants spring to provide an SQLiteConfig object
	// which is a @Bean in the SqliteConfiguration class
	@Autowired
	public SessionsTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	/// This will return `null` if no session with the provided ID exists
	public Session getSessionById(UUID sessionId) throws SQLException, DateTimeParseException {
		// this ? syntax as well as "prepared" statements is to avoid a malicious attack called "SQL injection"
		// i plan to talk about it in the future, but look it up early if you're interested
		String sql = "SELECT * FROM sessions WHERE session_id = ? LIMIT 1";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlQuery = connection.prepareStatement(sql);
		// this replaces the question mark in the actual SQL query
		sqlQuery.setString(1, sessionId.toString());

		ResultSet results = sqlQuery.executeQuery();

		// ResultSet.first() returns false if there are no rows
		if (!results.first()) {
			return null;
		}

		UUID resultSessionId = UUID.fromString(results.getString("session_id"));
		Instant resultExpiresAt = Instant.parse(results.getString("expires_at"));
		int resultUserId = results.getInt("user_id");

		return new Session(resultSessionId, resultExpiresAt, resultUserId);
	}

	public void insertSession(Session session) throws SQLException {
		String sql = "INSERT INTO sessions (session_id, expires_at, user_id) VALUES (?, ?, ?)";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlStatement = connection.prepareStatement(sql);
		// these replace the three question marks in the actual SQL statement
		sqlStatement.setString(1, session.getSessionId().toString());
		sqlStatement.setString(2, session.getExpiresAt().toString());
		sqlStatement.setInt(3, session.getUserId());

		sqlStatement.execute();
	}

	public void deleteSessionById(UUID sessionId) throws SQLException {
		String sql = "DELETE FROM sessions WHERE session_id = ?";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlStatement = connection.prepareStatement(sql);
		// this replaces the question mark in the actual SQL statement
		sqlStatement.setString(1, sessionId.toString());

		sqlStatement.execute();
	}
}
