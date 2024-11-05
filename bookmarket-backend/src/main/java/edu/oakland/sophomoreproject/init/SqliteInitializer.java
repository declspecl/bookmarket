package edu.oakland.sophomoreproject.init;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class SqliteInitializer extends TableAccessor {
	private final ResourcePatternResolver resourcePatternResolver;

	@Autowired
	public SqliteInitializer(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);

		this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
	}

	public void initialize() throws SQLException, IOException {
		executeTableSchema();
	}

	private void executeTableSchema() throws SQLException, IOException {
		Resource[] resources = resourcePatternResolver.getResources("classpath*:sql/*.sql");
		List<Resource> sortedResources = Arrays.stream(resources)
			.sorted(Comparator.comparing(Resource::getFilename))
			.toList();

		Connection connection = getDatabaseConnection();

		for (Resource resource : sortedResources) {
			InputStream inputStream = new DataInputStream(resource.getInputStream());

			String sql = new String(inputStream.readAllBytes());
			connection.createStatement().execute(sql);
		};
	}
}
