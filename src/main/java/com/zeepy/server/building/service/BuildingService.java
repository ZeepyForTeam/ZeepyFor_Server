package com.zeepy.server.building.service;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.building.dto.BuildingResponseDto;
import com.zeepy.server.building.repository.BuildingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    // UPDATE
    @Transactional
    public void update(Long id, BuildingRequestDto buildingRequestDto) {
        buildingRepository.findById(id).ifPresent(building -> {
            building.setBuildYear(buildingRequestDto.getBuildYear());
            building.setAddress(buildingRequestDto.getAddress());
            building.setExclusivePrivateArea(buildingRequestDto.getExclusivePrivateArea());
            building.setAreaCode(buildingRequestDto.getAreaCode());
            buildingRepository.save(building);
        });
    }

    // DELETE
    @Transactional
    public void deleteById(Long id) {
        buildingRepository.deleteById(id);
    }
}
