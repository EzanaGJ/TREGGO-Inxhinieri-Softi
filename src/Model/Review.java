package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Review {
    int reviewId;
    public int reviewedUserId;
    public int rating;
    public String comment;
    public Date createdAt;

    public static List<Review> reviewDatabase = new ArrayList<>();
    public static int idCounter = 1;

    public Review(int reviewedUserId, int rating, String comment) {
        this.reviewId = idCounter++;
        this.reviewedUserId = reviewedUserId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = new Date();
    }

    public void createReview() {
        reviewDatabase.add(this);
        System.out.println("Review " + reviewId + " created for user " + reviewedUserId);
    }

    public void updateReview(int newRating, String newComment) {
        this.rating = newRating;
        this.comment = newComment;
        this.createdAt = new Date();
        System.out.println("Review " + reviewId + " updated.");
    }

    public void deleteReview() {
        reviewDatabase.removeIf(r -> r.reviewId == this.reviewId);
        System.out.println("Review " + reviewId + " deleted.");
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String newComment) {
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
