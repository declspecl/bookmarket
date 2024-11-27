package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.controllers.model.CommentDisplayDetails;

@JsonSerialize
public class CreateCommentResponse {
    private CommentDisplayDetails comment;

    public CreateCommentResponse(CommentDisplayDetails comment) {
        this.comment = comment;
    }

    public CommentDisplayDetails getComment() {
        return comment;
    }

    public void setComment(CommentDisplayDetails comment) {
        this.comment = comment;
    }
}
