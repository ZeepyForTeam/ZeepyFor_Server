package com.zeepy.server.review.service;

import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.dto.ResponseReviewListDto;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.repository.ReviewInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    final private ReviewInterface reviewInterface;

    @Transactional(readOnly = true)
    public List<Review> getReviewList(String address){
        return reviewInterface.findAllByAddress(address);
    }

    @Transactional
    public void create(ReviewDto reviewDto) {
        Review review = reviewDto.returnReviewEntity();
        reviewInterface.save(review);
    }
}
