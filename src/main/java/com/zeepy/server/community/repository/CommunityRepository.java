package com.zeepy.server.community.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
	List<Community> findAllByUserId(Long userId);

	Page<Community> findAll(Pageable pageable);

	Page<Community> findByAddress(String address, Pageable pageable);

	Page<Community> findByAddressAndCommunityCategory(String address, CommunityCategory communityCategory, Pageable pageable);

	Page<Community> findByCategory(CommunityCategory communityType, Pageable pageable);
}
