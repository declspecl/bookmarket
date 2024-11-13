package edu.oakland.sophomoreproject.dependencies.sqlite.listings;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.model.listings.Listing;
import edu.oakland.sophomoreproject.model.listings.ListingWithoutId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.sql.*;
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

	public Listing getListingById(int listingId) throws SQLException {
		String sql = "SELECT * FROM listings WHERE listing_id = ?";

		Connection connection = getDatabaseConnection();
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, listingId);

		ResultSet results = statement.executeQuery();

		if (results.next()) {
			String author = results.getString("author");
			String title = results.getString("title");
			float price = results.getFloat("price");
			String description = results.getString("description");
			int sellerId = results.getInt("seller_id");
			String classSubject = results.getString("class_subject");
			String condition = results.getString("condition");
			String saleAvailability = results.getString("sale_availability");
			Instant createdAt = Instant.parse(results.getString("created_at"));

			return new Listing(
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
		}

		return null;
	}

	/// this object is ListingWithoutId because we will get the ID from SQL after creating it
	/// this can be done by doing `INSERT INTO listings ... RETURNING listing_id`
	/// and parsing the `listing_id` column it returns with `results.getString("listing_id")`
	public void createListing(ListingWithoutId listingWithoutId) throws SQLException {
		String sql = "INSERT INTO listings (title, author, description, class_subject, price, condition, created_at, sale_availability, seller_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlQuery = connection.prepareStatement(sql);

		sqlQuery.setString(1, listingWithoutId.getTitle());
		sqlQuery.setString(2, listingWithoutId.getAuthorName());
		sqlQuery.setString(3, listingWithoutId.getClassSubject());
		sqlQuery.setString(4, listingWithoutId.getDescription());
		sqlQuery.setFloat(5, listingWithoutId.getPrice());
		sqlQuery.setString(6, listingWithoutId.getCondition());
		sqlQuery.setString(7, Instant.now().toString());
		sqlQuery.setString(8, listingWithoutId.getAvailability());
		sqlQuery.setInt(9, listingWithoutId.getSellerId());

		sqlQuery.execute();
	}
}
