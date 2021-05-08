package com.zeepy.server.community.repository;

import com.zeepy.server.community.domain.CommunityLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike,Long> {
}
