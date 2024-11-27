package edu.oakland.sophomoreproject.dependencies.sqlite.comments;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.dependencies.sqlite.users.UsersTableAccessor;
import edu.oakland.sophomoreproject.model.auth.User;
import edu.oakland.sophomoreproject.model.comments.CommentWithCreator;
import edu.oakland.sophomoreproject.model.comments.CommentWithoutId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentsTableAccessor extends TableAccessor {
    private final UsersTableAccessor usersTableAccessor;

    @Autowired
    public CommentsTableAccessor(SQLiteConfig sqliteConfig, UsersTableAccessor usersTableAccessor) {
        super(sqliteConfig);

        this.usersTableAccessor = usersTableAccessor;
    }

    public List<CommentWithCreator> getAllCommentsForListing(int listingId) throws SQLException {
        List<CommentWithCreator> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE parent_listing_id = ? ORDER BY created_at DESC";

        Connection connection = getDatabaseConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, listingId);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            int commentId = results.getInt("comment_id");
            String content = results.getString("content");
            Instant createdAt = Instant.parse(results.getString("created_at"));
            int creatorId = results.getInt("creator_id");
            int parentListingId = results.getInt("parent_listing_id");
            Integer parentCommentId = results.getInt("parent_comment_id");

            // if you see this, it's already too late 🙏
            User creator = usersTableAccessor.getUserById(creatorId);

            CommentWithCreator comment = new CommentWithCreator(
                    commentId,
                    content,
                    createdAt,
                    creator,
                    parentListingId,
                    parentCommentId == 0 ? null : parentCommentId
            );

            comments.add(comment);
        }

        return comments;
    }

    public CommentWithCreator createComment(CommentWithoutId commentWithoutId) throws SQLException {
        String sql = "INSERT INTO comments (content, created_at, creator_id, parent_listing_id, parent_comment_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = getDatabaseConnection();
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, commentWithoutId.getContent());
        statement.setString(2, commentWithoutId.getCreatedAt().toString());
        statement.setInt(3, commentWithoutId.getCreatorId());
        statement.setInt(4, commentWithoutId.getParentListingId());
        if (commentWithoutId.getParentCommentId() != null) {
            statement.setInt(5, commentWithoutId.getParentCommentId());
        } else {
            statement.setNull(5, java.sql.Types.INTEGER);
        }

        statement.executeUpdate();
        ResultSet results = statement.getGeneratedKeys();

        int commentId = results.getInt(1);

        // if you see this, it's already too late 🙏
        User creator = usersTableAccessor.getUserById(commentWithoutId.getCreatorId());

        return new CommentWithCreator(
                commentId,
                commentWithoutId.getContent(),
                commentWithoutId.getCreatedAt(),
                creator,
                commentWithoutId.getParentListingId(),
                commentWithoutId.getParentCommentId()
        );
    }
}
