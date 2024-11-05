package edu.oakland.sophomoreproject.dependencies.sqlite.listings;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

@Component
public class ListingsTableAccessor extends TableAccessor {
	@Autowired
	public ListingsTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	// TODO
}
