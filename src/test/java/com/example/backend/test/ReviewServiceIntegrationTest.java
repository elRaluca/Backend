package com.example.backend.test;

import com.example.backend.BackendApplication;
import com.example.backend.entity.Review;
import com.example.backend.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = BackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    public void testAddAndRetrieveReviews() {
        Review newReview = reviewService.addReview("John Doe",
                "Great service!", 5);
        assertNotNull(newReview, "The new review should not be null");
        assertEquals("John Doe", newReview.getName());
        assertEquals("Great service!", newReview.getMessage());
        assertEquals(5, newReview.getRating());
        List<Review> reviews = reviewService.getAllReviews();
        assertNotNull(reviews, "The reviews list should not be null");
        assertEquals(5, reviews.size(),
                "There should be one review in the list");
        assertEquals(newReview, reviews.get(0));
    }
}
