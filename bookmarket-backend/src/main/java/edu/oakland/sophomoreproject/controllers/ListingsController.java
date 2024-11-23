package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.controllers.model.ListingDisplayDetails;
import edu.oakland.sophomoreproject.controllers.requests.CreateListingRequest;
import edu.oakland.sophomoreproject.controllers.requests.UpdateListingRequest;
import edu.oakland.sophomoreproject.controllers.responses.CreateListingResponse;
import edu.oakland.sophomoreproject.controllers.responses.GetAllListingsResponse;
import edu.oakland.sophomoreproject.controllers.responses.GetListingByIdResponse;
import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.dependencies.sqlite.listings.ListingsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import edu.oakland.sophomoreproject.model.listings.ListingWithSeller;
import edu.oakland.sophomoreproject.model.listings.ListingWithoutId;
import edu.oakland.sophomoreproject.model.sessions.Session;
import edu.oakland.sophomoreproject.model.listings.Listing;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
	private static final Logger log = LogManager.getLogger(ListingsTableAccessor.class);

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
		log.info("Got getListingById request for listing with ID {}", listingId);

		ListingWithSeller listing = listingsTableAccessor.getListingById(listingId);

		GetListingByIdResponse response = new GetListingByIdResponse(ListingDisplayDetails.fromListingWithSeller(listing));
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/api/listings")
	public ResponseEntity<GetAllListingsResponse> getAllListings(HttpServletRequest request) throws SQLException {
		log.info("Got getAllListings request");

		List<ListingWithSeller> listings = listingsTableAccessor.getAllListings();

		GetAllListingsResponse response = new GetAllListingsResponse(
				listings.stream()
						.map(ListingDisplayDetails::fromListingWithSeller)
						.toList()
		);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/api/listings")
	public ResponseEntity<CreateListingResponse> createListing(
			HttpServletRequest request,
			@RequestBody CreateListingRequest payload
	) throws SQLException {
		log.info("Got createListing request with payload {}", payload);

		Session session;
		try {
			session = sessionAuthorizer.authorize(request);
		} catch (Exception exception) {
			log.info(exception.getMessage());
			log.warn("Got createListingRequest from unauthorized user", exception);
			return ResponseEntity.status(403).build();
		}

		String title = payload.getTitle();
		String description = payload.getDescription();
		String authorName = payload.getAuthorName();
		float price = payload.getPrice();
		String condition = payload.getCondition();
		String availability = payload.getAvailability();
		String classSubject = payload.getClassSubject();

		int sellerId = session.getUserId();

		ListingWithoutId listingWithoutId = new ListingWithoutId(
				title,
				description,
				authorName,
				price,
				condition,
				Instant.now(),
				availability,
				classSubject,
				sellerId
		);

		int listingId = listingsTableAccessor.createListing(listingWithoutId);

		Listing listing = new Listing(
				listingId,
				title,
				description,
				authorName,
				price,
				condition,
				Instant.now(),
				availability,
				classSubject,
				sellerId
		);

		CreateListingResponse response = new CreateListingResponse(listing);

		HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(headers).body(response);
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
