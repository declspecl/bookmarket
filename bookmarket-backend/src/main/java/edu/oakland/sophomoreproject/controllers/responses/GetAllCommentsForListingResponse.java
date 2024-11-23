package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.controllers.model.CommentDisplayDetails;

import java.util.List;

@JsonSerialize
public class GetAllCommentsForListingResponse {
    private List<CommentDisplayDetails> comments;

    public GetAllCommentsForListingResponse(List<CommentDisplayDetails> comments) { this.comments = comments; }

    public List<CommentDisplayDetails> getComments() {
        return comments;
    }

    public void setComments(List<CommentDisplayDetails> comments) {
        this.comments = comments;
    }
}
