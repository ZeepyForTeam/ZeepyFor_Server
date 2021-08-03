package com.zeepy.server.review.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.review.domain.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SimpleReviewListDto {
	private List<SimpleReviewDto> simpleReviewDtoList;

	public SimpleReviewListDto(List<SimpleReviewDto> simpleReviewDtoList) {
		this.simpleReviewDtoList = simpleReviewDtoList;
	}

	public static SimpleReviewListDto listOf(List<Review> reviewList) {
		return reviewList.stream()
			.map(SimpleReviewDto::new)
			.collect(Collectors.collectingAndThen(Collectors.toList(), SimpleReviewListDto::new));
	}
}
