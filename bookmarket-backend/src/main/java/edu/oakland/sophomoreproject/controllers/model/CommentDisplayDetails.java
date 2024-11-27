package edu.oakland.sophomoreproject.controllers.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.model.comments.CommentWithCreator;

import java.time.Instant;

@JsonSerialize
public class CommentDisplayDetails {
    private int id;
    private String content;
    private Instant createdAt;
    private UserDisplayDetails creator;
    private int parentListingId;
    private Integer parentCommentId;

    public CommentDisplayDetails(int id, String content, Instant createdAt, UserDisplayDetails creator, int parentListingId, Integer parentCommentId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.creator = creator;
        this.parentListingId = parentListingId;
        this.parentCommentId = parentCommentId;
    }

    public static CommentDisplayDetails fromCommentWithCreator(CommentWithCreator comment) {
        return new CommentDisplayDetails(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                UserDisplayDetails.fromUser(comment.getCreator()),
                comment.getParentListingId(),
                comment.getParentCommentId()
        );
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

    public UserDisplayDetails getCreator() {
        return creator;
    }

    public void setCreator(UserDisplayDetails creator) {
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
