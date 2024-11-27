package edu.oakland.sophomoreproject.model.images;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
public class ImageWithoutId {
    private int listingId;
    private String rawBytes;

    public ImageWithoutId(int listingId, String rawBytes) {
        this.listingId = listingId;
        this.rawBytes = rawBytes;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public String getRawBytes() {
        return rawBytes;
    }

    public void setRawBytes(String rawBytes) {
        this.rawBytes = rawBytes;
    }
}
