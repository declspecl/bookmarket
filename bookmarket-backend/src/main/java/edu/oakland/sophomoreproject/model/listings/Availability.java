package edu.oakland.sophomoreproject.model.listings;

public enum Availability {
    AVAILABLE,
    CANCELLED,
    SOLD;

    public static Availability[] ALL_AVAILABILITIES = Availability.values();
}
