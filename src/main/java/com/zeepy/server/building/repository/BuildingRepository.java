package com.zeepy.server.building.repository;

import com.zeepy.server.building.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Minky on 2021-05-15
 */
public interface BuildingRepository extends JpaRepository<Building, Long> {
    List<Building> findByAddress(String address);
}
