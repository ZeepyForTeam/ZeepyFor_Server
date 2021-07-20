package com.zeepy.server.review.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewResponseDtos {
	private List<ReviewResponseDto> reviewResponseDtos;

	public ReviewResponseDtos(List<ReviewResponseDto> reviewResponseDto) {
		this.reviewResponseDtos = reviewResponseDto;
	}
}
