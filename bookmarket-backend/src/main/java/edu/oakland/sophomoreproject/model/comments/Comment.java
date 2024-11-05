package edu.oakland.sophomoreproject.model.comments;

import java.time.Instant;

public class Comment {
    private Integer id;
    private String content;
    private Instant createdAt;
    private Integer creatorId;
    private Integer parentListingId;
    private Integer parentCommentId;
}
