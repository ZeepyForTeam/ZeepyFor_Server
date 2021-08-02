package com.zeepy.server.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.building.domain.AreaCode;

/**
 * Created by Minky on 2021-05-15
 */
@Repository
public interface AreaCodeRepository extends JpaRepository<AreaCode, Long> {
}
