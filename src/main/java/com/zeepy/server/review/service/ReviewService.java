package com.zeepy.server.review.service;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.dto.ReviewResponseDto;
import com.zeepy.server.review.dto.ReviewResponseDtos;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.repository.ReviewInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewInterface reviewInterface;

    @Transactional(readOnly = true)
    public ReviewResponseDtos getReviewList(String address) {
        List<Review> reviewList = reviewInterface.findAllByAddress(address);
        if(reviewList.isEmpty()) throw new NoContentException();
        return new ReviewResponseDtos(reviewList.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList()));
    }

    @Transactional
    public Long create(ReviewDto reviewDto) {
        Review review = reviewInterface.save(reviewDto.returnReviewEntity());
        return review.getId();
    }
}
