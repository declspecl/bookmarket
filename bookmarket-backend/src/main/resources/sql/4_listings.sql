CREATE TABLE IF NOT EXISTS listings (
    listing_id INTEGER PRIMARY KEY AUTOINCREMENT,
    author TEXT NOT NULL,
    title TEXT NOT NULL,
    price REAL NOT NULL,
    description TEXT NOT NULL,
    seller_id INTEGER NOT NULL,
    class_subject TEXT NOT NULL,
    condition TEXT NOT NULL,
    sale_availability TEXT NOT NULL,
    expires_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE SET NULL
);
