package com.zeepy.server.review.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.review.domain.Review;

@NoArgsConstructor
@Getter
public class ReviewResponseDtos {
	private List<ReviewResponseDto> reviewResponseDtos;

	public ReviewResponseDtos(List<ReviewResponseDto> reviewResponseDto) {
		this.reviewResponseDtos = reviewResponseDto;
	}

    public static ReviewResponseDtos listOf(List<Review> reviews) {
        return reviews.stream()
            .map(ReviewResponseDto::new)
            .collect(Collectors.collectingAndThen(Collectors.toList(), ReviewResponseDtos::new));
    }
}
