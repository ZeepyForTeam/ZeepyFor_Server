package com.zeepy.server.community.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.community.domain.Participation;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
	List<Participation> findAllByUserId(Long userId);

	Optional<Participation> findByCommunityIdAndUserId(Long communityId, Long userId);

	void deleteByUserIdAndCommunityId(Long userId, Long communityId);
}
