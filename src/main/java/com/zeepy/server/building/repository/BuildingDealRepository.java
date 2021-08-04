package com.zeepy.server.building.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.building.domain.BuildingDeal;

/**
 * Created by Minky on 2021-05-15
 */
@Repository
public interface BuildingDealRepository extends JpaRepository<BuildingDeal, Long> {
	Optional<BuildingDeal> findByFloorAndBuilding_Id(int floor, Long id);
}
