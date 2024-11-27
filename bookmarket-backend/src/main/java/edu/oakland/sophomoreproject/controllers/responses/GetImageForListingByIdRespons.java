package edu.oakland.sophomoreproject.controllers.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.oakland.sophomoreproject.model.images.Image;

@JsonSerialize
public class GetImageForListingByIdRespons {
    private Image image;

    public GetImageForListingByIdRespons(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
