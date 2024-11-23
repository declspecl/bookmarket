package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.controllers.model.ListingDisplayDetails;

@JsonSerialize
public class GetListingByIdResponse {
    private ListingDisplayDetails listing;

    public GetListingByIdResponse(ListingDisplayDetails listing) {
        this.listing = listing;
    }

    public ListingDisplayDetails getListing() {
        return listing;
    }

    public void setListing(ListingDisplayDetails listing) {
        this.listing = listing;
    }
}
