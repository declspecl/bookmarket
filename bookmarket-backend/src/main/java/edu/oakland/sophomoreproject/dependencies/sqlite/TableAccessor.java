package edu.oakland.sophomoreproject.dependencies.sqlite;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// notice this is NOT marked with @Component because it is not a spring global object
// it is just meant to be a class for other TableAccessors to extend to get some nice shared utility behavior
public abstract class TableAccessor {
	private final SQLiteConfig sqliteConfig;

	public TableAccessor(SQLiteConfig sqliteConfig) {
		this.sqliteConfig = sqliteConfig;
	}

	public String getDatabaseName() {
		return "bookmarket";
	}

	public String getDatabaseJdbcUrl() {
		return "jdbc:sqlite:" + getDatabaseName() + ".db";
	}

	public Connection getDatabaseConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(getDatabaseJdbcUrl(), sqliteConfig.toProperties());
		connection.setAutoCommit(true);
		return connection;
	}
}
