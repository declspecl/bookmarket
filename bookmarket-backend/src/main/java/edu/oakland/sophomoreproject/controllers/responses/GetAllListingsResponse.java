package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.model.listings.Listing;

import java.util.List;

@JsonSerialize
public class GetAllListingsResponse {
    private List<Listing> listings;
}
