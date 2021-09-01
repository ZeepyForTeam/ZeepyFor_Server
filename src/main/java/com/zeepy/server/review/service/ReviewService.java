package com.zeepy.server.review.service;

import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.MultiChoiceReview;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

		List<Review> reviewList = reviewRepository.findAllByBuilding(building);

		building.setAverageCommunicationTendency(
				returnMaxCommunicationTendency(reviewList));
		building.setAverageSoundInsulation(
				returnMaxSoundInsulation(reviewList));
		building.setAveragePest(
				returnMaxPest(reviewList));
		building.setAverageLightning(
				returnMaxLightning(reviewList));
		building.setAverageWaterPressure(
				returnMaxWaterPressure(reviewList));

		buildingRepository.save(building);

		return save.getId();
	}

	@Transactional
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	}

	private CommuncationTendency returnMaxCommunicationTendency(List<Review> reviewList) {
		Map<CommuncationTendency, Long> communicationTendencyCounts = reviewList.stream()
				.collect(Collectors.groupingBy(
						element -> element.getCommunicationTendency(),
						Collectors.counting()
				));

		return Collections.max(
				communicationTendencyCounts.entrySet(),
				Map.Entry.comparingByValue()
		).getKey();
	}

	private MultiChoiceReview returnMaxSoundInsulation(List<Review> reviewList) {
		Map<MultiChoiceReview, Long> soundInsulationCounts = reviewList.stream()
				.collect(Collectors.groupingBy(
						element -> element.getSoundInsulation(),
						Collectors.counting()
				));

		return Collections.max(
				soundInsulationCounts.entrySet(),
				Map.Entry.comparingByValue()
		).getKey();
	}

	private MultiChoiceReview returnMaxPest(List<Review> reviewList) {
		Map<MultiChoiceReview, Long> pestCounts = reviewList.stream()
				.collect(Collectors.groupingBy(
						element -> element.getPest(),
						Collectors.counting()
				));

		return Collections.max(
				pestCounts.entrySet(),
				Map.Entry.comparingByValue()
		).getKey();
	}

	private MultiChoiceReview returnMaxLightning(List<Review> reviewList) {
		Map<MultiChoiceReview, Long> lightningCounts = reviewList.stream()
				.collect(Collectors.groupingBy(
						element -> element.getLightning(),
						Collectors.counting()
				));

		return Collections.max(
				lightningCounts.entrySet(),
				Map.Entry.comparingByValue()
		).getKey();
	}

	private MultiChoiceReview returnMaxWaterPressure(List<Review> reviewList) {
		Map<MultiChoiceReview, Long> waterPressureCounts = reviewList.stream()
				.collect(Collectors.groupingBy(
						element -> element.getWaterPressure(),
						Collectors.counting()
				));

		return Collections.max(
				waterPressureCounts.entrySet(),
				Map.Entry.comparingByValue()
		).getKey();
	}
}
