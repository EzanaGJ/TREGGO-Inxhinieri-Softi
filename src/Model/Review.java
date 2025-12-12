package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Review {
    int reviewId;
    int reviewedUserId;
    int rating;
    String comment;
    Date createdAt;

    static List<Review> reviewDatabase = new ArrayList<>();
    static int idCounter = 1;

    public Review(int reviewedUserId, int rating, String comment) {
        this.reviewId = idCounter++;
        this.reviewedUserId = reviewedUserId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = new Date();
    }

    void createReview() {
        reviewDatabase.add(this);
        System.out.println("Review " + reviewId + " created for user " + reviewedUserId);
    }

    void updateReview(int newRating, String newComment) {
        this.rating = newRating;
        this.comment = newComment;
        this.createdAt = new Date();
        System.out.println("Review " + reviewId + " updated.");
    }

    void deleteReview() {
        reviewDatabase.removeIf(r -> r.reviewId == this.reviewId);
        System.out.println("Review " + reviewId + " deleted.");
    }

    String getComment() {
        return comment;
    }

    void setComment(String newComment) {
        this.comment = newComment;
        this.createdAt = new Date();
        System.out.println("Comment for review " + reviewId + " updated.");
    }

    static void listReviewsForUser(int userId) {
        System.out.println("Reviews for user " + userId + ":");
        for (Review r : reviewDatabase) {
            if (r.reviewedUserId == userId) {
                System.out.println(" - Rating: " + r.rating + ", Comment: " + r.comment + ", Date: " + r.createdAt);
            }
        }
    }
}
