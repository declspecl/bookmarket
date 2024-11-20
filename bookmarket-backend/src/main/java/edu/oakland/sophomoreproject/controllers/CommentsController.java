package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.controllers.requests.CreateCommentRequest;
import edu.oakland.sophomoreproject.controllers.responses.GetAllCommentsForListingResponse;
import edu.oakland.sophomoreproject.authorization.SessionAuthorizer;
import edu.oakland.sophomoreproject.components.ControllerUtils;
import edu.oakland.sophomoreproject.dependencies.sqlite.comments.CommentsTableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import edu.oakland.sophomoreproject.model.comments.Comment;
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
        List<Comment> comments = commentsTableAccessor.getAllCommentsForListing(listingId);

        GetAllCommentsForListingResponse response = new GetAllCommentsForListingResponse(comments);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/listings/{listingId}/comments")
    public ResponseEntity<Void> createComment(
            HttpServletRequest request,
            @PathVariable("listingId") Integer listingId,
            @RequestBody CreateCommentRequest payload
    ) throws SQLException {
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
        commentsTableAccessor.createComment(newComment);

        HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/api/listings/{listingId}/comments/{parentCommentId}/reply")
    public ResponseEntity<Void> createCommentReply(
            HttpServletRequest request,
            @PathVariable("listingId") Integer listingId,
            @PathVariable("parentCommentId") Integer parentCommentId,
            @RequestBody CreateCommentRequest payload
    ) throws SQLException {
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

        commentsTableAccessor.createComment(replyComment);

        HttpHeaders headers = controllerUtils.getHeadersWithSessionCookie(session);
        return ResponseEntity.ok().headers(headers).build();
    }
}
