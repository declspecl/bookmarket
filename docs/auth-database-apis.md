# Database Design


## Entities


### 1. Users


Table for User Information:


| Column      | Type      | Description                            |
|-------------|-----------|----------------------------------------|
| `userId`    | INTEGER   | Primary Key (PK). Identifies each user.|
| `email`     | TEXT      | User's email address.                  |
| `firstName` | TEXT      | User's first name.                     |
| `lastName`  | TEXT      | User's last name.                      |
| `password`  | TEXT      | User's password.                       |
| `createdAt` | TIMESTAMP | DATE/TIME                              |
### 2. Session


Table for sessions:


| Column      | Type      | Description                             |
|-------------|-----------|-----------------------------------------|
| `userId`    | INTEGER   | Foreign Key (FK). Links session to User.|
| `sessionId` | TEXT      | Unique ession token for the session. UUID format (e.g., `6e8f68bb-e62b-4861-aadb-ea408ce5ae31`).                 |
| `createdAt` | TIMESTAMP | DATE/TIME the session was created.      |
| `expiresAt` | TIMESTAMP | DATE/TIME the session expires.          |
---
