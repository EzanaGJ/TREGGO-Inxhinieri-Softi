package Model;

import java.util.Date;

public class Review {
    private int reviewId;
    private int reviewedUserId;
    private int orderId;
    private int rating;
    private String comment;
    private Date createdAt;

    public Review(int reviewedUserId, int orderId, int rating, String comment) {
        this.reviewedUserId = reviewedUserId;
        this.orderId = orderId;
        this.rating = rating;
        this.comment = comment;
    }

    public Review(int reviewId, int reviewedUserId, int orderId, int rating, String comment, Date createdAt) {
        this.reviewId = reviewId;
        this.reviewedUserId = reviewedUserId;
        this.orderId = orderId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getReviewedUserId() {
        return reviewedUserId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
