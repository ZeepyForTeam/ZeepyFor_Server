package com.zeepy.server.email.repository;

import com.zeepy.server.email.domain.AdminEmail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Minky on 2021-07-25
 */
public interface EmailRepository extends JpaRepository<AdminEmail, Long> {
}
