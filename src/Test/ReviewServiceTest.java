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
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewServiceTest {

    private ReviewService reviewService;
    private UserService userService;
    private int testUserId;
    private int testOrderId;

    @BeforeEach
    void setUp() throws SQLException {
        reviewService = new ReviewService(new JdbcReviewDAO());
        userService = new UserService(new JdbcUserDAO());

        try (Connection conn = DatabaseManager.getConnection()) {


            try (PreparedStatement psUser = conn.prepareStatement(
                    "INSERT INTO user (name, password, role_type, email) VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, "Ezana");
                psUser.setString(2, "pass123");
                psUser.setString(3, "USER");
                psUser.setString(4, "ezana_review@example.com");
                psUser.executeUpdate();

                try (ResultSet rs = psUser.getGeneratedKeys()) {
                    if (rs.next()) testUserId = rs.getInt(1);
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
        try (Connection conn = DatabaseManager.getConnection();
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
        Assertions.assertNotEquals(0, r.getReviewId());

        Review found = reviewService.getReviewById(r.getReviewId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals(testUserId, found.getReviewedUserId());
        Assertions.assertEquals(testOrderId, found.getOrderId());
        Assertions.assertEquals(5, found.getRating());
        Assertions.assertEquals("Excellent seller!", found.getComment());
    }
    @Test
    void testCreateReviewInvalidRatingTooLow() {
        try {
            reviewService.createReview(testUserId, testOrderId, 0, "Bad rating");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
    }
    @Test
    void testGetReviewByIdNonExisting() throws SQLException {
        Review found = reviewService.getReviewById(999999);
        assertNull(found);
    }
    @Test
    void testCreateReviewInvalidRatingTooHigh() {
        try {
            reviewService.createReview(testUserId, testOrderId, 6, "High rating");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (SQLException e) {
            fail("Unexpected SQLException");
        }
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
    void testGetReviewByIdNotFound() throws SQLException {
        Review r = reviewService.getReviewById(999999);
        assertNull(r);
    }
    @Test
    void testDeleteReview() throws SQLException {
        Review r = reviewService.createReview(testUserId, testOrderId, 2, "Not good");
        reviewService.deleteReview(r.getReviewId());

        Review found = reviewService.getReviewById(r.getReviewId());
        Assertions.assertNull(found);
    }

    @Test
    void testGetReviewsForUser() throws SQLException {
        reviewService.createReview(testUserId, testOrderId, 4, "Good");
        reviewService.createReview(testUserId, testOrderId, 5, "Very good");

        List<Review> reviews = reviewService.getReviewsForUser(testUserId);
        Assertions.assertEquals(2, reviews.size());
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
        Assertions.assertEquals(4.0, avg);
    }
}
