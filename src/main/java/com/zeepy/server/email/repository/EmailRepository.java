package com.zeepy.server.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeepy.server.email.domain.AdminEmail;

/**
 * Created by Minky on 2021-07-25
 */
public interface EmailRepository extends JpaRepository<AdminEmail, Long> {
}
