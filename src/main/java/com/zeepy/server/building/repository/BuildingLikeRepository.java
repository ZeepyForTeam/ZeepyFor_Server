package com.zeepy.server.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.building.domain.BuildingLike;

/**
 * Created by Minky on 2021-06-02
 */
@Repository
public interface BuildingLikeRepository extends JpaRepository<BuildingLike, Long> {
}
