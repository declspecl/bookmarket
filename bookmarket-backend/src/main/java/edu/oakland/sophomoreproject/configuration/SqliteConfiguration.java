package edu.oakland.sophomoreproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteConfig;

// the @Configuration annotation is a bit special
// instead of spring creating an object of SqliteConfiguration,
// it will instead run all the methods of this class and add the objects returned by methods with the @Bean
// annotation to the global object pool
@Configuration
public class SqliteConfiguration {
	// so @Bean annotation on this method means spring will add a global SQLiteConfig object to the global object pool
	@Bean
	public SQLiteConfig sqliteConfig() {
		SQLiteConfig sqliteConfig = new SQLiteConfig();

		// small configuration change in SQLite that can make it more performant sometimes
		sqliteConfig.setJournalMode(SQLiteConfig.JournalMode.WAL);

		return sqliteConfig;
	}
}
