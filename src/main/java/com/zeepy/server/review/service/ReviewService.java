package com.zeepy.server.review.service;

import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.dto.ResponseReviewListDto;
import com.zeepy.server.review.dto.ResponseReviewListDtos;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.repository.ReviewInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    final private ReviewInterface reviewInterface;

    @Transactional(readOnly = true)
    public ResponseReviewListDtos getReviewList(String address){
        return new ResponseReviewListDtos(reviewInterface.findAllByAddress(address).stream()
                .map(ResponseReviewListDto::new)
                .collect(Collectors.toList()));
    }

    @Transactional
    public void create(ReviewDto reviewDto) {
        Review review = reviewDto.returnReviewEntity();
        reviewInterface.save(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewInterface.deleteById(id);
    }
}
