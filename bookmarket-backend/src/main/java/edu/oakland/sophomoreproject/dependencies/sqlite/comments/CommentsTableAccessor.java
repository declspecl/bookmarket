package edu.oakland.sophomoreproject.dependencies.sqlite.comments;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.model.comments.Comment;
import edu.oakland.sophomoreproject.model.comments.CommentWithoutId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentsTableAccessor extends TableAccessor {
	@Autowired
	public CommentsTableAccessor(SQLiteConfig sqliteConfig) {
		super(sqliteConfig);
	}

	public List<Comment> getAllCommentsForListing(int commentId) {
		List<Comment> comments = new ArrayList<>();

		// TODO

		return comments;
	}

	/// this object is CommentWithoutId because we will get the ID from SQL after creating it
	/// this can be done by doing `INSERT INTO comments ... RETURNING comment_id`
	/// and parsing the `comment_id` column it returns with `results.getString("comment_id")`
	/// also you don't need a `createReply` function since the only difference is whether `parentCommentId` is null or not
	public void createComment(CommentWithoutId commentWithoutId) {
		// TODO
	}
}
