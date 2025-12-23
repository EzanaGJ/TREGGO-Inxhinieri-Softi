package Test;

import DAO.JdbcReviewDAO;
import Model.Review;
import Service.ReviewService;
import db.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ReviewServiceTest {

    private ReviewService reviewService;

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM review");
        }

        reviewService = new ReviewService(new JdbcReviewDAO());
    }

    @Test
    void testCreateAndGetReview() throws SQLException {

        Review r = reviewService.createReview(89, 3, 5, "Excellent seller!");
        Assertions.assertNotEquals(0, r.getReviewId());

        Review found = reviewService.getReviewById(r.getReviewId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals(89, found.getReviewedUserId());
        Assertions.assertEquals(3, found.getOrderId());
        Assertions.assertEquals(5, found.getRating());
        Assertions.assertEquals("Excellent seller!", found.getComment());
    }

    @Test
    void testDeleteReview() throws SQLException {
        Review r = reviewService.createReview(89, 3, 2, "Not good");
        reviewService.deleteReview(r.getReviewId());

        Review found = reviewService.getReviewById(r.getReviewId());
        Assertions.assertNull(found);
    }

    @Test
    void testGetReviewsForUser() throws SQLException {
        reviewService.createReview(89, 3, 4, "Good");
        reviewService.createReview(89, 3, 5, "Very good");

        List<Review> reviews = reviewService.getReviewsForUser(89);
        Assertions.assertEquals(2, reviews.size());
    }

    @Test
    void testGetAverageRating() throws SQLException {
        reviewService.createReview(89, 3, 3, "Okay");
        reviewService.createReview(89, 3, 5, "Excellent");

        double avg = reviewService.getUserRating(89);
        Assertions.assertEquals(4.0, avg);
    }
}
