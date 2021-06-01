package com.zeepy.server.community.repository;

import com.zeepy.server.community.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findAllByUserId(Long userId);

    Optional<Participation> findByCommunityIdAndUserId(Long communityId, Long userId);

    void deleteByUserIdAndCommunityId(Long userId, Long communityId);
}
