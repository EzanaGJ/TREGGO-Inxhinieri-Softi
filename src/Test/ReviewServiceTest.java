package Test;

import Model.Review;
import DAO.JdbcReviewDAO;
import DAO.JdbcUserDAO;
import Service.ReviewService;
import Service.UserService;
import db.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewServiceTest {

    private ReviewService reviewService;
    private UserService userService;
    private int testUserId;
    private int testOrderId;

    @BeforeEach
    void setUp() throws SQLException {
        reviewService = new ReviewService(new JdbcReviewDAO());
        userService = new UserService(new JdbcUserDAO());

        try (Connection conn = DatabaseManager.getInstance().getConnection()) {

            try (PreparedStatement psUser = conn.prepareStatement(
                    "INSERT INTO user (name, password, role_type, email) VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                psUser.setString(1, "Ezana");
                psUser.setString(2, "pass123");
                psUser.setString(3, "USER");
                psUser.setString(4, "ezana_review@example.com");
                psUser.executeUpdate();

                try (ResultSet rs = psUser.getGeneratedKeys()) {
                    rs.next();
                    testUserId = rs.getInt(1);
                }
            }

            int testAddressId;
            try (PreparedStatement psAddr = conn.prepareStatement(
                    "INSERT INTO addresses (user_id, street, city, postal_code, country) VALUES (?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                psAddr.setInt(1, testUserId);
                psAddr.setString(2, "123 Review St");
                psAddr.setString(3, "Tirana");
                psAddr.setString(4, "1000");
                psAddr.setString(5, "Albania");
                psAddr.executeUpdate();

                try (ResultSet rs = psAddr.getGeneratedKeys()) {
                    rs.next();
                    testAddressId = rs.getInt(1);
                }
            }

            try (PreparedStatement psOrder = conn.prepareStatement(
                    "INSERT INTO `order` (user_id, address_id, status) VALUES (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                psOrder.setInt(1, testUserId);
                psOrder.setInt(2, testAddressId);
                psOrder.setString(3, "pending");
                psOrder.executeUpdate();

                try (ResultSet rs = psOrder.getGeneratedKeys()) {
                    rs.next();
                    testOrderId = rs.getInt(1);
                }
            }
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM review WHERE reviewed_user_id = " + testUserId);
            stmt.executeUpdate("DELETE FROM `order` WHERE order_id = " + testOrderId);
            stmt.executeUpdate("DELETE FROM addresses WHERE user_id = " + testUserId);
            stmt.executeUpdate("DELETE FROM user WHERE user_id = " + testUserId);
        }
    }

    @Test
    void testCreateAndGetReview() throws SQLException {
        Review r = reviewService.createReview(testUserId, testOrderId, 5, "Excellent seller!");
        assertNotEquals(0, r.getReviewId());

        Review found = reviewService.getReviewById(r.getReviewId());
        assertNotNull(found);
        assertEquals(testUserId, found.getReviewedUserId());
        assertEquals(testOrderId, found.getOrderId());
        assertEquals(5, found.getRating());
        assertEquals("Excellent seller!", found.getComment());
    }

    @Test
    void testCreateReviewInvalidRatingTooLow() {
        assertThrows(IllegalArgumentException.class, () ->
                reviewService.createReview(testUserId, testOrderId, 0, "Bad rating"));
    }

    @Test
    void testCreateReviewInvalidRatingTooHigh() {
        assertThrows(IllegalArgumentException.class, () ->
                reviewService.createReview(testUserId, testOrderId, 6, "High rating"));
    }

    @Test
    void testGetReviewByIdNonExisting() throws SQLException {
        Review found = reviewService.getReviewById(999999);
        assertNull(found);
    }

    @Test
    void testGetReviewById() throws SQLException {
        Review r = reviewService.createReview(testUserId, testOrderId, 5, "Excellent seller!");

        Review found = reviewService.getReviewById(r.getReviewId());

        assertNotNull(found);
        assertEquals(r.getReviewId(), found.getReviewId());
        assertEquals(r.getReviewedUserId(), found.getReviewedUserId());
        assertEquals(r.getOrderId(), found.getOrderId());
        assertEquals(r.getRating(), found.getRating());
        assertEquals(r.getComment(), found.getComment());
    }

    @Test
    void testDeleteReview() throws SQLException {
        Review r = reviewService.createReview(testUserId, testOrderId, 2, "Not good");
        reviewService.deleteReview(r.getReviewId());

        Review found = reviewService.getReviewById(r.getReviewId());
        assertNull(found);
    }

    @Test
    void testGetReviewsForUser() throws SQLException {
        reviewService.createReview(testUserId, testOrderId, 4, "Good");
        reviewService.createReview(testUserId, testOrderId, 5, "Very good");

        List<Review> reviews = reviewService.getReviewsForUser(testUserId);
        assertEquals(2, reviews.size());
    }

    @Test
    void testGetReviewsForUserNoReviews() throws SQLException {
        List<Review> reviews = reviewService.getReviewsForUser(testUserId);
        assertTrue(reviews.isEmpty());
    }

    @Test
    void testGetAverageRating() throws SQLException {
        reviewService.createReview(testUserId, testOrderId, 3, "Okay");
        reviewService.createReview(testUserId, testOrderId, 5, "Excellent");

        double avg = reviewService.getUserRating(testUserId);
        assertEquals(4.0, avg);
    }

    @Test
    void testCreateReviewRatingLowerBoundary() throws SQLException {
        Review r = reviewService.createReview(testUserId, testOrderId, 1, "Minimum rating");
        assertEquals(1, r.getRating());
    }

    @Test
    void testCreateReviewRatingUpperBoundary() throws SQLException {
        Review r = reviewService.createReview(testUserId, testOrderId, 5, "Maximum rating");
        assertEquals(5, r.getRating());
    }

    @Test
    void testGetAverageRatingNoReviews() throws SQLException {
        double avg = reviewService.getUserRating(testUserId);
        assertEquals(0.0, avg);
    }

    @Test
    void testDeleteNonExistingReview() {
        assertDoesNotThrow(() ->
                reviewService.deleteReview(999999));
    }

    @Test
    void testGetReviewsForUserMultipleCalls() throws SQLException {
        reviewService.createReview(testUserId, testOrderId, 3, "Ok");

        List<Review> reviews1 = reviewService.getReviewsForUser(testUserId);
        List<Review> reviews2 = reviewService.getReviewsForUser(testUserId);

        assertEquals(reviews1.size(), reviews2.size());
    }
}
