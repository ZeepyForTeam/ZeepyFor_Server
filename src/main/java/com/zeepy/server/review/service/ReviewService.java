package com.zeepy.server.review.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.dto.ReviewResponseDto;
import com.zeepy.server.review.dto.ReviewResponseDtos;
import com.zeepy.server.review.repository.ReviewInterface;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewService {
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public ReviewResponseDtos getReviewList(String address) {
		List<Review> reviewList = reviewInterface.findAllByAddress(address);
		if (reviewList.isEmpty()) {
			throw new NoContentException();
		}
		return new ReviewResponseDtos(reviewList.stream()
			.map(ReviewResponseDto::new)
			.collect(Collectors.toList()));
	}

	@Transactional
	public Long create(ReviewDto reviewDto) {
		Review review = reviewDto.returnReviewEntity();
		Review save = reviewRepository.save(review);
		return save.getId();
	}

	@Transactional
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	}
}
