package com.zeepy.server.review.repository;

import com.zeepy.server.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewInterface extends JpaRepository<Review, Long> {
}
