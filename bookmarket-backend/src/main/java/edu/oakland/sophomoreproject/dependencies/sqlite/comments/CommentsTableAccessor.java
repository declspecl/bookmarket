package edu.oakland.sophomoreproject.dependencies.sqlite.comments;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.model.comments.Comment;
import edu.oakland.sophomoreproject.model.comments.CommentWithoutId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentsTableAccessor extends TableAccessor {
    @Autowired
    public CommentsTableAccessor(SQLiteConfig sqliteConfig) {
        super(sqliteConfig);
    }

    public List<Comment> getAllCommentsForListing(int listingId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE listing_id = ? ORDER BY created_at";

        try (Connection connection = getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, listingId);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int commentId = results.getInt("comment_id");
                String content = results.getString("content");
                Instant createdAt = Instant.parse(results.getString("created_at"));
                int creatorId = results.getInt("creator_id");
                int parentListingId = results.getInt("listing_id");
                Integer parentCommentId = results.getInt("parent_comment_id");
                if (results.wasNull()) {
                    parentCommentId = null;
                }

                Comment comment = new Comment(
                        commentId,
                        content,
                        createdAt,
                        creatorId,
                        parentListingId,
                        parentCommentId
                );
                comments.add(comment);
            }
        }
        return comments;
    }

    // This object is CommentWithoutId because we will get the ID from SQL after creating it
    // This can be done by doing `INSERT INTO comments ... RETURNING comment_id`
    // and parsing the `comment_id` column it returns with `results.getString("comment_id")`
    // Also, you don't need a `createReply` function since the only difference is whether `parentCommentId` is null or not
    public void createComment(CommentWithoutId commentWithoutId) throws SQLException {
        String sql = "INSERT INTO comments (content, created_at, creator_id, listing_id, parent_comment_id) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING comment_id";

        try (Connection connection = getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, commentWithoutId.getContent());
            statement.setString(2, commentWithoutId.getCreatedAt().toString());
            statement.setInt(3, commentWithoutId.getCreatorId());
            statement.setInt(4, commentWithoutId.getParentListingId());

            if (commentWithoutId.getParentCommentId() != null) {
                statement.setInt(5, commentWithoutId.getParentCommentId());
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
            }

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                int generatedId = results.getInt("comment_id");
            }
        }
    }
}
