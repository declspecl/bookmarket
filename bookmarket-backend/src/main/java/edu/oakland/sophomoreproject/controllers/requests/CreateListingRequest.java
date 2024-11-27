package edu.oakland.sophomoreproject.controllers.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonDeserialize
public class CreateListingRequest {
	private String title;
	private String description;
	private String authorName;
	private float price;
	private String condition;
	private String availability;
	private String classSubject;
	private String imageRawBytes;

	public CreateListingRequest(
			String title,
			String description,
			String authorName,
			float price,
			String condition,
			String availability,
			String classSubject,
			String imageRawBytes
	) {
		this.title = title;
		this.description = description;
		this.authorName = authorName;
		this.price = price;
		this.condition = condition;
		this.availability = availability;
		this.classSubject = classSubject;
		this.imageRawBytes = imageRawBytes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getClassSubject() {
		return classSubject;
	}

	public void setClassSubject(String classSubject) {
		this.classSubject = classSubject;
	}

	public String getImageRawBytes() {
		return imageRawBytes;
	}

	public void setImageRawBytes(String imageRawBytes) {
		this.imageRawBytes = imageRawBytes;
	}
}
