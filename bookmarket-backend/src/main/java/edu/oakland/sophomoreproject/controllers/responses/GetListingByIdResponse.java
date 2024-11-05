package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.model.listings.Listing;

@JsonSerialize
public class GetListingByIdResponse {
    private Listing listing;
}
