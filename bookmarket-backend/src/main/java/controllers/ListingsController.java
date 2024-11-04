package controllers;

import controllers.requests.CreateListingRequest;
import controllers.requests.UpdateListingRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListingsController {
	public ListingsController() {}

	@GetMapping("/api/listing/{listingId}")
	public ResponseEntity<Void> getListingById(
			HttpServletRequest request,
			@PathVariable("listingId") String listingId
	) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}

	@GetMapping("/api/listing")
	public ResponseEntity<Void> getAllListings(HttpServletRequest request) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/listing")
	public ResponseEntity<Void> createListing(
			HttpServletRequest request,
			@RequestBody CreateListingRequest payload
	) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}

	@PutMapping("/api/listing/{listingId}")
	public ResponseEntity<Void> updateListing(
			HttpServletRequest request,
			@PathVariable String listingId,
			@RequestBody UpdateListingRequest payload
	) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}
}
