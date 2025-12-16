package Test;

import Model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @BeforeEach
    void setUp() {
        Review.reviewDatabase.clear();
        Review.idCounter = 1;
    }

    @Test
    void testCreateReview() {
        Review r = new Review(101, 5, "Excellent!");
        r.createReview();

        assertEquals(1, Review.reviewDatabase.size());
        assertEquals("Excellent!", Review.reviewDatabase.get(0).comment);
        assertEquals(101, Review.reviewDatabase.get(0).reviewedUserId);
    }

    @Test
    void testUpdateReview() throws InterruptedException {
        Review r = new Review(101, 4, "Good");
        r.createReview();

        Thread.sleep(10);
        r.updateReview(5, "Very Good");

        assertEquals(5, r.rating);
        assertEquals("Very Good", r.comment);
        assertNotNull(r.createdAt);
    }

    @Test
    void testDeleteReview() {
        Review r1 = new Review(101, 3, "Average");
        Review r2 = new Review(102, 5, "Excellent");

        r1.createReview();
        r2.createReview();

        r1.deleteReview();

        assertEquals(1, Review.reviewDatabase.size());
        assertEquals(102, Review.reviewDatabase.get(0).reviewedUserId);
    }

    @Test
    void testSetComment() throws InterruptedException {
        Review r = new Review(101, 4, "Good");
        r.createReview();

        Thread.sleep(10);
        r.setComment("Very Good");

        assertEquals("Very Good", r.getComment());
        assertNotNull(r.createdAt);
    }

    @Test
    void testListReviewsForUser() {
        Review r1 = new Review(101, 5, "Excellent");
        Review r2 = new Review(101, 4, "Good");
        Review r3 = new Review(102, 3, "Average");

        r1.createReview();
        r2.createReview();
        r3.createReview();

        long count101 = Review.reviewDatabase.stream().filter(r -> r.reviewedUserId == 101).count();
        long count102 = Review.reviewDatabase.stream().filter(r -> r.reviewedUserId == 102).count();

        assertEquals(2, count101);
        assertEquals(1, count102);
    }
}
