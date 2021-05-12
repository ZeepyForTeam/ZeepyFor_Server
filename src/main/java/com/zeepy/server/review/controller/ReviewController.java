package com.zeepy.server.review.controller;

import com.zeepy.server.review.dto.ResponseReviewListDtos;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewController {
    final private ReviewService reviewService;

    @GetMapping("/{address}")//api를 스케줄링으로 불러오면 build_id사용해서 객체참조로 불러오도록 수정
    public ResponseEntity<ResponseReviewListDtos> getReview(@PathVariable String address) {
        ResponseReviewListDtos responseReviewListDtos = reviewService.getReviewList(address);
        return ResponseEntity.ok().body(responseReviewListDtos);
    }

    @PostMapping
    public ResponseEntity<Void> saveReview(@RequestBody ReviewDto reviewDto) {
        reviewService.create(reviewDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}
