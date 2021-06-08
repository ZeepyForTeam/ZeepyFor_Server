package com.zeepy.server.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeepy.server.community.domain.Participation;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
