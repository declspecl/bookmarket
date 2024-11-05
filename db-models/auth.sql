-- dm-models/auth.sql

-- User Table 

CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,              -- Identifes each user 
    first_name TEXT NOT NULL,                               -- User's first name
    last_name TEXT NOT NULL,                                -- User's Last name
    email TEXT NOT NULL,                                    -- User's email address 
    password TEXT NOT NULL,                                 -- User's Password
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP                
);

-- Session Table 

CREATE TABLE sessions (
    session_id TEXT NOT NULL PRIMARY KEY,                             -- Unique session token, UUID format (e.g., `6e8f68bb-e62b-4861-aadb-ea408ce5ae31`)
    user_id INTEGER NOT NULL,                                         -- Links session to user
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP                -- Timestamp for when the session was created
    expires_at TEXT NOT NULL,                                         -- Timestamp for when the session expires
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
