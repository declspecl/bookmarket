package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.controllers.requests.CreateCommentRequest;
import edu.oakland.sophomoreproject.controllers.responses.GetAllCommentsForListingResponse;
import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.dependencies.sqlite.comments.CommentsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import edu.oakland.sophomoreproject.model.auth.User;
import edu.oakland.sophomoreproject.model.sessions.Session;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class CommentsController {
	private final ControllerUtils controllerUtils;
	private final SessionAuthorizer sessionAuthorizer;
	/// YOU WILL NEED THIS TO GET THE USER FOR `creator_id`
	private final UsersTableAccessor usersTableAccessor;
	private final CommentsTableAccessor commentsTableAccessor;

	@Autowired
	public CommentsController(
			ControllerUtils controllerUtils,
			SessionAuthorizer sessionAuthorizer,
			UsersTableAccessor usersTableAccessor,
			CommentsTableAccessor commentsTableAccessor
	) {
		this.controllerUtils = controllerUtils;
		this.sessionAuthorizer = sessionAuthorizer;
		this.usersTableAccessor = usersTableAccessor;
		this.commentsTableAccessor = commentsTableAccessor;
	}

	@GetMapping("/api/listings/{listingId}/comments")
	public ResponseEntity<GetAllCommentsForListingResponse> getAllCommentsForListing(
			HttpServletRequest request,
			@PathVariable("listingId") Integer listingId
	) {
		// ... do logic here
		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/listings/{listingId}/comments")
	public ResponseEntity<Void> createComment(
			HttpServletRequest request,
			@RequestBody CreateCommentRequest payload
	) throws SQLException {
		Session session = sessionAuthorizer.authorize(request);

		// ... do logic here

		HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(headers).build();
	}

	@PostMapping("/api/listings/{listingId}/comments/{commentId}/reply")
	public ResponseEntity<Void> createCommentReply(
			HttpServletRequest request,
			@PathVariable("listingId") Integer listingId,
			@PathVariable("commentId") Integer commentId,
			@RequestBody CreateCommentRequest payload
	) throws SQLException {
		Session session = sessionAuthorizer.authorize(request);

		// ... do logic here

		HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
		return ResponseEntity.ok().headers(headers).build();
	}
}
