package com.zeepy.server.user.repository;

import com.zeepy.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
