package edu.oakland.sophomoreproject.dependencies.sqlite.images;

import edu.oakland.sophomoreproject.dependencies.sqlite.TableAccessor;
import edu.oakland.sophomoreproject.model.images.Image;
import edu.oakland.sophomoreproject.model.images.ImageWithoutId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.sql.*;

@Component
public class ImagesTableAccessor extends TableAccessor {
    private static final Logger log = LoggerFactory.getLogger(ImagesTableAccessor.class);

    @Autowired
    public ImagesTableAccessor(SQLiteConfig sqliteConfig) {
        super(sqliteConfig);
    }

    public Image getImageForListing(int listingId) throws SQLException {
        String sql = "SELECT * FROM images WHERE fk_listing_id = ? LIMIT 1";

        Connection connection = getDatabaseConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, listingId);
        ResultSet results = statement.executeQuery();

        if (results.next()) {
            int imageId = results.getInt("image_id");
            String rawBytes = results.getString("raw_bytes");

            return new Image(
                    imageId,
                    listingId,
                    rawBytes
            );
        }

        return null;
    }

    public Image insertImage(ImageWithoutId imageWithoutId) throws SQLException {
        String sql = "INSERT INTO images (fk_listing_id, raw_bytes) VALUES (?, ?)";

        Connection connection = getDatabaseConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, imageWithoutId.getListingId());
        statement.setString(2, imageWithoutId.getRawBytes());

        statement.execute();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        int imageId = generatedKeys.getInt(1);

        log.info("Inserted image with ID {}", imageId);

        return new Image(
                imageId,
                imageWithoutId.getListingId(),
                imageWithoutId.getRawBytes()
        );
    }
}
