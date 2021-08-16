package com.zeepy.server.review.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.dto.ReviewResponseDto;
import com.zeepy.server.review.dto.SimpleReviewListDto;
import com.zeepy.server.review.service.ReviewService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/{review}")
	public ResponseEntity<ReviewResponseDto> getReview(@PathVariable("review") Long reviewId) {
		ReviewResponseDto reviewResponseDto = reviewService.getReview(reviewId);
		return ResponseEntity.ok().body(reviewResponseDto);
	}

	@GetMapping("/user")
	public ResponseEntity<SimpleReviewListDto> getUserReviews(@AuthenticationPrincipal String userEmail) {
		SimpleReviewListDto simpleReviewListDto = reviewService.getUserReviewList(userEmail);
		return ResponseEntity.ok().body(simpleReviewListDto);
	}

	@PostMapping
	public ResponseEntity<Void> saveReview(
		@Valid @RequestBody ReviewDto reviewDto,
		@AuthenticationPrincipal String userEmail
	) {
		Long saveId = reviewService.create(reviewDto, userEmail);
		return ResponseEntity.created(URI.create("/api/review/" + saveId)).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
		reviewService.deleteReview(id);
		return ResponseEntity.noContent().build();
	}
}
