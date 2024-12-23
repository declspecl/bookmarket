package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.controllers.model.CommentDisplayDetails;
import edu.oakland.sophomoreproject.controllers.requests.CreateCommentRequest;
import edu.oakland.sophomoreproject.controllers.responses.CreateCommentResponse;
import edu.oakland.sophomoreproject.controllers.responses.GetAllCommentsForListingResponse;
import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.dependencies.sqlite.comments.CommentsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import edu.oakland.sophomoreproject.model.comments.CommentWithCreator;
import edu.oakland.sophomoreproject.model.comments.CommentWithoutId;
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
import java.time.Instant;
import java.util.List;

@RestController
public class CommentsController {
    private final ControllerUtils controllerUtils;
    private final SessionAuthorizer sessionAuthorizer;
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
    ) throws SQLException {
        List<CommentWithCreator> comments = commentsTableAccessor.getAllCommentsForListing(listingId);

        GetAllCommentsForListingResponse response = new GetAllCommentsForListingResponse(
                comments.stream()
                        .map(CommentDisplayDetails::fromCommentWithCreator)
                        .toList()
        );
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/listings/{listingId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            HttpServletRequest request,
            @PathVariable("listingId") Integer listingId,
            @RequestBody CreateCommentRequest payload
    ) throws SQLException {
        if (payload.getContent() == null || payload.getContent().isBlank()) {
            return ResponseEntity.status(400).build();
        }

        Session session;
        try {
            session = sessionAuthorizer.authorize(request);
        } catch (Exception exception) {
            return ResponseEntity.status(403).build();
        }

        CommentWithoutId newComment = new CommentWithoutId(
                payload.getContent(),
                Instant.now(),
                session.getUserId(),
                listingId,
                null
        );
        CommentWithCreator comment = commentsTableAccessor.createComment(newComment);

        HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
        return ResponseEntity.ok().headers(headers).body(
                new CreateCommentResponse(CommentDisplayDetails.fromCommentWithCreator(comment))
        );
    }

    @PostMapping("/api/listings/{listingId}/comments/{parentCommentId}/reply")
    public ResponseEntity<CreateCommentResponse> createCommentReply(
            HttpServletRequest request,
            @PathVariable("listingId") Integer listingId,
            @PathVariable("parentCommentId") Integer parentCommentId,
            @RequestBody CreateCommentRequest payload
    ) throws SQLException {
        if (payload.getContent() == null || payload.getContent().isBlank()) {
            return ResponseEntity.status(400).build();
        }

        Session session;
        try {
            session = sessionAuthorizer.authorize(request);
        } catch (Exception exception) {
            return ResponseEntity.status(403).build();
        }

        CommentWithoutId replyComment = new CommentWithoutId(
                payload.getContent(),
                Instant.now(),
                session.getUserId(),
                listingId,
                parentCommentId
        );

        CommentWithCreator comment = commentsTableAccessor.createComment(replyComment);

        HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
        return ResponseEntity.ok().headers(headers).body(
                new CreateCommentResponse(CommentDisplayDetails.fromCommentWithCreator(comment))
        );
    }
}
