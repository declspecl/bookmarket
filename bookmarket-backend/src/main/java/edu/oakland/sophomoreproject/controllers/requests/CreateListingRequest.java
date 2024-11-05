package edu.oakland.sophomoreproject.controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.oakland.sophomoreproject.model.listings.Availability;
import edu.oakland.sophomoreproject.model.listings.ClassSubject;
import edu.oakland.sophomoreproject.model.listings.Condition;

import java.math.BigDecimal;
import java.time.Instant;

@JsonDeserialize
public class CreateListingRequest {
	private String title;
	private String description;
	private String primaryAuthorName;
	private BigDecimal price;
	private Condition condition;
	private Instant createdAt;
	private Availability availability;
	private ClassSubject classSubject;
}
