package com.zeepy.server.review.controller;

import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    final private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Void> getReview() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> saveReview(@RequestBody ReviewDto reviewDto) {
        reviewService.create(reviewDto);
        return ResponseEntity.ok().build();
    }

}
