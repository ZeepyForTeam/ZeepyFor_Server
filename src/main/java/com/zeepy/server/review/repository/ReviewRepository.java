package com.zeepy.server.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeepy.server.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findAllByUserId(Long userId);
}
