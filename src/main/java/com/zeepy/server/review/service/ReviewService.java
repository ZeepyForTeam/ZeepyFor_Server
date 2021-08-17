package com.zeepy.server.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.repository.BuildingRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundReviewException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.dto.ReviewResponseDto;
import com.zeepy.server.review.dto.SimpleReviewListDto;
import com.zeepy.server.review.repository.ReviewRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final BuildingRepository buildingRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public ReviewResponseDto getReview(Long reviewId) {
		Review findReview = reviewRepository.findById(reviewId)
			.orElseThrow(NotFoundReviewException::new);

		return new ReviewResponseDto(findReview);
	}

	@Transactional(readOnly = true)
	public SimpleReviewListDto getUserReviewList(String userEmail) {
		User findUser = userRepository.findByEmail(userEmail)
			.orElseThrow(NotFoundUserException::new);

		return SimpleReviewListDto.listOf(findUser
			.getReviews());
	}

	@Transactional
	public Long create(ReviewDto reviewDto, String userEmail) {
		User findUser = userRepository.findByEmail(userEmail)
			.orElseThrow(NotFoundUserException::new);

		Review review = reviewDto.returnReviewEntity();
		review.setUser(findUser);

		Building building = buildingRepository.findById(reviewDto.getBuildingId())
			.orElseThrow(NoContentException::new);

		review.setBuilding(building);
		Review save = reviewRepository.save(review);
		return save.getId();
	}

	@Transactional
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	}
}
