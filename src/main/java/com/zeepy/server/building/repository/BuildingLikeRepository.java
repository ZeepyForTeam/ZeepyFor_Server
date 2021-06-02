package com.zeepy.server.building.repository;

import com.zeepy.server.building.domain.BuildingLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Minky on 2021-06-02
 */
public interface BuildingLikeRepository extends JpaRepository<BuildingLike, Long> {
}
