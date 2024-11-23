package edu.oakland.sophomoreproject.dependencies.sqlite.listings;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import edu.oakland.sophomoreproject.model.auth.User;
import edu.oakland.sophomoreproject.model.listings.ListingWithSeller;
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
	private final UsersTableAccessor usersTableAccessor;

	@Autowired
	public ListingsTableAccessor(SQLiteConfig sqliteConfig, UsersTableAccessor usersTableAccessor) {
		super(sqliteConfig);

		this.usersTableAccessor = usersTableAccessor;
	}

	public List<ListingWithSeller> getAllListings() throws SQLException {
		String sql = "SELECT * FROM listings ORDER BY created_at DESC";

		Connection connection = getDatabaseConnection();
		PreparedStatement sqlQuery = connection.prepareStatement(sql);

		ResultSet results = sqlQuery.executeQuery();

		List<ListingWithSeller> listings = new ArrayList<>();

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

			// if you see this, it's already too late 🙏
			User seller = usersTableAccessor.getUserById(sellerId);

			ListingWithSeller listing = new ListingWithSeller(
					listingId,
					title,
					description,
					author,
					price,
					condition,
					createdAt,
					saleAvailability,
					classSubject,
					seller
			);

			listings.add(listing);
		}

		return listings;
	}

	public ListingWithSeller getListingById(int listingId) throws SQLException {
		String sql = "SELECT * FROM listings WHERE listing_id = ? LIMIT 1";

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

			// if you see this, it's already too late 🙏
			User seller = usersTableAccessor.getUserById(sellerId);

			return new ListingWithSeller(
					listingId,
					title,
					description,
					author,
					price,
					condition,
					createdAt,
					saleAvailability,
					classSubject,
					seller
			);
		}

		return null;
	}

	public int createListing(ListingWithoutId listingWithoutId) throws SQLException {
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

		sqlQuery.executeUpdate();

		ResultSet results = sqlQuery.getGeneratedKeys();
		return results.getInt(1);
	}
}
