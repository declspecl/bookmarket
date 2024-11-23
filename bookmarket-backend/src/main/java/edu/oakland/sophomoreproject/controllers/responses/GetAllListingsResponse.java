package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.controllers.model.ListingDisplayDetails;

import java.util.List;

@JsonSerialize
public class GetAllListingsResponse {
    private List<ListingDisplayDetails> listings;

    public GetAllListingsResponse(List<ListingDisplayDetails> listings) {
        this.listings = listings;
    }

    public List<ListingDisplayDetails> getListings() {
        return listings;
    }

    public void setListings(List<ListingDisplayDetails> listings) {
        this.listings = listings;
    }
}
