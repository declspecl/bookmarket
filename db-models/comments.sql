-- db-models/comments.sql

CREATE TABLE comments (
    comment_id INTEGER PRIMARY KEY AUTOINCREMENT,                   -- Unique for each comment, auto-incremented
    parent_comment_id INTEGER,                                      -- ID of the parent comment for replies
    parent_listing_id INTEGER NOT NULL,                             -- ID of the parent comment listing, required
    creator_id INTEGER NOT NULL,                                    -- ID of the author, required
    content TEXT NOT NULL,                                          -- Comment text
    created_at TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL,             -- Timestamp when comment was created
    FOREIGN KEY (creator_id) REFERENCES creators(id),
    FOREIGN KEY (parent_comment_id) REFERENCES comments(comment_id)
);