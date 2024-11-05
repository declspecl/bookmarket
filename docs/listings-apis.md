# Listings API

## Overview

This API enables interaction with a listings data source using JSON format, providing CRUD (Create, Read, Update, Delete) operations for managing listings. Each endpoint interacts with `listings.json` and performs operations such as retrieval, addition, updates, and deletion of listing data. JSON stubs serve as examples for request and response data structures.

## Endpoints

### GET /api/listings

Retrieve all listings available.

#### Example Response
```json
{
  "listings": [
    {
      "id": 1,
      "author": "nameExample",
      "title": "Used Bicycle",
      "price": 150.00,
      "description": "A gently used mountain bike.",
      "seller": "Student1",
      "sellerID": 294720,
      "class": "Sports",
      "condition": "Used",
      "saleAvailability": "Available"
    }
  ]
}
```

---

### GET /api/listings/{listingId}

Retrieve a specific listing by its ID.

#### Example Request
`GET /api/listings/1`

#### Example Response
```json
{
  "id": 1,
  "author": "nameExample",
  "title": "Used Bicycle",
  "price": 150.00,
  "description": "A gently used mountain bike.",
  "seller": "Student1",
  "sellerID": 294720,
  "class": "Sports",
  "condition": "Used",
  "saleAvailability": "Available"
}
```

---

### POST /api/listings

Create a new listing. The request body should contain the listing details.

#### Example Request Body
```json
{
  "author": "nameExample",
  "title": "New Item Title",
  "price": 200.00,
  "description": "Description of the new item.",
  "seller": "Student3",
  "sellerID": 294722,
  "class": "Electronics",
  "condition": "New",
  "saleAvailability": "Available"
}
```

---

### PUT /api/listings/{listingId}

Update an existing listing by its ID. Include the updated details in the request body.

#### Example Request
`PUT /api/listings/2`

#### Example Request Body
```json
{
  "price": 35.00,
  "saleAvailability": "Unavailable"
}
```

---

### DELETE /api/listings/{listingId}

Delete a specific listing by its ID.

#### Example Request
`DELETE /api/listings/1`

---

## CRUD Operations Summary

- **Create**: `POST /api/listings`
- **Read**: `GET /api/listings`
- **Update**: `PUT /api/listings/{listingId}`
- **Delete**: `DELETE /api/listings/{listingId}`

---

## JSON Data Interaction

- **GET Requests**: Retrieve data from `listings.json`.
- **POST Requests**: Add new listing to the array and update `listings.json`.
- **PUT Requests**: Modify listing details by ID and save changes.
- **DELETE Requests**: Remove listing by ID from `listings.json`.

---
