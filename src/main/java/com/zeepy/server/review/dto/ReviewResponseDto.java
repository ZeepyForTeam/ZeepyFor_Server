package com.zeepy.server.review.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.Furniture;
import com.zeepy.server.review.domain.LessorAge;
import com.zeepy.server.review.domain.LessorGender;
import com.zeepy.server.review.domain.MultiChoiceReview;
import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.domain.RoomCount;
import com.zeepy.server.review.domain.TotalEvaluation;
import com.zeepy.server.user.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewResponseDto {
	private Long id;
	private ReviewUserResDto user;
	private CommuncationTendency communcationTendency;
	private LessorGender lessorGender;
	private LessorAge lessorAge;
	private String lessorReview;
	private RoomCount roomCount;
	private MultiChoiceReview soundInsulation;
	private MultiChoiceReview pest;
	private MultiChoiceReview lightning;
	private MultiChoiceReview waterPressure;
	private List<Furniture> furnitures;
	private String review;
	private TotalEvaluation totalEvaluation;
	private List<String> imageUrls;

	public ReviewResponseDto(
		Long id,
		User user,
		LessorAge lessorAge,
		LessorGender lessorGender,
		CommuncationTendency communcationTendency,
		MultiChoiceReview soundInsulation,
		MultiChoiceReview pest,
		MultiChoiceReview lightning,
		MultiChoiceReview waterPressure,
		String lessorReview,
		RoomCount roomCount,
		List<Furniture> furnitures,
		String review,
		TotalEvaluation totalEvaluation,
		List<String> imageUrls
	) {
		this.id = id;
		this.user = new ReviewUserResDto(user);
		this.lessorAge = lessorAge;
		this.lessorGender = lessorGender;
		this.communcationTendency = communcationTendency;
		this.soundInsulation = soundInsulation;
		this.pest = pest;
		this.lightning = lightning;
		this.waterPressure = waterPressure;
		this.lessorReview = lessorReview;
		this.roomCount = roomCount;
		this.furnitures = furnitures;
		this.review = review;
		this.totalEvaluation = totalEvaluation;
		this.imageUrls = imageUrls;
	}

    public ReviewResponseDto(Review review) {
		this.id = review.getId();
		this.user = new ReviewUserResDto(review.getUser());
		this.lessorAge = review.getLessorAge();
		this.lessorGender = review.getLessorGender();
		this.communcationTendency = review.getCommunicationTendency();
		this.soundInsulation = review.getSoundInsulation();
		this.pest = review.getPest();
		this.lightning = review.getLightning();
		this.waterPressure = review.getWaterPressure();
		this.lessorReview = review.getLessorReview();
		this.roomCount = review.getRoomCount();
		this.furnitures = review.getFurnitures();
		this.review = review.getReview();
		this.totalEvaluation = review.getTotalEvaluation();
		this.imageUrls = review.getImageUrls();
	}

    public static ReviewResponseDto of(Review review) {
		return new ReviewResponseDto(
			review.getId(),
			review.getUser(),
			review.getLessorAge(),
			review.getLessorGender(),
			review.getCommunicationTendency(),
			review.getSoundInsulation(),
			review.getPest(),
			review.getLightning(),
			review.getWaterPressure(),
			review.getLessorReview(),
			review.getRoomCount(),
			review.getFurnitures(),
			review.getReview(),
			review.getTotalEvaluation(),
			review.getImageUrls()
		);
    }

    public static List<ReviewResponseDto> listOf(List<Review> reviewList) {
		return reviewList
			.stream()
			.map(ReviewResponseDto::of)
			.collect(Collectors.toList());
	}
}
