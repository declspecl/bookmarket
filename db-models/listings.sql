-- db-models/listings.sql
CREATE TABLE listings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,       -- Auto-incrementing ID
    author TEXT NOT NULL,                       
    title TEXT NOT NULL,                     
    price  NOT NULL,                            -- Floating-point number
    item_description TEXT NOT NULL,              
    seller TEXT NOT NULL,                       
    seller_id INTEGER NOT NULL,                 -- Foreign key to link to the sellers table
    class TEXT NOT NULL,                        
    condition TEXT NOT NULL,                    
    sale_availability TEXT NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE SET NULL
);
