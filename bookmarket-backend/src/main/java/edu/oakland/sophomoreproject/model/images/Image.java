package edu.oakland.sophomoreproject.model.images;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@Data
@JsonSerialize
public class Image {
    private int id;
    private int listingId;
    private String rawBytes;

    public Image(int id, int listingId, String rawBytes) {
        this.id = id;
        this.listingId = listingId;
        this.rawBytes = rawBytes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
