package com.zeepy.server.building.repository;

import com.zeepy.server.building.domain.BuildingDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Minky on 2021-05-15
 */
@Repository
public interface BuildingDealRepository extends JpaRepository<BuildingDeal, Long> {
    Optional<BuildingDeal> findByFloorAndBuilding_Id(int floor, Long id);
}
