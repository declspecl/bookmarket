package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.model.comments.Comment;

import java.util.List;

@JsonSerialize
public class GetAllCommentsForListingResponse {
    private List<Comment> comments;

    public GetAllCommentsForListingResponse(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
