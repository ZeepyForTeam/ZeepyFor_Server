package com.zeepy.server.building.repository;

import com.zeepy.server.building.domain.BuildingDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Minky on 2021-05-15
 */
public interface BuildingDealRepository extends JpaRepository<BuildingDeal, Long> {
    List<BuildingDeal> findByFloor(int floor);
}