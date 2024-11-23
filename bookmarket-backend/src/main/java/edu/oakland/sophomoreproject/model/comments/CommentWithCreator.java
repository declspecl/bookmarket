package edu.oakland.sophomoreproject.model.comments;

import edu.oakland.sophomoreproject.model.auth.User;

import java.time.Instant;

public class CommentWithCreator {
    private int id;
    private String content;
    private Instant createdAt;
    private User creator;
    private int parentListingId;
    /// this can be `null` if its not a reply
    private Integer parentCommentId;

    public CommentWithCreator(int id, String content, Instant createdAt, User creator, int parentListingId, Integer parentCommentId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.creator = creator;
        this.parentListingId = parentListingId;
        this.parentCommentId = parentCommentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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
