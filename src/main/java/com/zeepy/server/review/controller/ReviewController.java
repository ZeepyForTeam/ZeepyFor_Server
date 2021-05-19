package com.zeepy.server.review.controller;

import com.zeepy.server.review.dto.ReviewResponseDtos;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{address}")//api를 스케줄링으로 불러오면 build_id사용해서 객체참조로 불러오도록 수정
    public ResponseEntity<ReviewResponseDtos> getReview(@PathVariable String address) {
        ReviewResponseDtos reviewResponseDtos = reviewService.getReviewList(address);
        return ResponseEntity.ok().body(reviewResponseDtos);
    }

    @PostMapping
    public ResponseEntity<Void> saveReview(@Valid @RequestBody ReviewDto reviewDto) {
        Long saveId = reviewService.create(reviewDto);
        return ResponseEntity.created(URI.create("/api/review/" + saveId)).build();
    }

}
