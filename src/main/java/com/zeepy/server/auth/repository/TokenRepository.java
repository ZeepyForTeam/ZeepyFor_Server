package com.zeepy.server.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.auth.domain.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	void deleteByUserId(Long userId);

	Optional<Token> findByRefreshToken(String refreshToken);
}
