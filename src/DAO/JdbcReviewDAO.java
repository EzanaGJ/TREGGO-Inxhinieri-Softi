package DAO;

import Model.Review;
import db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JdbcReviewDAO implements ReviewDAO {

    @Override
    public Review create(Review review) throws SQLException {
        String sql = "INSERT INTO review (reviewed_user_id, order_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, review.getReviewedUserId());
            ps.setInt(2, review.getOrderId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Review(
                        rs.getInt(1),
                        review.getReviewedUserId(),
                        review.getOrderId(),
                        review.getRating(),
                        review.getComment(),
                        new Date()
                );
            }
        }
        return null;
    }

    @Override
    public Review getById(int id) throws SQLException {
        String sql = "SELECT * FROM review WHERE review_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rowToReview(rs);
        }
        return null;
    }

    @Override
    public List<Review> getReviewsForUser(int userId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE reviewed_user_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) reviews.add(rowToReview(rs));
        }
        return reviews;
    }

    @Override
    public double getAverageRatingForUser(int userId) throws SQLException {
        String sql = "SELECT AVG(rating) FROM review WHERE reviewed_user_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        }
        return 0.0;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM review WHERE review_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Review rowToReview(ResultSet rs) throws SQLException {
        return new Review(
                rs.getInt("review_id"),
                rs.getInt("reviewed_user_id"),
                rs.getInt("order_id"),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getTimestamp("created_at")
        );
    }
}
