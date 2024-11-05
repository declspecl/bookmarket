package edu.oakland.sophomoreproject.controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.oakland.sophomoreproject.model.listings.Availability;

import java.math.BigDecimal;

@JsonDeserialize
public class UpdateListingRequest {
    private BigDecimal price;
    private Availability availability;
}
