package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.model.listings.Listing;

@JsonSerialize
public class CreateListingResponse {
    private Listing listing;

    public CreateListingResponse(Listing listing) {
        this.listing = listing;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
}
