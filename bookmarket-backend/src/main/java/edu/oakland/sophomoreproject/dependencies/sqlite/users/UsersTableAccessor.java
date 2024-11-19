package edu.oakland.sophomoreproject.dependencies.sqlite.users;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.model.auth.User;
import edu.oakland.sophomoreproject.model.auth.UserWithoutId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

@Component
public class UsersTableAccessor extends TableAccessor {
	@Autowired
	public UsersTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	public User getUserById(int userId) throws SQLException {
		String sql = "SELECT * FROM users WHERE userId = ? LIMIT 1";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlQuery = connection.prepareStatement(sql);
		sqlQuery.setInt(1, userId);

		ResultSet results = sqlQuery.executeQuery();

		if (results.next()) {
			String email = results.getString("email");
			String password = results.getString("password");
		}

		return null;
	}

	/// this object is UserWithoutId because we will get the ID from SQL after creating it
	/// this can be done by doing `INSERT INTO users ... RETURNING user_id`
	/// and parsing the `user_id` column it returns with `results.getString("user_id")`
	public int createUser(UserWithoutId userWithoutId) throws SQLException {
		String sql = "INSERT INTO users (email, password) VALUES (?, ?) RETURNING user_id";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlStatement = connection.prepareStatement(sql);
		sqlStatement.setString(1, userWithoutId.getEmail());
		sqlStatement.setString(2, userWithoutId.getPassword());

		sqlStatement.executeUpdate();

		ResultSet generatedKeys = sqlStatement.getGeneratedKeys();

		if (generatedKeys.next()) {
			return generatedKeys.getInt(1);
		} else {
			throw new SQLException();
		}
	}
}
