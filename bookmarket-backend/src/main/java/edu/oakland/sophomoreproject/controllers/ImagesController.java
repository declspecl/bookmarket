package edu.oakland.sophomoreproject.controllers;

import edu.oakland.sophomoreproject.controllers.responses.GetImageForListingByIdRespons;
import edu.oakland.sophomoreproject.dependencies.sqlite.images.ImagesTableAccessor;
import edu.oakland.sophomoreproject.model.images.Image;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class ImagesController {
	private static final Logger log = LogManager.getLogger(ImagesController.class);

	private final ImagesTableAccessor imagesTableAccessor;

	@Autowired
	public ImagesController(ImagesTableAccessor imagesTableAccessor) {
		this.imagesTableAccessor = imagesTableAccessor;
	}

	@GetMapping("/api/images/{listingId}")
	public ResponseEntity<GetImageForListingByIdRespons> getImageForListingById(
			HttpServletRequest request,
			@PathVariable("listingId") Integer listingId
	) throws SQLException {
		log.info("Got getImageForListingbyId request for listing with ID {}", listingId);

		Image image = imagesTableAccessor.getImageForListing(listingId);

		log.info(
				"{} an image for listing {}",
				image == null ? "Did not find" : "Found",
				listingId
		);

		return ResponseEntity.ok().body(new GetImageForListingByIdRespons(image));
	}
}
