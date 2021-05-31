package com.zeepy.server.review.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zeepy.server.common.annotation.Enum;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {

	private Long user; // 변경 될 겁니다.

	@NotNull
	private String address;

	@Enum(enumClass = CommuncationTendency.class, message = "소통경향은 필수값입니다.")
	private CommuncationTendency communcationTendency;

	@Enum(enumClass = LessorGender.class, message = "임대인 성별은 필수값입니다.")
	private LessorGender lessorGender;

	@Enum(enumClass = LessorAge.class, message = "임대인 나이는 필수값입니다.")
	private LessorAge lessorAge;

	@NotNull
	private String lessorReview;

	@Enum(enumClass = RoomCount.class, message = "방갯수 필수값입니다.")
	private RoomCount roomCount;

	@Enum(enumClass = MultiChoiceReview.class, message = "방음은 필수값입니다.")
	private MultiChoiceReview soundInsulation;

	@Enum(enumClass = MultiChoiceReview.class, message = "해충은 필수값입니다.")
	private MultiChoiceReview pest;

	@Enum(enumClass = MultiChoiceReview.class, message = "채광은 필수값입니다.")
	private MultiChoiceReview lightning;

	@Enum(enumClass = MultiChoiceReview.class, message = "수압은 필수값입니다.")
	private MultiChoiceReview waterPressure;

	private List<Furniture> furnitures;

	@NotBlank(message = "상세리뷰는 필수값입니다.")
	private String review;

	@Enum(enumClass = TotalEvaluation.class, message = "종합평가는 필수값입니다.")
	private TotalEvaluation totalEvaluation;

	private List<String> imageUrls;

	@Builder
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
