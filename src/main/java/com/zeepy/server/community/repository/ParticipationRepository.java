package com.zeepy.server.community.repository;

import com.zeepy.server.community.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
