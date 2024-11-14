package edu.oakland.sophomoreproject.controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonDeserialize
public class UpdateListingRequest {
    /// this might be `null`
    private BigDecimal price;
    /// this might be `null`
    private String availability;

    public UpdateListingRequest(BigDecimal price, String availability) {
        this.price = price;
        this.availability = availability;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
