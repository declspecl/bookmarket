package edu.oakland.sophomoreproject.dependencies.sqlite.users;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

@Component
public class UsersTableAccessor extends TableAccessor {
	@Autowired
	public UsersTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	// TODO
}
