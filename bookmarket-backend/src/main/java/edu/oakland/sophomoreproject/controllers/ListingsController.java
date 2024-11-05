package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.controllers.requests.CreateListingRequest;
import edu.oakland.sophomoreproject.controllers.requests.UpdateListingRequest;
import edu.oakland.sophomoreproject.controllers.responses.GetAllListingsResponse;
import edu.oakland.sophomoreproject.controllers.responses.GetListingByIdResponse;
import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.dependencies.sqlite.listings.ListingsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.sessions.Session;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class ListingsController {
	private final ControllerUtils controllerUtils;
	private final SessionAuthorizer sessionAuthorizer;
	private final ListingsTableAccessor listingsTableAccessor;

	public ListingsController(
			ControllerUtils controllerUtils,
			SessionAuthorizer sessionAuthorizer,
			ListingsTableAccessor listingsTableAccessor
	) {
		this.controllerUtils = controllerUtils;
		this.sessionAuthorizer = sessionAuthorizer;
		this.listingsTableAccessor = listingsTableAccessor;
	}

	@GetMapping("/api/listings/{listingId}")
	public ResponseEntity<GetListingByIdResponse> getListingById(
			HttpServletRequest request,
			@PathVariable("listingId") Integer listingId
	) {
		// ... do logic here

		System.out.println(listingId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/api/listings")
	public ResponseEntity<GetAllListingsResponse> getAllListings(HttpServletRequest request) {
		// ... do logic here

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/listings")
	public ResponseEntity<Void> createListing(
			HttpServletRequest request,
			@RequestBody CreateListingRequest payload
	) throws SQLException {
		Session session = sessionAuthorizer.authorize(request);

		// ... do logic here

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
