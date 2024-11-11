package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.controllers.requests.CreateListingRequest;
import edu.oakland.sophomoreproject.controllers.requests.UpdateListingRequest;
import edu.oakland.sophomoreproject.controllers.responses.GetAllListingsResponse;
import edu.oakland.sophomoreproject.controllers.responses.GetListingByIdResponse;
import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.dependencies.sqlite.listings.ListingsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import edu.oakland.sophomoreproject.model.sessions.Session;
import edu.oakland.sophomoreproject.model.listings.Listing;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlR2dbcScriptDatabaseInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
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
		// ... do logic here

		System.out.println(listingId);

		return ResponseEntity.ok().build();
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
		}
		catch (Exception exception) {
			return ResponseEntity.status(403).build();
		}

		String title = payload.getTitle();
		String author = session.getEmail(); // Just used email for now can change to name or something else if needed.
		String classSubject = payload.getClassSubject();
		String description = payload.getDescription();
		String condition = payload.getCondition();
		String saleAvailability = payload.getSaleAvailability();
		int sellerId = session.getUserId();

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
