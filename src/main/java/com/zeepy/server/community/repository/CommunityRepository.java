package com.zeepy.server.community.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.community.domain.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
	List<Community> findAllByUserId(Long userId);
}
