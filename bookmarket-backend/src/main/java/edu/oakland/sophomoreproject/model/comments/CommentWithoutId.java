package edu.oakland.sophomoreproject.model.comments;

import java.time.Instant;

public class CommentWithoutId {
    private String content;
    private Instant createdAt;
    private int creatorId;
    private int parentListingId;
    private Integer parentCommentId;

    public CommentWithoutId(String content, Instant createdAt, int creatorId, int parentListingId, Integer parentCommentId) {
        this.content = content;
        this.creatorId = creatorId;
        this.parentListingId = parentListingId;
        this.parentCommentId = parentCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getParentListingId() {
        return parentListingId;
    }

    public void setParentListingId(int parentListingId) {
        this.parentListingId = parentListingId;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
