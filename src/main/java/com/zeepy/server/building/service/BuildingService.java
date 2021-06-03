package com.zeepy.server.building.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.dto.BuildingAddressResponseDto;
import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.building.dto.BuildingResponseDto;
import com.zeepy.server.building.repository.BuildingRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Minky on 2021-05-15
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final JPAQueryFactory jpaQueryFactory;

    // CREATE
    @Transactional
    public Long create(BuildingRequestDto buildingRequestDto) {
        Building building = buildingRepository.save(buildingRequestDto.returnBuildingEntity());
        return building.getId();
    }

    // READ
    @Transactional(readOnly = true)
    public List<BuildingResponseDto> getAll() {
        List<Building> buildingList = buildingRepository.findAll();
        return BuildingResponseDto.listOf(buildingList);
    }

    // READ
    @Transactional(readOnly = true)
    public BuildingResponseDto getByAddress(String address) {
        Building building = buildingRepository.findByAddress(address)
                .orElseThrow(NoContentException::new);
        return BuildingResponseDto.of(building);
    }

    // READ
    @Transactional(readOnly = true)
    public List<BuildingResponseDto> getByLatitudeAndLongitude(
            double latitudeGreater,
            double latitudeLess,
            double longitudeGreater,
            double longitudeLess
    ) {
        List<Building> buildingList = buildingRepository
                .findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                        latitudeGreater,
                        latitudeLess,
                        longitudeGreater,
                        longitudeLess
                );
        return BuildingResponseDto.listOf(buildingList);
    }

    // READ
    // TODO : 추후에 페이징 기능 탑제
    @Transactional(readOnly = true)
    public List<BuildingAddressResponseDto> getBuildingAddressesByAddress(String address) {
        List<Building> buildingList = buildingRepository.findByAddressContaining(address);
        return BuildingAddressResponseDto.listOf(buildingList);
    }

    // READ
    @Transactional(readOnly = true)
    public BuildingResponseDto getById(Long id) {
        Building building = buildingRepository
                .findById(id)
                .orElseThrow(NoContentException::new);
        return BuildingResponseDto.of(building);
    }

    // UPDATE
    @Transactional
    public void update(Long id, BuildingRequestDto buildingRequestDto) {
        Building building = buildingRepository
                .findById(id)
                .orElseThrow(NoContentException::new);

        building.setBuildYear(buildingRequestDto.getBuildYear());
        building.setApartmentName(buildingRequestDto.getApartmentName());
        building.setShortAddress(buildingRequestDto.getShortAddress());
        building.setAddress(buildingRequestDto.getAddress());
        building.setExclusivePrivateArea(buildingRequestDto.getExclusivePrivateArea());
        building.setAreaCode(buildingRequestDto.getAreaCode());

        buildingRepository.save(building);
    }

    // DELETE
    @Transactional
    public void deleteById(Long id) {
        buildingRepository.deleteById(id);
    }
}
