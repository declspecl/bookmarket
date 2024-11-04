-- db-models/sessions.sql

CREATE TABLE sessions (
    session_token TEXT NOT NULL,                            -- Unique token for each session
    created_at TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL,     -- Timestamp of when the session was created
    expired_at TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL      -- Timestamp of when the session will expire
);