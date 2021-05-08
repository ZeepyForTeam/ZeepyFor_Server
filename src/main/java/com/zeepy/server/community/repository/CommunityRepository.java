package com.zeepy.server.community.repository;

import com.zeepy.server.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community,Long> {
}
