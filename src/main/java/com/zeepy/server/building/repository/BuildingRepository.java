package com.zeepy.server.building.repository;

import com.zeepy.server.building.domain.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Minky on 2021-05-15
 */
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByAddressContaining(String address);

    Page<Building> findByAddressContaining(String address, Pageable pageable);

    List<Building> findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
            double latitudeGreater,
            double latitudeLess,
            double longitudeGreater,
            double longitudeLess
    );
}
