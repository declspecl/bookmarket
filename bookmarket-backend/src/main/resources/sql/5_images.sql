CREATE TABLE IF NOT EXISTS images (
    image_id INTEGER PRIMARY KEY AUTOINCREMENT,
    fk_listing_id INTEGER UNIQUE NOT NULL,
    raw_bytes BLOB NOT NULL,
    FOREIGN KEY (fk_listing_id) REFERENCES listings(listing_id)
);
