package edu.oakland.sophomoreproject.dependencies.sqlite.comments;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

@Component
public class CommentsTableAccessor extends TableAccessor {
	@Autowired
	public CommentsTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	// TODO
}
