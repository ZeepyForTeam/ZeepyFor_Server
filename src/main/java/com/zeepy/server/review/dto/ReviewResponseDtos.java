package com.zeepy.server.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ReviewResponseDtos {
    private List<ReviewResponseDto> reviewResponseDtos;

    public ReviewResponseDtos(List<ReviewResponseDto> reviewResponseDto) {
        this.reviewResponseDtos = reviewResponseDto;
    }
}
