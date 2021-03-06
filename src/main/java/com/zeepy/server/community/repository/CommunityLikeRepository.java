package com.zeepy.server.community.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.user.domain.User;

@Repository
public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {
	CommunityLike findByCommunityAndUser(Community community, User user);

	List<CommunityLike> findAllByUserId(Long id);
}
