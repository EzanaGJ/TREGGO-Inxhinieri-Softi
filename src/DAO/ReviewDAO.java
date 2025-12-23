package DAO;

import Model.Review;
import java.sql.SQLException;
import java.util.List;

public interface ReviewDAO {

    Review create(Review review) throws SQLException;

    Review getById(int id) throws SQLException;

    List<Review> getReviewsForUser(int userId) throws SQLException;

    double getAverageRatingForUser(int userId) throws SQLException;

    void delete(int id) throws SQLException;
}
