package com.zeepy.server.building.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.building.domain.Building;

/**
 * Created by Minky on 2021-05-15
 */
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
	Optional<Building> findByFullNumberAddress(String address);

	Optional<Building> findByFullNumberAddressOrFullRoadNameAddress(
		String address1,
		String address2
	);

	Page<Building> findByFullNumberAddressContainingOrFullRoadNameAddressContaining(
		String address1,
		String address2,
		Pageable pageable
	);

	List<Building> findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
		double latitudeGreater,
		double latitudeLess,
		double longitudeGreater,
		double longitudeLess
	);
}
