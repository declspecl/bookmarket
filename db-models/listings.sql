-- db-models/listings.sql
CREATE TABLE listings (
    listings_id INTEGER PRIMARY KEY AUTOINCREMENT,       -- Auto-incrementing ID
    author TEXT NOT NULL,                       
    title TEXT NOT NULL,                     
    price REAL NOT NULL,                            -- Floating-point number
    description TEXT NOT NULL,                     
    seller_id INTEGER NOT NULL,                 -- Foreign key to link to the sellers table
    class_subject TEXT NOT NULL,                        
    condition TEXT NOT NULL,                    
    sale_availability TEXT NOT NULL,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL, 
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE SET NULL
);
