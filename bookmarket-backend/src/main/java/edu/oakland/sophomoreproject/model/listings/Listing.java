package edu.oakland.sophomoreproject.model.listings;

import java.time.Instant;

public class Listing {
    private Integer id;
    private String title;
    private String description;
    private String authorName;
    private float price;
    private String condition;
    private Instant createdAt;
    private String availability;
    private String classSubject;
    private Integer sellerId;

    public Listing(Integer id, String title, String description, String authorName, float price, String condition, Instant createdAt, String availability, String classSubject, Integer sellerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorName = authorName;
        this.price = price;
        this.condition = condition;
        this.createdAt = createdAt;
        this.availability = availability;
        this.classSubject = classSubject;
        this.sellerId = sellerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }
}
