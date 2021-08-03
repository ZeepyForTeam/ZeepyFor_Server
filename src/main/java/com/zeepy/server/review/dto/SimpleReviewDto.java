package com.zeepy.server.review.dto;

import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.LessorAge;
import com.zeepy.server.review.domain.MultiChoiceReview;
import com.zeepy.server.review.domain.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SimpleReviewDto {
	private Long id;
	private LessorAge lessorAge;
	private CommuncationTendency communcationTendency;
	private MultiChoiceReview soundInsulation;
	private MultiChoiceReview pest;
	private MultiChoiceReview lightning;
	private MultiChoiceReview waterPressure;

	public SimpleReviewDto(Review review) {
		this.id = review.getId();
		this.lessorAge = review.getLessorAge();
		this.communcationTendency = review.getCommunicationTendency();
		this.soundInsulation = review.getSoundInsulation();
		this.pest = review.getPest();
		this.lightning = review.getLightning();
		this.waterPressure = review.getWaterPressure();
	}
}
