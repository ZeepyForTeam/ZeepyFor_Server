package com.zeepy.server.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.review.domain.Review;

@Repository
public interface ReviewInterface extends JpaRepository<Review, Long> {
	List<Review> findAllByAddress(String address);
}
