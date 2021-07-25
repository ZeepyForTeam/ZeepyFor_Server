package com.zeepy.server.community.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
	List<Community> findAllByUserId(Long userId);

	List<Community> findByAddress(String address);

	List<Community> findByAddressAndCommunityCategory(String address, CommunityCategory communityCategory);

	List<Community> findByCategory(CommunityCategory communityType);
}
