package com.example.backend.service;

import com.example.backend.entity.Review;
import com.example.backend.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;



    public Review addReview(String name, String message, int rating) {
        Review review = new Review();
        review.setName(name);
        review.setMessage(message);
        review.setRating(rating);

        return reviewRepo.save(review);
    }

    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepo.findAll();
        Collections.reverse(reviews);
        return reviews;}

}
