package edu.oakland.sophomoreproject.dependencies.sqlite.listings;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.model.listings.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListingsTableAccessor extends TableAccessor {
	@Autowired
	public ListingsTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	public List<Listing> getAllListings() throws SQLException {
		String sql = "SELECT * FROM listings ORDER BY created_at DESC";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlQuery = connection.prepareStatement(sql);

		ResultSet results = sqlQuery.executeQuery();

		List<Listing> listings = new ArrayList<>();

		while (results.next()) {
			int listingId = results.getInt("listing_id");
			String author = results.getString("author");
			String title = results.getString("title");
			float price = results.getFloat("price");
			String description = results.getString("description");
			int sellerId = results.getInt("seller_id");
			String classSubject = results.getString("class_subject");
			String condition = results.getString("condition");
			String saleAvailability = results.getString("sale_availability");
			Instant createdAt = Instant.parse(results.getString("created_at"));

			Listing listing = new Listing(
					listingId,
					title,
					description,
					author,
					price,
					condition,
					createdAt,
					saleAvailability,
					classSubject,
					sellerId
			);

			listings.add(listing);
		}

		return listings;
	}

	// TODO
}
