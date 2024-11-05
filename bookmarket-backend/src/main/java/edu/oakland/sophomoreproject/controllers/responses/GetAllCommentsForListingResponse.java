package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.model.comments.Comment;

import java.util.List;

@JsonSerialize
public class GetAllCommentsForListingResponse {
    private List<Comment> comments;
}
