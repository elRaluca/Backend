package com.example.backend.controller;

import com.example.backend.entity.Review;
import com.example.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review createdReview = reviewService.addReview(review.getName(), review.getMessage(), review.getRating());

        if ( createdReview.getName().length() > 30) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ( createdReview.getMessage().length() > 500) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(createdReview);
    }


}
