-- db-models/comments.sql

CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,          -- Unique for each comment
    parent_comment_id INTEGER,              -- ID of the parent comment for replies
    author_name TEXT NOT NULL,              -- Author comment's name
    content TEXT NOT NULL,                  -- Comment text
    created_at TIMESTAMP DEFAULT NOW()      --Timestamp when comment was created
);