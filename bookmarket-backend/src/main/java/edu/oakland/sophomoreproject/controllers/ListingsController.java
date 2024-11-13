package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.controllers.requests.CreateListingRequest;
import edu.oakland.sophomoreproject.controllers.requests.UpdateListingRequest;
import edu.oakland.sophomoreproject.controllers.responses.GetAllListingsResponse;
import edu.oakland.sophomoreproject.controllers.responses.GetListingByIdResponse;
import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.dependencies.sqlite.listings.ListingsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import edu.oakland.sophomoreproject.model.listings.ListingWithoutId;
import edu.oakland.sophomoreproject.model.sessions.Session;
import edu.oakland.sophomoreproject.model.listings.Listing;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlR2dbcScriptDatabaseInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@RestController
public class ListingsController {
	private final ControllerUtils controllerUtils;
	private final SessionAuthorizer sessionAuthorizer;
	private final UsersTableAccessor usersTableAccessor;
	private final ListingsTableAccessor listingsTableAccessor;

	@Autowired
	public ListingsController(
			ControllerUtils controllerUtils,
			SessionAuthorizer sessionAuthorizer,
			UsersTableAccessor usersTableAccessor,
			ListingsTableAccessor listingsTableAccessor
	) {
		this.controllerUtils = controllerUtils;
		this.sessionAuthorizer = sessionAuthorizer;
		this.usersTableAccessor = usersTableAccessor;
		this.listingsTableAccessor = listingsTableAccessor;
	}

	@GetMapping("/api/listings/{listingId}")
	public ResponseEntity<GetListingByIdResponse> getListingById(
			HttpServletRequest request,
			@PathVariable("listingId") Integer listingId
	) throws SQLException {
		Listing listing = listingsTableAccessor.getListingById(listingId);

		System.out.println(listingId);

		if (listing == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		GetAllListingsResponse response = new GetAllListingsResponse(listing);

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/api/listings")
	public ResponseEntity<GetAllListingsResponse> getAllListings(HttpServletRequest request) throws SQLException {
		List<Listing> listings = listingsTableAccessor.getAllListings();

		GetAllListingsResponse response = new GetAllListingsResponse(listings);

		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/api/listings")
	public ResponseEntity<Void> createListing(
			HttpServletRequest request,
			@RequestBody CreateListingRequest payload
	) throws SQLException {
		Session session;
		try {
			session = sessionAuthorizer.authorize(request);
		} catch (Exception exception) {
			return ResponseEntity.status(403).build();
		}

		String title = payload.getTitle();
		String description = payload.getDescription();
		String authorName = payload.getAuthorName();
		float price = payload.getPrice();
		String condition = payload.getCondition();
		Instant createdAt = payload.getCreatedAt();
		String availability = payload.getAvailability();
		String classSubject = payload.getClassSubject();

		int sellerId = session.getUserId();

		ListingWithoutId listingWithoutId = new ListingWithoutId(
				title,
				description,
				authorName,
				price,
				condition,
				createdAt,
				availability,
				classSubject,
				sellerId
		);

		try {
			listingsTableAccessor.createListing(listingWithoutId);
		} catch (SQLException e){
			return ResponseEntity.status(500).build();
		}
		HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(headers).build();
	}

	@PutMapping("/api/listings/{listingId}")
	public ResponseEntity<Void> updateListing(
			HttpServletRequest request,
			@PathVariable Integer listingId,
			@RequestBody UpdateListingRequest payload
	) throws SQLException {
		Session session = sessionAuthorizer.authorize(request);

		// ... do logic here

		HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(headers).build();
	}
}
