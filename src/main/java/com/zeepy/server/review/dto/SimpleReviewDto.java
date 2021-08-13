package com.zeepy.server.review.dto;

import java.time.LocalDateTime;

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
	private String address;
	private LessorAge lessorAge;
	private CommuncationTendency communcationTendency;
	private MultiChoiceReview soundInsulation;
	private MultiChoiceReview pest;
	private MultiChoiceReview lightning;
	private MultiChoiceReview waterPressure;
	private LocalDateTime reviewDate;

	public SimpleReviewDto(Review review) {
		this.id = review.getId();
		this.address = review.getAddress();
		this.lessorAge = review.getLessorAge();
		this.communcationTendency = review.getCommunicationTendency();
		this.soundInsulation = review.getSoundInsulation();
		this.pest = review.getPest();
		this.lightning = review.getLightning();
		this.waterPressure = review.getWaterPressure();
		this.reviewDate = review.getCreatedDate();
	}
}
