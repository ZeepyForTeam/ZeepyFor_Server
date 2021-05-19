package com.zeepy.server.building.service;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.building.dto.BuildingResponseDto;
import com.zeepy.server.building.repository.BuildingRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Minky on 2021-05-15
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingService {
    private final BuildingRepository buildingRepository;

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
    // TODO : 추후에 ZoomLevel에 따른 위도 경도 범위 지정 기능 추가
    @Transactional(readOnly = true)
    public List<BuildingResponseDto> getByLatitudeAndLongitude(double latitude, double longitude) {
        List<Building> buildingList = buildingRepository
                .findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                        latitude - 0.0001,
                        latitude + 0.0001,
                        longitude - 0.001,
                        longitude + 0.001
                );
        return BuildingResponseDto.listOf(buildingList);
    }

    // READ
    // TODO : 추후에 페이징 기능 탑제
    @Transactional(readOnly = true)
    public List<String> getBuildingAddressesByAddress(String address) {
        List<Building> buildingList = buildingRepository.findByAddressContaining(address);
        return buildingList
                .stream()
                .map(building -> building.getAddress())
                .collect(Collectors.toList());
    }

    // READ
    @Transactional(readOnly = true)
    @ExceptionHandler(NoContentException.class)
    public BuildingResponseDto getById(Long id) {
        Building building = buildingRepository
                .findById(id)
                .orElseThrow(() -> new NoContentException());
        return BuildingResponseDto.of(building);
    }

    // UPDATE
    @Transactional
    @ExceptionHandler(NoContentException.class)
    public void update(Long id, BuildingRequestDto buildingRequestDto) {
        Building building = buildingRepository
                .findById(id)
                .orElseThrow(() -> new NoContentException());
        building.setBuildYear(buildingRequestDto.getBuildYear());
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
