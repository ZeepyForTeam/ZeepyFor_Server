package com.zeepy.server.review.dto;

import java.util.List;

import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.LessorAge;
import com.zeepy.server.review.domain.MultiChoiceReview;
import com.zeepy.server.review.domain.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewResponseDto {
	private Long id;
	private Long user;
	private String address;
	private LessorAge lessorAge;
	private CommuncationTendency communcationTendency;
	private MultiChoiceReview soundInsulation;
	private MultiChoiceReview pest;
	private MultiChoiceReview lightning;
	private MultiChoiceReview waterPressure;
	private String lessorReview;
	private String review;
	private List<String> imageUrls;

	public ReviewResponseDto(Review review) {
		this.id = review.getId();
		this.user = review.getUser();
		this.address = review.getAddress();
		this.lessorAge = review.getLessorAge();
		this.communcationTendency = review.getCommuncationTendency();
		this.soundInsulation = review.getSoundInsulation();
		this.pest = review.getPest();
		this.lightning = review.getLightning();
		this.waterPressure = review.getWaterPressure();
		this.lessorReview = review.getLessorReview();
		this.review = review.getReview();
		this.imageUrls = review.getImageUrls();
	}
}
