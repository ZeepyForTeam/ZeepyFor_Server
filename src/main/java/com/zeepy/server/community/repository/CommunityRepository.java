package com.zeepy.server.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.community.domain.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
}
