package com.zeepy.server.review.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.Furniture;
import com.zeepy.server.review.domain.LessorAge;
import com.zeepy.server.review.domain.LessorGender;
import com.zeepy.server.review.domain.MultiChoiceReview;
import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.domain.RoomCount;
import com.zeepy.server.review.domain.TotalEvaluation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ReviewDto {
	private Long user; // 변경 될 겁니다.

	@NotNull
	private String address;

	@NotNull
	private CommuncationTendency communcationTendency;

	@NotNull
	private LessorGender lessorGender;

	@NotNull
	private LessorAge lessorAge;

	@NotNull
	private String lessorReview;

	@NotNull
	private RoomCount roomCount;

	@NotNull
	private MultiChoiceReview soundInsulation;

	@NotNull
	private MultiChoiceReview pest;

	@NotNull
	private MultiChoiceReview lightning;

	@NotNull
	private MultiChoiceReview waterPressure;

	private List<Furniture> furnitures;

	@NotBlank(message = "상세리뷰는 필수값입니다.")
	private String review;

	@NotNull
	private TotalEvaluation totalEvaluation;

	private List<String> imageUrls;

	public ReviewDto(
		Long user, String address,
		CommuncationTendency communcationTendency,
		LessorGender lessorGender,
		LessorAge lessorAge,
		String lessorReview,
		RoomCount roomCount,
		MultiChoiceReview soundInsulation,
		MultiChoiceReview pest,
		MultiChoiceReview lightning,
		MultiChoiceReview waterPressure,
		List<Furniture> furnitures,
		String review,
		TotalEvaluation totalEvaluation,
		List<String> imageUrls
	) {
		this.user = user;
		this.address = address;
		this.communcationTendency = communcationTendency;
		this.lessorGender = lessorGender;
		this.lessorAge = lessorAge;
		this.lessorReview = lessorReview;
		this.roomCount = roomCount;
		this.soundInsulation = soundInsulation;
		this.pest = pest;
		this.lightning = lightning;
		this.waterPressure = waterPressure;
		this.furnitures = furnitures;
		this.review = review;
		this.totalEvaluation = totalEvaluation;
		this.imageUrls = imageUrls;
	}

	public Review returnReviewEntity() {
		return new Review(null,
			this.user,
			this.address,
			this.communcationTendency,
			this.lessorGender,
			this.lessorAge,
			this.lessorReview,
			this.roomCount,
			this.soundInsulation,
			this.pest,
			this.lightning,
			this.waterPressure,
			this.furnitures,
			this.review,
			this.totalEvaluation,
			this.imageUrls
		);
	}
}
