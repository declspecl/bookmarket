package edu.oakland.sophomoreproject.dependencies.sqlite.users;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.model.auth.User;
import edu.oakland.sophomoreproject.model.auth.UserWithoutId;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.time.Instant;

@Log4j2
@Component
public class UsersTableAccessor extends TableAccessor {
	@Autowired
	public UsersTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	public User getUserByEmailAndPassword(String email, String password) throws SQLException {
		String sql = "SELECT * FROM users WHERE email = ? AND password = ? LIMIT 1";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlQuery = connection.prepareStatement(sql);
		sqlQuery.setString(1, email);
		sqlQuery.setString(2, password);

		ResultSet results = sqlQuery.executeQuery();

		if (results.next()) {
			Integer userId = results.getInt("user_id");
			String firstName = results.getString("first_name");
			String lastName = results.getString("last_name");
			Instant createdAt = Instant.parse(results.getString("created_at"));

			return new User(userId, firstName, lastName, email, password, createdAt);
		}

		return null;
	}

	public User createUser(UserWithoutId userWithoutId) throws SQLException {
		String sql = "INSERT INTO users (first_name, last_name, email, password, created_at) VALUES (?, ?, ?, ?, ?)";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		sqlStatement.setString(1, userWithoutId.getFirstName());
		sqlStatement.setString(2, userWithoutId.getLastName());
		sqlStatement.setString(3, userWithoutId.getEmail());
		sqlStatement.setString(4, userWithoutId.getPassword());
		sqlStatement.setString(5, userWithoutId.getCreatedAt().toString());

		sqlStatement.executeUpdate();
		ResultSet results = sqlStatement.getGeneratedKeys();

		log.info("Created user");

		if (results.next()) {
			Integer userId = results.getInt(1);

			return new User(
					userId,
					userWithoutId.getFirstName(),
					userWithoutId.getLastName(),
					userWithoutId.getEmail(),
					userWithoutId.getPassword(),
					userWithoutId.getCreatedAt()
			);
		}

		return null;
	}
}
