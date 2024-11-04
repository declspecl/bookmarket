package controllers;

import controllers.requests.CreateCommentRequest;
import controllers.responses.GetAllCommentsForListingResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentsController {
	public CommentsController() {}

	@GetMapping("/api/listing/{listingId}/comment")
	public ResponseEntity<GetAllCommentsForListingResponse> getAllCommentsForListing(
			HttpServletRequest request,
			@PathVariable("listingId") String listingId
	) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/listing/{listingId}/comment")
	public ResponseEntity<Void> createComment(
			HttpServletRequest request,
			@RequestBody CreateCommentRequest payload
	) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/listing/{listingId}/comment/{commentId}/reply")
	public ResponseEntity<Void> createCommentReply(
			HttpServletRequest request,
			@PathVariable("listingId") String listingId,
			@PathVariable("commentId") String commentId,
			@RequestBody CreateCommentRequest payload
	) {
		// ... do logic here

		// ... add session cookie to response

		return ResponseEntity.ok().build();
	}
}
