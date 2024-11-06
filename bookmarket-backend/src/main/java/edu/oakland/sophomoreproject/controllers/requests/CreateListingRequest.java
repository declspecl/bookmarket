package edu.oakland.sophomoreproject.controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.Instant;

@JsonDeserialize
public class CreateListingRequest {
	private String title;
	private String description;
	private String authorName;
	private float price;
	private String condition;
	private Instant createdAt;
	private String availability;
	private String classSubject;

	public CreateListingRequest(
			String title,
			String description,
			String authorName,
			float price,
			String condition,
			Instant createdAt,
			String availability,
			String classSubject
	) {
		this.title = title;
		this.description = description;
		this.authorName = authorName;
		this.price = price;
		this.condition = condition;
		this.createdAt = createdAt;
		this.availability = availability;
		this.classSubject = classSubject;
	}
}
