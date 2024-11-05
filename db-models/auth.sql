-- dm-models/auth.sql

-- User Table 

CREATE TABLE users (
    userId INTEGER PRIMARY KEY AUTOINCREMENT,               -- Identifes each user 
    firstName TEXT NOT NULL,                                -- User's first name
    lastName TEXT NOT NULL,                                 -- User's Last name
    email TEXT NOT NULL,                                    -- User's email address 
    password TEXT NOT NULL,                                 -- User's Password
    createdAt TEXT DEFAULT CURRENT_TIMESTAMP                
);

-- Session Table 

CREATE TABLE session (
    userId INTEGER NOT NULL,                                       -- Links session to user
    sessionId TEXT PRIMARY KEY,                                    -- Unique session token, UUID format (e.g., `6e8f68bb-e62b-4861-aadb-ea408ce5ae31`)
    createdAt TEXT DEFAULT CURRENT_TIMESTAMP                       -- Timestamp for when the session was created
    expiresAt TEXT NOT NULL,                                       -- Timestamp for when the session expires
    FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE
);