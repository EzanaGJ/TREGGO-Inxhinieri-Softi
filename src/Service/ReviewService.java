package Service;

import DAO.ReviewDAO;
import Model.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public Review createReview(int reviewedUserId, int orderId, int rating, String comment) throws SQLException {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        Review review = new Review(reviewedUserId, orderId, rating, comment);
        return reviewDAO.create(review);
    }

    public Review getReviewById(int id) throws SQLException {
        return reviewDAO.getById(id);
    }

    public List<Review> getReviewsForUser(int userId) throws SQLException {
        return reviewDAO.getReviewsForUser(userId);
    }

    public double getUserRating(int userId) throws SQLException {
        return reviewDAO.getAverageRatingForUser(userId);
    }

    public void deleteReview(int id) throws SQLException {
        reviewDAO.delete(id);
    }
}
