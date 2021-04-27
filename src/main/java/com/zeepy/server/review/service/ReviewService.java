package com.zeepy.server.review.service;

import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.repository.ReviewInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    final private ReviewInterface reviewInterface;

    @Transactional
    public void create(ReviewDto reviewDto) {
        Review review = reviewDto.returnReviewEntity();
        reviewInterface.save(review);
    }
}
