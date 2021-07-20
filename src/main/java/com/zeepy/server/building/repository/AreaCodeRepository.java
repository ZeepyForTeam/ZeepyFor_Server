package com.zeepy.server.building.repository;

import com.zeepy.server.building.domain.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Minky on 2021-05-15
 */
@Repository
public interface AreaCodeRepository extends JpaRepository<AreaCode, Long> {
}
