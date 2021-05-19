package com.zeepy.server.building.repository;

import com.zeepy.server.building.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Minky on 2021-05-15
 */
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByAddress(String address);

    List<Building> findByAddressContaining(String address);

    List<Building> findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
            double latitudeGreater,
            double latitudeLess,
            double longitudeGreater,
            double longitudeLess
    );
}
