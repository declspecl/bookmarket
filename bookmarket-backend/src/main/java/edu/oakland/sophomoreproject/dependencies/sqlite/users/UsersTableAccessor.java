package edu.oakland.sophomoreproject.dependencies.sqlite.users;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.model.auth.User;
import edu.oakland.sophomoreproject.model.auth.UserWithoutId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

@Component
public class UsersTableAccessor extends TableAccessor {
	@Autowired
	public UsersTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	public User getUserById(int userId) {
		// TODO
		return null;
	}

	/// this object is UserWithoutId because we will get the ID from SQL after creating it
	/// this can be done by doing `INSERT INTO users ... RETURNING user_id`
	/// and parsing the `user_id` column it returns with `results.getString("user_id")`
	public void createUser(UserWithoutId userWithoutId) {
		// TODO
	}
}
